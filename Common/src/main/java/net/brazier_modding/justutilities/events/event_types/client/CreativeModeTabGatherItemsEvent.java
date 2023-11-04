package net.brazier_modding.justutilities.events.event_types.client;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeModeTabGatherItemsEvent {
	ResourceKey<CreativeModeTab> tab;
	CreativeModeTab.Output itemDisplayBuilder;
	CreativeModeTab.ItemDisplayParameters itemDisplayParameters;

	public CreativeModeTabGatherItemsEvent(ResourceKey<CreativeModeTab> tab, CreativeModeTab.Output itemDisplayBuilder, CreativeModeTab.ItemDisplayParameters itemDisplayParameters) {
		this.tab = tab;
		this.itemDisplayBuilder = itemDisplayBuilder;
		this.itemDisplayParameters = itemDisplayParameters;
	}

	public ResourceKey<CreativeModeTab> getTab() {
		return tab;
	}

	public CreativeModeTab.Output getItemDisplayBuilder() {
		return itemDisplayBuilder;
	}

	public CreativeModeTab.ItemDisplayParameters getItemDisplayParameters() {
		return itemDisplayParameters;
	}
}
