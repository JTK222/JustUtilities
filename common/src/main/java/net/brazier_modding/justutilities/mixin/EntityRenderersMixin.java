package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.ClientLifecycleHooks;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {

	@Shadow
	protected static void register(EntityType<?> entityType, EntityRendererProvider<?> renderProvider){}

	@Inject(
			method = "<clinit>()V",
			at = @At(value = "TAIL")
	)
	private static void justutilities_registerEntityRenderers(CallbackInfo callback) {
		ClientLifecycleHooks.registerEntityRenderers(EntityRenderersMixin::register);
	}
}
