package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.ClientLifecycleHooks;
import net.brazier_modding.justutilities.events.hooks.LifecycleHooks;
import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapHook {

	@Inject(
			method = "bootStrap()V",
			at = @At(value = "HEAD")
	)
	private static void justutilities_bootstrap(CallbackInfo callback) {
		LifecycleHooks.bootstrapHook();
	}

	@Inject(
			method = "bootStrap()V",
			at = @At(value = "INVOKE", target="Ljava/time/Instant;now()Ljava/time/Instant;", shift = At.Shift.AFTER) //putstatic
	)
	private static void justutilities_bootstrapStep2(CallbackInfo callback) {
	}

	@Inject(
			method = "bootStrap()V",
			at = @At(value = "RETURN")
	)
	private static void justutilities_postBootstrap(CallbackInfo callback) {
		LifecycleHooks.postBootstrapHook();
		ClientLifecycleHooks.postBootstrapHook();
	}
}
