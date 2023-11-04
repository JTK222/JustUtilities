package net.brazier_modding.justutilities.physics.collision;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class OrientedAABB implements IBoundingBox {

	private Vec3 pos;
	private AABB boundingBox;
	private Vec3[] axes = new Vec3[3];
	private Vec3[] verts = new Vec3[8];

	public OrientedAABB(AABB boundingBox, Vec3 pos){

	}

	@Override
	public void setPos(Vec3 pos){
		this.pos = pos;
	}

	@Override
	public void move(Vec3 pos){
		this.pos = this.pos.add(pos);
	}

	@Override
	public Vec3 getPos() {
		return this.pos;
	}

	@Override
	public Vec3[] getAxes() {
		return new Vec3[0];
	}

	@Override
	public Vec3[] getVerts() {
		return new Vec3[0];
	}
}
