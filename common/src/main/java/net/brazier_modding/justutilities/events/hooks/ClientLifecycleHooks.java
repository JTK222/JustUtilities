package net.brazier_modding.justutilities.events.hooks;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.brazier_modding.justutilities.events.Events;
import net.brazier_modding.justutilities.events.event_types.client.*;
import net.brazier_modding.justutilities.mixin.accessors.ItemBlockRenderTypesAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ClientLifecycleHooks {

	public static final void postBootstrapHook() {

	}

	public static void registerEntityRenderers(BiConsumer<EntityType<?>, EntityRendererProvider<?>> registerCallback){
		RegisterEntityRenderersEvent event = new RegisterEntityRenderersEvent(registerCallback);
		Events.Init.CLIENT_REGISTER_ENTITY_RENDERERS.postEvent(event);
	}

	public static void hudRenderHook(PoseStack poseStack, float partialTicks){
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



	public static void clientTickHook(boolean pre) {
		if(pre) Events.Runtime.CLIENT_TICK_PRE.postEvent(null);
		else Events.Runtime.CLIENT_TICK_POST.postEvent(null);
	}


	public static void chunkRenderHook(ChunkRenderDispatcher.RenderChunk chunk) {
		ChunkRenderEvent event = new ChunkRenderEvent();
		event.set(chunk);
		Events.Runtime.CHUNK_RENDER.postEvent(event);
	}

	public static final Set<KeyMapping> gatherKeyMappingsHook(){
		RegisterKeybindsEvent event = new RegisterKeybindsEvent();
		Events.Init.CLIENT_REGISTER_KEYBINDS.postEvent(event);

		Set<KeyMapping> keys = event.getObjects();

		return keys;
	}

	public static final void handleKeybindsHook(){
		Events.Runtime.CLIENT_HANDLE_KEYBINDS.postEvent(null);
	}

	public static final void postRegistryBootstrapHook(){}

	public static final void registerRenderLayers(){
		RenderTypeLookupsEvent event = new RenderTypeLookupsEvent();
		Events.Init.CLIENT_RENDER_TYPE_LOOKUPS.postEvent(event);

		ImmutableMap<ResourceKey<Block>, RenderType> blockRenderTypes = event.getBlockLookups();
		Map<Block, RenderType> blockLookups = ItemBlockRenderTypesAccessor.justutilities_getTypeByBlock();

		for(ResourceKey<Block> block : blockRenderTypes.keySet()){
			blockLookups.put(BuiltInRegistries.BLOCK.get(block), blockRenderTypes.get(block));
		}

		ImmutableMap<ResourceKey<Fluid>, RenderType> fluidRenderTypes = event.getFluidLookups();
		Map<Fluid, RenderType> fluidLookups = ItemBlockRenderTypesAccessor.justutilities_getTypeByFluid();

		for(ResourceKey<Fluid> fluid : fluidRenderTypes.keySet()){
			fluidLookups.put(BuiltInRegistries.FLUID.get(fluid), fluidLookups.get(fluid));
		}
	}

	public static final void creativeModeTabGatherItems(CreativeModeTab tab, CreativeModeTab.ItemDisplayParameters displayParameters, CreativeModeTab.Output output){
		ResourceKey<CreativeModeTab> resourcekey = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).orElseThrow(() -> new IllegalStateException("Unregistered creative tab: " + tab));

		CreativeModeTabGatherItemsEvent event = new CreativeModeTabGatherItemsEvent(resourcekey, output, displayParameters);
		Events.Runtime.CREATIVE_MODE_TABS_GATHER_ITEMS.postEvent(event);
	}


}