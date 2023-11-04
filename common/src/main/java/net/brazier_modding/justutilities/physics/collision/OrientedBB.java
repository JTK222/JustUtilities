package net.brazier_modding.justutilities.physics.collision;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class OrientedBB implements IBoundingBox {

	private Vec3 pos;
	private AABB aabb;

	private Vec3[] axes = new Vec3[3];
	private Vec3[] verts = new Vec3[8];

	private float rotX, rotY, rotZ;

	public OrientedBB(AABB aabb, Vec3 origin){
		this(aabb, origin, 0, 0 , 0);
	}

	public OrientedBB(AABB aabb, Vec3 pos, float rotX, float rotY, float rotZ){
		this.aabb = aabb;
		this.pos = pos;
		this.setRotation(rotX, rotY, rotZ);
		this.update();
	}

	@Override
	public void setPos(Vec3 pos){
		this.pos = pos;
		this.update();
	}

	@Override
	public void move(Vec3 pos){
		this.pos = this.pos.add(pos);
		this.update();
	}

	@Override
	public Vec3 getPos() {
		return this.pos;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void rotate(float rotX, float rotY, float rotZ){
		this.rotX += rotX;
		this.rotY += rotY;
		this.rotZ += rotZ;
		this.update();
	}

	public void setRotation(float rotX, float rotY, float rotZ){
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.update();
	}

	private void update(){
		//Reset Verts
		verts[0] = new Vec3(aabb.minX, aabb.minY, aabb.minZ);
		verts[1] = new Vec3(aabb.minX, aabb.minY, aabb.maxZ);
		verts[2] = new Vec3(aabb.minX, aabb.maxY, aabb.minZ);
		verts[3] = new Vec3(aabb.minX, aabb.maxY, aabb.maxZ);
		verts[4] = new Vec3(aabb.maxX, aabb.minY, aabb.minZ);
		verts[5] = new Vec3(aabb.maxX, aabb.minY, aabb.maxZ);
		verts[6] = new Vec3(aabb.maxX, aabb.maxY, aabb.minZ);
		verts[7] = new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ);

		double cosA = Math.cos(Math.toRadians(rotZ)), sinA = Math.sin(Math.toRadians(rotZ));
		double cosB = Math.cos(Math.toRadians(rotY)), sinB = Math.sin(Math.toRadians(rotY));
		double cosC = Math.cos(Math.toRadians(rotX)), sinC = Math.sin(Math.toRadians(rotX));

		double Axx = cosA * cosB,
				Axy = cosA * sinB * sinC - sinA * cosC,
				Axz = cosA * sinB * cosC + sinA * sinC;

		double Ayx = sinA * cosB,
				Ayy = sinA * sinB * sinC + cosA * cosC,
				Ayz = sinA * sinB * cosC - cosA * sinC;

		double Azx = -sinB,
				Azy = cosB * sinC,
				Azz = cosB * cosC;

		for(int i = 0; i < 8; i ++){
			Vec3 p = verts[i];

			this.verts[i] = new Vec3(
					(Axx * p.x + Axy * p.y + Axz * p.z) + this.pos.x,
					(Ayx * p.x + Ayy * p.y + Ayz * p.z) + this.pos.y,
					(Azx * p.x + Azy * p.y + Azz * p.z) + this.pos.z
			);
		}

		//Calculate axes
		this.axes[0] = verts[0].subtract(verts[1]).normalize();
		this.axes[1] = verts[0].subtract(verts[2]).normalize();
		this.axes[2] = verts[0].subtract(verts[4]).normalize();
	}

	@Override
	public Vec3[] getAxes() {
		return this.axes;
	}

	@Override
	public Vec3[] getVerts(){
		return this.verts;
	}
}
