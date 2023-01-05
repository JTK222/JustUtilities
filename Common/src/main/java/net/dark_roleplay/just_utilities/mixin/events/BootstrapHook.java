package net.dark_roleplay.just_utilities.mixin.events;

import net.dark_roleplay.just_utilities.impl.events.EventHooks;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltInRegistries.class)
public class BootstrapHook {
	
	@Inject(
			method = "bootStrap()V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V", ordinal = 0)
	)
	private static void just_utilities_bootstrap(CallbackInfo callback) {
		EventHooks.bootstrapHook();
	}
}
