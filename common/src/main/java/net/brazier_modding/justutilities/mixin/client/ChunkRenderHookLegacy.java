package net.brazier_modding.justutilities.mixin.client;

import net.brazier_modding.justutilities.events.hooks.client.ClientRenderHooks;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRenderDispatcher.RenderChunk.class)
public class ChunkRenderHookLegacy {

	@Inject(
			method = "createCompileTask",
			at = @At(value = "TAIL")
	)
	private void just_utilities_compileChunk(RenderRegionCache regionCahge, CallbackInfoReturnable<?> cir) {
		ClientRenderHooks.chunkRender((ChunkRenderDispatcher.RenderChunk)(Object)this);
	}
}
