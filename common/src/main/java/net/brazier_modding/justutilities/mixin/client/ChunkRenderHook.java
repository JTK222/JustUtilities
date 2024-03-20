package net.brazier_modding.justutilities.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.brazier_modding.justutilities.events.hooks.client.ClientRenderHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Set;

@Mixin(targets="net/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk$RebuildTask") //ChunkRenderDispatcher.RenderChunk.class)
public class ChunkRenderHook {

	@Inject(
			method = "Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk$RebuildTask;compile(FFFLnet/minecraft/client/renderer/ChunkBufferBuilderPack;)Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk$RebuildTask$CompileResults;",
			at = @At(
					value = "INVOKE",
					target="Lnet/minecraft/client/Minecraft;getBlockRenderer()Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;",
					ordinal = 0
			),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	private void just_utilities_compileChunk(
			float xIn, float yIn, float zIn,
			ChunkBufferBuilderPack bufferBuilders,
			CallbackInfoReturnable<ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults> cir,
			ChunkRenderDispatcher.RenderChunk.RebuildTask.CompileResults $$4,
			int i, BlockPos blockPos, BlockPos blockPos1,
			VisGraph visgraph,
			RenderChunkRegion chunk, PoseStack poseStack,
			Set renderTypeSet, RandomSource random) {


		BlockRenderDispatcher renderDispatcher = Minecraft.getInstance().getBlockRenderer();
		ClientRenderHooks.bakeChunkGeometry(bufferBuilders, chunk, poseStack, random, renderDispatcher, renderTypeSet, visgraph, blockPos);


//		ClientLifecycleHooks.chunkRenderHook((ChunkRenderDispatcher.RenderChunk)(Object)this);
	}
}
