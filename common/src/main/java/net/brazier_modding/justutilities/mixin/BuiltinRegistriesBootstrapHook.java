package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.ClientLifecycleHooks;
import net.brazier_modding.justutilities.events.hooks.LifecycleHooks;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltInRegistries.class)
public class BuiltinRegistriesBootstrapHook {

	@Inject(
			method = "bootStrap()V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V", ordinal = 0)
	)
	private static void justutilities_bootstrap(CallbackInfo callback) {
		LifecycleHooks.registryBootstrapHook();
	}

	@Inject(
			method = "bootStrap()V",
			at = @At(value = "RETURN", ordinal = 0)
	)
	private static void justutilities_postBootstrap(CallbackInfo callback) {
		LifecycleHooks.postRegistryBootstrapHook();
		ClientLifecycleHooks.postRegistryBootstrapHook();
	}
}
