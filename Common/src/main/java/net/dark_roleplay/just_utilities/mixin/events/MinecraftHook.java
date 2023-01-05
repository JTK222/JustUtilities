package net.dark_roleplay.just_utilities.mixin.events;

import net.dark_roleplay.just_utilities.impl.events.EventHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftHook {

	@Inject(
			method = "<init>",
			at = @At(value = "TAIL")
	)
	private void just_utilities_render(GameConfig cfg, CallbackInfo callback) {
		EventHooks.clientInitHook();
	}

	@Inject(
			method = "setLevel",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;",
					shift = At.Shift.BEFORE
			)
	)
	private void setLevel(ClientLevel newLevel, CallbackInfo callback) {
		EventHooks.changeLevelHook(Minecraft.getInstance().level, newLevel);
	}

	@Inject(
			method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;",
					shift = At.Shift.BEFORE
			)
	)
	private void just_utilities_setLevel(Screen screen, CallbackInfo callback) {
		EventHooks.changeLevelHook(Minecraft.getInstance().level, null);
	}


	@Inject(
			method = "tick",
			at = @At(value = "HEAD")
	)
	private void just_utilities_clientTickPre(CallbackInfo ci){
		EventHooks.clientTickHook(true);
	}

	@Inject(
			method = "tick",
			at = @At(value = "TAIL")
	)
	private void just_utilities_clientTickPost(CallbackInfo ci){
		EventHooks.clientTickHook(false);
	}
}
