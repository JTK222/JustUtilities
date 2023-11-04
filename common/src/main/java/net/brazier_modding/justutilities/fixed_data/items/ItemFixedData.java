package net.brazier_modding.justutilities.fixed_data.items;

import com.google.gson.JsonParser;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import net.brazier_modding.justutilities.JustUtilitiesConstants;
import net.brazier_modding.justutilities.fixed_data.FixedData;
import net.brazier_modding.justutilities.util.either_codec_registry.CodecDispatchRegistry;
import net.brazier_modding.justutilities.util.either_codec_registry.CodecDispatchType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.io.IOException;
import java.io.InputStreamReader;

public class ItemFixedData extends FixedData {

	public static final CodecDispatchRegistry<Item> ITEM_PROVIDERS = new CodecDispatchRegistry<>();

	static{
		ITEM_PROVIDERS.register(new CodecDispatchType(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "item"), Item.class, ItemCodecs.ITEM));
		ITEM_PROVIDERS.register(new CodecDispatchType(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "block_item"), BlockItem.class, ItemCodecs.BLOCK_ITEM));
	}

	public static void load() {
		String activeNamespace = "";
		FixedData.load("items/", (dataLocation, pack) -> {
			try(InputStreamReader dataStream = new InputStreamReader(pack.getResource(dataLocation))){
				ResourceLocation registryName = dataLocToRegName(dataLocation);

				DataResult<Item> result = ITEM_PROVIDERS.getCodec().parse(JsonOps.INSTANCE, JsonParser.parseReader(dataStream));
				Item item = result.getOrThrow(false, LOGGER::warn);

				JustUtilitiesConstants.MOD_PROVIDER.setLoadingContext(registryName.getNamespace());
				Registry.register(BuiltInRegistries.ITEM, registryName, item);
				JustUtilitiesConstants.MOD_PROVIDER.resetLoadingContext();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
