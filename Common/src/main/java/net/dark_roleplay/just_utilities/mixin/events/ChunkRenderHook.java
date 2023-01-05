package net.dark_roleplay.just_utilities.mixin.events;

import net.dark_roleplay.just_utilities.impl.events.EventHooks;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import net.minecraft.core.BlockPos;
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
		EventHooks.chunkRenderHook((ChunkRenderDispatcher.RenderChunk)(Object)this);
	}
}
