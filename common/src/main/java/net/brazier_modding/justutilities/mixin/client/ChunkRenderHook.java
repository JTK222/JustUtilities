package net.brazier_modding.justutilities.mixin.client;

import net.brazier_modding.justutilities.events.hooks.ClientLifecycleHooks;
import net.brazier_modding.justutilities.events.hooks.LifecycleHooks;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRenderDispatcher.RenderChunk.class)
public class ChunkRenderHook {

	@Inject(
			method = "createCompileTask",
			at = @At(value = "TAIL")
	)
	private void just_utilities_compileChunk(RenderRegionCache regionCahge, CallbackInfoReturnable<?> cir) {
		ClientLifecycleHooks.chunkRenderHook((ChunkRenderDispatcher.RenderChunk)(Object)this);
	}
}
