package net.brazier_modding.justutilities.events.hooks;

import com.google.common.collect.ImmutableList;
import net.brazier_modding.nails_and_screws.DecorHitResult;
import net.brazier_modding.nails_and_screws.DecorInstance;
import net.brazier_modding.nails_and_screws.DecorProvider;
import net.brazier_modding.justutilities.events.Events;
import net.brazier_modding.justutilities.events.event_types.ChunkIOEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RuntimeHooks {

	//TODO Extract this into standalone mod
	public static void entityCollisionHook(@Nullable Entity entity, Vec3 movementVec, AABB aabb, Level level, List<VoxelShape> shapes, ImmutableList.Builder<VoxelShape> builder) {
		Entity e = entity;
		Iterable<DecorInstance> decorations = DecorProvider.getDecorations(Minecraft.getInstance().level, aabb.expandTowards(movementVec).inflate(1));
		List<VoxelShape> shapes2 = StreamSupport.stream(decorations.spliterator(), false).map(deco ->{
			VoxelShape shape = deco.getShape();
			BlockPos globalPos = deco.getGlobalPos();
			Vector3f localPos = deco.getLocalPos();
			return shape.move(globalPos.getX() + localPos.x, globalPos.getY() + localPos.y, globalPos.getZ() + localPos.z);
		}).collect(Collectors.toList());

		builder.addAll(shapes2);
	}


	public static void readChunk(ServerLevel level, ChunkPos pos){
		Events.Runtime.CHUNK_READ_EVENT.postEvent(new ChunkIOEvent(level, pos));
	}

	public static void writeChunk(ServerLevel level, ChunkPos pos){
		Events.Runtime.CHUNK_WRITE_EVENT.postEvent(new ChunkIOEvent(level, pos));

	}

	//TODO Extract into Event and standalone mod
	public static HitResult entityRayTraceHook(Level level, Vec3 rayOrigin, Vec3 rayTarget, HitResult hitResult){
		Vec3 direction = rayOrigin.subtract(rayTarget);

		Iterable<DecorInstance> decorations = DecorProvider.getDecorations(Minecraft.getInstance().level, new AABB(rayOrigin, rayTarget).inflate(1));

		HitResult nearestHit = hitResult.getType() == HitResult.Type.MISS ? null : hitResult;
		double smallestDistance = hitResult.getType() == HitResult.Type.MISS ? Double.MAX_VALUE : rayOrigin.distanceTo(hitResult.getLocation());
		DecorInstance hitDecor = null;
		Direction hitDir = null;

		for(DecorInstance decor : decorations){
			VoxelShape shape = decor.getShape();

			//TODO Do actual raytrace
			Vec3i globalPos = decor.getGlobalPos();
			Vector3f localPos = decor.getLocalPos();

			BlockHitResult currentHit = shape.move(localPos.x, localPos.y, localPos.z).clip(rayOrigin, rayTarget, new BlockPos(globalPos));
			if(currentHit == null) continue;
			if(nearestHit == null ||
					(nearestHit.getType() != HitResult.Type.MISS && smallestDistance > rayOrigin.distanceTo(currentHit.getLocation()))){
				nearestHit = currentHit;
				smallestDistance = rayOrigin.distanceTo(currentHit.getLocation());
				hitDecor = decor;
				hitDir = currentHit.getDirection();
			}
		}

		if(nearestHit != null && nearestHit != hitResult)
			return new DecorHitResult(rayOrigin, hitDir, hitDecor.getGlobalPos(), 0);

		return null;
	}
}
