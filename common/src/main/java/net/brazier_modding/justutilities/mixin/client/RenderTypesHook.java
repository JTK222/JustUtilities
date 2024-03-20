package net.brazier_modding.justutilities.mixin.client;

import net.brazier_modding.justutilities.events.hooks.client.ClientLifecycleHooks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemBlockRenderTypes.class)
public class RenderTypesHook {

	@Inject(
			method = "<clinit>()V",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/ItemBlockRenderTypes;TYPE_BY_BLOCK:Ljava/util/Map;", opcode = 179, shift = At.Shift.AFTER)
	)
	private static void justutilities_registerRenderTypes(CallbackInfo callback) {
		ClientLifecycleHooks.registerRenderLayers();
	}
}
