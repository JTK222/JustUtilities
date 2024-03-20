package net.brazier_modding.justutilities.events.hooks.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.brazier_modding.justutilities.events.Events;
import net.brazier_modding.justutilities.events.event_types.client.ChunkRenderEvent;
import net.brazier_modding.justutilities.events.event_types.client.HudPostRenderEvent;
import net.brazier_modding.nails_and_screws.DecorInstance;
import net.brazier_modding.nails_and_screws.DecorProvider;
import net.brazier_modding.nails_and_screws.client.DecorModelRepository;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import org.joml.Vector3f;

import java.util.List;
import java.util.Set;

public class ClientRenderHooks {

	public static void hudRender(PoseStack poseStack, float partialTicks){
		Window window = Minecraft.getInstance().getWindow();
		int screenWidth = window.getGuiScaledWidth();
		int screenHeight = window.getGuiScaledHeight();

		GuiGraphics gfx = new GuiGraphics(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());

		HudPostRenderEvent event = new HudPostRenderEvent();
		event.setPoseStack(poseStack);
		event.setPartialTicks(partialTicks);
		event.setDimensions(screenWidth, screenHeight);
		event.setGraphics(gfx);
		Events.Runtime.HUD_POST_RENDER.postEvent(event);
	}

	public static void chunkRender(ChunkRenderDispatcher.RenderChunk chunk) {
		ChunkRenderEvent event = new ChunkRenderEvent();
		event.set(chunk);
		Events.Runtime.CHUNK_RENDER.postEvent(event);
	}

	//TODO Extract into Events
	public static void bakeChunkGeometry(ChunkBufferBuilderPack chunkBufferBuilderPack, RenderChunkRegion chunkAccess, PoseStack poseStack, RandomSource random, BlockRenderDispatcher renderDispatcher, Set renderTypeSet, VisGraph visgraph, BlockPos chunkPos){
		//if(!chunkPos.equals(new BlockPos(0, 64, 0))) return;

		BufferBuilder buffer = chunkBufferBuilderPack.builder(RenderType.cutout());

		List<DecorInstance> decorations = DecorProvider.getDecorationsInChunk(chunkAccess, chunkPos);

		if(renderTypeSet.add(RenderType.cutout())){
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
		}

		poseStack.pushPose();
		for(DecorInstance decor : decorations){
			BlockPos decorPos = decor.getGlobalPos();
			Vector3f localPos = decor.getLocalPos();

			float offsetX = decorPos.getX() - chunkPos.getX() + localPos.x;
			float offsetY = decorPos.getY() - chunkPos.getY() + localPos.y;
			float offsetZ = decorPos.getZ() - chunkPos.getZ() + localPos.z;

			poseStack.translate(offsetX, offsetY, offsetZ);

			int brightness = chunkAccess.getBrightness(LightLayer.SKY, decorPos) << 20 | chunkAccess.getBrightness(LightLayer.BLOCK, decorPos) << 4;
			renderDecor(decor, buffer, renderDispatcher, poseStack.last(), brightness);

			poseStack.translate(-offsetX, -offsetY, -offsetZ);
		}
		poseStack.popPose();

		//visgraph.setOpaque(chunkPos);
	}

	public static void renderDecor(DecorInstance decor, BufferBuilder buffer, BlockRenderDispatcher renderDispatcher, PoseStack.Pose pose, int lightCoordinate){
		ModelResourceLocation modelLoc = DecorModelRepository.getModelPathFromDecor(decor.getDecor());
		BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelLoc);
//		BakedModel model = renderDispatcher.getBlockModel(Blocks.ANVIL.defaultBlockState());


		renderDispatcher.getModelRenderer().renderModel(
				pose, buffer, null, model,
				1F, 1F, 1F, lightCoordinate,
				OverlayTexture.NO_OVERLAY
		);
	}
}
