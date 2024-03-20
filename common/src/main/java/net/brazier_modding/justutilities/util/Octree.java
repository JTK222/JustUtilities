package net.brazier_modding.justutilities.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class Octree<T> implements Iterable<T>{

	private int x, y, z;
	private int size, halfSize;
	private Octree<T>[] children;
	private T value;

	public Octree(int x, int y, int z, int size) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = size;
		this.halfSize = size / 2;
	}

	public T set(int x, int y, int z, T value){
		int childIndex = ((x - this.x >= halfSize) ? 1 : 0)
				+ ((y - this.y >= halfSize) ? 2 : 0)
				+ ((z - this.z >= halfSize) ? 4 : 0);

		if(this.size > 1) {
			if (this.children == null)
				this.children = new Octree[8];

			if(this.children[childIndex] == null)
				this.children[childIndex] = new Octree<>(
						(childIndex % 2) == 0 ? this.x : this.x + halfSize,
						(childIndex % 4) < 2 ? this.y : this.y + halfSize,
						childIndex < 4 ? this.z : this.z + halfSize,
						halfSize
				);

			return this.children[childIndex].set(x, y, z, value);
		}else{
			T oldVal = this.value;
			this.value = value;
			return oldVal;
		}
	}

	public T get(int x, int y, int z){
		if(this.size > 1 && this.children == null) return null;
		else if(this.children == null) return this.value;
		int childIndex = ((x - this.x >= halfSize) ? 1 : 0)
				+ ((y - this.y >= halfSize) ? 2 : 0)
				+ ((z - this.z >= halfSize) ? 4 : 0);

		return this.children[childIndex] == null ? null : this.children[childIndex].get(x, y, z);
	}

	public T remove(int x, int y, int z){
		if(this.size > 1 && this.children == null) return null;
		else if(this.children == null){
			T oldVal = this.value;
			this.value = null;
			return oldVal;
		}

		int childIndex = ((x - this.x >= halfSize) ? 1 : 0)
				+ ((y - this.y >= halfSize) ? 2 : 0)
				+ ((z - this.z >= halfSize) ? 4 : 0);

		if(this.children[childIndex] == null) return null;
		T oldVal = this.children[childIndex].remove(x, y, z);
		if(this.size == 2) this.children[childIndex] = null;
		else if(this.children[childIndex].isEmpty()) this.children[childIndex] = null;

		return oldVal;
	}

	private boolean isEmpty(){
		if(this.children == null) return true;
		for(int i = 0; i < 8; i++)
			if(this.children[i] != null) return false;
		return true;
	}

	public OctreeIterator<T> iterator(AABB aabb){
		return new OctreeIterator<T>(this, aabb);
	}

	@NotNull
	@Override
	public Iterator<T> iterator() {
		return new OctreeIterator<>(this, new AABB(this.x, this.y, this.z, this.x + this.size, this.y + this.size, this.z + this.size));
	}

	public void prettyPrint(int depth){
		String prefix = "\t".repeat(depth);

		if(this.size > 1 && this.children == null){
			System.out.println(prefix + "Tree Leaf: [EMPTY]");
		}else if(this.size > 1) {
			System.out.println(prefix + "Tree Node: [");
			for (int i = 0; i < 8; i++) {
				this.children[i].prettyPrint(depth + 1);
			}
			System.out.println(prefix + "]");
		}else{
			System.out.println(prefix + "Tree Leaf: " + this.value);
		}
	}

	public boolean isInAABB(AABB aabb){
		return this.x < aabb.maxX && this.x + this.size > aabb.minX && this.y < aabb.maxY &&
				this.y + this.size > aabb.minY && this.z < aabb.maxZ && this.z + this.size > aabb.minZ;
	}

	public class OctreeIterator<T> implements Iterator<T>, Iterable<T>{

		private Octree<T> octree;
		private AABB aabb;
		private int[] currentIndex;
		private int depth;
		private T next;

		public OctreeIterator(Octree<T> octree, AABB aabb) {
			this.octree = octree;
			this.aabb = aabb;
			this.currentIndex = new int[Mth.log2(octree.size)];
			this.depth = this.currentIndex.length;

			this.prepareNext();
		}

		@Override
		public boolean hasNext() {
			return this.next != null;
		}

		@Override
		public T next() {
			T next = this.next;
			this.prepareNext();
			return next;
		}

		private void prepareNext(){
			mainLoop:
			while(true){
				//Sanitize index
				for(int i = this.depth - 1; i >= 0; i--){
					if(this.currentIndex[i] > 7){
						if(i == 0) {
							this.next = null;
							return;
						}
						this.currentIndex[i-1]++;
						for(int j = i; j < this.depth; j++)
							this.currentIndex[j] = 0;
					}
				}

				//Find next valid branch
				Octree<T> branch = this.octree;
				for(int i = 0; i < this.depth; i++){
					branch = branch.children[this.currentIndex[i]];

					if(branch == null || !branch.isInAABB(this.aabb)){
						this.currentIndex[i]++;
						continue mainLoop;
					}
				}

				this.currentIndex[this.depth-1]++;
				if(branch.size == 1 && branch.value != null) {
					this.next = branch.value;
					return;
				}
			}
		}

		@NotNull
		@Override
		public Iterator<T> iterator() {
			return this;
		}
	}}