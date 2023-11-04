package net.brazier_modding.justutilities.physics.collision;

import net.minecraft.world.phys.Vec3;

//An implementation of the Seperating Axis Theorem for bounding boxes only
public class CollisionChecker {

	public static CollissionInformation checkCollision(IBoundingBox a, IBoundingBox b){
		Vec3[] axes = a.getCollisionAxes(b);
		CollissionInformation info = hasOverlap(axes, b.getVerts(), a.getVerts());
		return info != null ? info : hasOverlap(axes, a.getVerts(), b.getVerts());
	}

	private static CollissionInformation hasOverlap(Vec3[] axes, Vec3[] bVerts, Vec3[] aVerts){
		double minOverlap = Double.POSITIVE_INFINITY;
		Vec3 smallestAxis = null;

		for(int i = 0; i < axes.length; i++){
			double bProjMin = Float.MAX_VALUE, aProjMin = Float.MAX_VALUE;
			double bProjMax= Float.MIN_VALUE, aProjMax = Float.MIN_VALUE;

			Vec3 axis = axes[i];

			if(axis == Vec3.ZERO)
				continue;
				//return new Overlap(smallestAxis, minOverlap);

			for(int j = 0; j < bVerts.length; j++){
				double val = bVerts[j].dot(axis);

				if(val < bProjMin) bProjMin = val;
				if(val > bProjMax) bProjMax = val;
			}

			for(int j = 0; j < bVerts.length; j++){
				double val = aVerts[j].dot(axis);

				if(val < aProjMin) aProjMin = val;
				if(val > aProjMax) aProjMax = val;
			}

			double overlap = getIntervalOverlap(aProjMin, aProjMax, bProjMin, bProjMax);

			double mins = Math.abs(aProjMin - bProjMin);
			double maxs = Math.abs(aProjMax - bProjMax);

			if(mins > maxs)
				axis = axis.reverse();

			if(overlap < minOverlap){
				minOverlap = overlap;
				smallestAxis = axis;
			}

			if(overlap <= 0) {
				return null;
			}
		}
		return new CollissionInformation(smallestAxis, minOverlap);
	}

	private static double getIntervalOverlap(double a1, double a2, double b1, double b2) {
		return a1 < b1 ?
				(a2 < b1 ?
						0 :
						(a2 - b1)) :
				(b2 < a1 ?
						0 :
						b2 - a1);
	}
}
