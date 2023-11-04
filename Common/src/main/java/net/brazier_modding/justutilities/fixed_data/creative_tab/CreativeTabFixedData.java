package net.brazier_modding.justutilities.fixed_data.creative_tab;

import net.brazier_modding.justutilities.fixed_data.FixedData;
import net.brazier_modding.justutilities.util.either_codec_registry.CodecDispatchRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class CreativeTabFixedData extends FixedData {

	private static Map<ResourceLocation, CreativeModeTab> TABS = new HashMap<>();

	static{
//		TABS.put(new ResourceLocation("minecraft", "building_blocks"), CreativeModeTab.TAB_BUILDING_BLOCKS);
//		TABS.put(new ResourceLocation("minecraft", "decorations"), CreativeModeTab.TAB_DECORATIONS);
//		TABS.put(new ResourceLocation("minecraft", "redstone"), CreativeModeTab.TAB_REDSTONE);
//		TABS.put(new ResourceLocation("minecraft", "transportation"), CreativeModeTab.TAB_TRANSPORTATION);
//		TABS.put(new ResourceLocation("minecraft", "misc"), CreativeModeTab.TAB_MISC);
//		TABS.put(new ResourceLocation("minecraft", "food"), CreativeModeTab.TAB_FOOD);
//		TABS.put(new ResourceLocation("minecraft", "tools"), CreativeModeTab.TAB_TOOLS);
//		TABS.put(new ResourceLocation("minecraft", "combat"), CreativeModeTab.TAB_COMBAT);
//		TABS.put(new ResourceLocation("minecraft", "brewing"), CreativeModeTab.TAB_BREWING);
	}

	public static final CodecDispatchRegistry<BiFunction<Integer, ResourceLocation, CreativeModeTab>> CREATIVE_TYPE_PROVIDERS = new CodecDispatchRegistry<>();
	static{
//		CREATIVE_TYPE_PROVIDERS.register(new CodecDispatchType(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "basic_tab"), BasicTabProvider.class, BasicTabProvider.CODEC));
	}

	public static void load(){
//		String[] mods = JustUtilitiesConstants.MOD_PROVIDER.getAvailableMods();
//
//		TABS.clear();
//
//		for(String modid : mods){
//			ModFilePack pack = ModFilePack.getPackForMod(modid);
//
//			Collection<ResourceLocation> tabLocations = pack.getResources(modid, "creative_tabs/", Integer.MAX_VALUE, file -> file.endsWith(".json"));
//			if(tabLocations.isEmpty()) continue;
//
//			CreativeModeTab[] tabs = CreativeTabAccessor.getCreativeTas();
//			int id = tabs.length;
//			CreativeModeTab[] newTabs = new CreativeModeTab[id + tabLocations.size()];
//			for(int i = 0; i < id; i++)
//				newTabs[i] = tabs[i];
//			CreativeTabAccessor.setCreativeTabs(newTabs);
//
//			for(ResourceLocation tabLocation : tabLocations){
//				try {
//					ResourceLocation registryName = dataLocToRegName(tabLocation);
//					InputStreamReader itemStream = new InputStreamReader(pack.getResource(tabLocation));
//
//					DataResult<BiFunction<Integer, ResourceLocation, CreativeModeTab>> result = CREATIVE_TYPE_PROVIDERS.getCodec().parse(JsonOps.INSTANCE, JsonParser.parseReader(itemStream));
//					BiFunction<Integer, ResourceLocation, CreativeModeTab> provider = result.getOrThrow(false, LOGGER::warn);
//
//					TABS.put(registryName, provider.apply(id++, registryName));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

	public static CreativeModeTab getTab(ResourceLocation name){
		if(!TABS.containsKey(name))
			LOGGER.error("Tried to query missing creative Tab {}", name);
		return TABS.get(name);
	}
}
