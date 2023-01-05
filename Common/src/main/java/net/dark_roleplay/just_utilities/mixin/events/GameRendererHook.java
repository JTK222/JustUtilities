package net.dark_roleplay.just_utilities.mixin.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dark_roleplay.just_utilities.impl.events.EventHooks;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererHook {

	@Inject(
			method = "render",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V", shift = At.Shift.AFTER)
	)
	private void just_utilities_render(float partialTicks, long $$1, boolean $$2, CallbackInfo callback) {
		EventHooks.hudRenderHook(new PoseStack(), partialTicks);
	}
}
