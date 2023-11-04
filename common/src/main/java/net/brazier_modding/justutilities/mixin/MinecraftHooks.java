package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.ClientLifecycleHooks;
import net.brazier_modding.justutilities.events.hooks.LifecycleHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftHooks {

	@Inject(
			method = "setLevel",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;",
					shift = At.Shift.BEFORE
			)
	)
	private void justutilities_setLevel(ClientLevel newLevel, CallbackInfo callback) {
		LifecycleHooks.changeLevelHook(Minecraft.getInstance().level, newLevel);
	}

	@Inject(
			method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;",
					shift = At.Shift.BEFORE
			)
	)
	private void justutilities_setLevel(Screen screen, CallbackInfo callback) {
		LifecycleHooks.changeLevelHook(Minecraft.getInstance().level, null);
	}

	@Inject(
			method = "tick",
			at = @At(value = "HEAD")
	)
	private void justutilities_clientTickPre(CallbackInfo ci) {
		ClientLifecycleHooks.clientTickHook(true);
	}

	@Inject(
			method = "tick",
			at = @At(value = "TAIL")
	)
	private void justutilities_clientTickPost(CallbackInfo ci) {
		ClientLifecycleHooks.clientTickHook(false);
	}


	@Inject(
			method = "handleKeybinds()V",
			at = @At(value = "TAIL")
	)
	private void justutilities_handleKeybinds(CallbackInfo callback) {
		ClientLifecycleHooks.handleKeybindsHook();
	}
}
