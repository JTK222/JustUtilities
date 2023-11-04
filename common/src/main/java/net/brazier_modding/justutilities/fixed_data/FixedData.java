package net.brazier_modding.justutilities.fixed_data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.brazier_modding.justutilities.JustUtilitiesConstants;
import net.brazier_modding.justutilities.resources.ModFilePack;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.function.BiConsumer;

public class FixedData {
	protected static final Gson GSON = new GsonBuilder().create();
	protected static final Logger LOGGER = LogManager.getLogger();

	protected static ResourceLocation dataLocToRegName(ResourceLocation dataLoc){
		String[] splitPath = dataLoc.getPath().split("/");
		return new ResourceLocation(dataLoc.getNamespace(), splitPath[splitPath.length-1].replace(".json", ""));
	}

	public static void load(String resourcePath, BiConsumer<ResourceLocation, ModFilePack> processor) {
		String[] mods = JustUtilitiesConstants.MOD_PROVIDER.getAvailableMods();

		JustUtilitiesConstants.MOD_PROVIDER.snapshotLoadingContext();
		for (String modid : mods) {
			JustUtilitiesConstants.MOD_PROVIDER.setLoadingContext(modid);
			ModFilePack pack = ModFilePack.getPackForMod(modid);

			Collection<ResourceLocation> itemLocations = pack.getResources(modid, resourcePath, Integer.MAX_VALUE, file -> file.endsWith(".json"));
			if(itemLocations.isEmpty()) continue;

			for(ResourceLocation itemLocation : itemLocations) {
				processor.accept(itemLocation, pack);
			}
		}
		JustUtilitiesConstants.MOD_PROVIDER.resetLoadingContext();
	}
}
