package net.brazier_modding.justutilities.physics.collision;

import net.minecraft.world.phys.Vec3;

public interface IBoundingBox {
	Vec3[] getAxes();

	Vec3[] getVerts();

	Vec3 getPos();
	void setPos(Vec3 pos);
	void move(Vec3 pos);

	default Vec3[] getCollisionAxes(IBoundingBox other){
		Vec3[] a = this.getAxes();
		Vec3[] b = other.getAxes();
		return new Vec3[]{
				a[0],
				a[1],
				a[2],
				b[0],
				b[1],
				b[2],
				a[0].cross(b[0]).normalize(),
				a[0].cross(b[1]).normalize(),
				a[0].cross(b[2]).normalize(),
				a[1].cross(b[0]).normalize(),
				a[1].cross(b[1]).normalize(),
				a[1].cross(b[2]).normalize(),
				a[2].cross(b[0]).normalize(),
				a[2].cross(b[1]).normalize(),
				a[2].cross(b[2]).normalize(),
		};
	}
}
