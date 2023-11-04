package net.brazier_modding.justutilities.events.event_types.client;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

public class RenderTypeLookupsEvent {

	private final Map<ResourceKey<Block>, RenderType> blockLookups = new HashMap<>();
	private final Map<ResourceKey<Fluid>, RenderType> fluidLookups = new HashMap<>();

	public void setBlockRenderType(ResourceKey<Block> block, RenderType renderType){
		this.blockLookups.put(block, renderType);
	}

	public void setFluidRenderType(ResourceKey<Fluid> fluid, RenderType renderType){
		this.fluidLookups.put(fluid, renderType);
	}

	public ImmutableMap<ResourceKey<Block>, RenderType> getBlockLookups(){
		return ImmutableMap.copyOf(this.blockLookups);
	}

	public ImmutableMap<ResourceKey<Fluid>, RenderType> getFluidLookups(){
		return ImmutableMap.copyOf(this.fluidLookups);
	}
}
