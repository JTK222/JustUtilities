package net.brazier_modding.justutilities.mixin;

import com.google.common.collect.ImmutableList;
import net.brazier_modding.justutilities.events.hooks.RuntimeHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(Entity.class)
public class EntityCollisionHook {

	@Inject(
			method = "collideBoundingBox",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Entity;collideWithShapes(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/List;)Lnet/minecraft/world/phys/Vec3;",
					ordinal = 0,
					shift = At.Shift.BEFORE
			),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private static void justutilities_move(@Nullable Entity entity, Vec3 movementVec, AABB aabb, Level level, List<VoxelShape> shapes, CallbackInfoReturnable<Vec3> cir, ImmutableList.Builder<VoxelShape> builder) {
		RuntimeHooks.entityCollisionHook(entity, movementVec, aabb, level, shapes, builder);
	}
}
