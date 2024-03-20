package net.brazier_modding.justutilities.events.hooks.client;

import com.google.common.collect.ImmutableMap;
import net.brazier_modding.justutilities.events.Events;
import net.brazier_modding.justutilities.events.event_types.client.CreativeModeTabGatherItemsEvent;
import net.brazier_modding.justutilities.events.event_types.client.RegisterEntityRenderersEvent;
import net.brazier_modding.justutilities.events.event_types.client.RenderTypeLookupsEvent;
import net.brazier_modding.justutilities.mixin.accessors.ItemBlockRenderTypesAccessor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Map;
import java.util.function.BiConsumer;

public class ClientLifecycleHooks{

	public static final void postBootstrap() {

	}

	public static void registerEntityRenderers(BiConsumer<EntityType<?>, EntityRendererProvider<?>> registerCallback){
		RegisterEntityRenderersEvent event = new RegisterEntityRenderersEvent(registerCallback);
		Events.Init.CLIENT_REGISTER_ENTITY_RENDERERS.postEvent(event);
	}

	public static final void postRegistryBootstrap(){}

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
