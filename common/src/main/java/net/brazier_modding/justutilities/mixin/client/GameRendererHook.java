package net.brazier_modding.justutilities.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.brazier_modding.justutilities.events.hooks.client.ClientRenderHooks;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererHook {

	@Inject(
			method = "render",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;render(Lnet/minecraft/client/gui/GuiGraphics;F)V", shift = At.Shift.AFTER)
	)
	private void justutilities_render(float partialTicks, long $$1, boolean $$2, CallbackInfo callback) {
		ClientRenderHooks.hudRender(new PoseStack(), partialTicks);
	}
}
