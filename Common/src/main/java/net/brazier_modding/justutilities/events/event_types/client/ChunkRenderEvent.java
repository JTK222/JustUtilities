package net.brazier_modding.justutilities.events.event_types.client;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;

public class ChunkRenderEvent {

	private BlockPos chunkOrigin;
	private ChunkRenderDispatcher.RenderChunk renderChunk;

	public BlockPos getChunkOrigin() {
		return chunkOrigin;
	}

	public ChunkRenderDispatcher.RenderChunk getRenderChunk() {
		return renderChunk;
	}

	public void set(ChunkRenderDispatcher.RenderChunk renderChunk) {
		this.chunkOrigin = renderChunk.getOrigin().immutable();
		this.renderChunk = renderChunk;
	}
}
