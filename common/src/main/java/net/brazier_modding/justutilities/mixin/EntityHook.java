package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.RuntimeHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public class EntityHook {

	@Shadow
	public Level level;

	@Shadow
	public final Vec3 getEyePosition(float $$0) {
		return null;
	}

	@Shadow
	public final Vec3 getViewVector(float $$0) {
		return null;
	}

	@Inject(
			method = "pick",
			at = @At(value = "RETURN"),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION,
			cancellable = true
	)
	private void justutilities_rayTraceFromEntity(double distance, float partialTick, boolean checkFluids, CallbackInfoReturnable<HitResult> cir) {
		Vec3 rayOrigin = this.getEyePosition(partialTick);
		Vec3 viewDirection = this.getViewVector(partialTick);
		Vec3 rayTarget = rayOrigin.add(viewDirection.x * distance, viewDirection.y * distance, viewDirection.z * distance);

		HitResult newHit = RuntimeHooks.entityRayTraceHook(this.level, rayOrigin, rayTarget, cir.getReturnValue());

		if(newHit != null)
			cir.setReturnValue(newHit);
	}
}
