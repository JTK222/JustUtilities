package net.brazier_modding.justutilities.fixed_data.items;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.Optional;

public class ItemCodecs {
	public static final Codec<Rarity> RARITY = Codec.either(Codec.STRING, Codec.INT).comapFlatMap(
			either -> either.map(
					str -> {
						Rarity rar = Rarity.valueOf(str);
						return rar != null ? DataResult.success(rar) : DataResult.error(() -> "Unknown rarity value name: " + str);
					},
					num -> {
						Rarity[] values = Rarity.values();
						return num >= 0 && num < values.length ? DataResult.success(values[num]) : DataResult.error(() -> "Unknown rarity id: " + num);
					}
			),
			value -> Either.left(value.name())
	);

	public static final Codec<FoodProperties> FOOD = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("nutrition").forGetter(obj -> obj.getNutrition()),
			Codec.FLOAT.fieldOf("saturation").forGetter(obj -> obj.getSaturationModifier()),
			Codec.BOOL.optionalFieldOf("isMeat", false).forGetter(obj -> obj.isMeat()),
			Codec.BOOL.optionalFieldOf("canAlwaysEat", false).forGetter(obj -> obj.canAlwaysEat()),
			Codec.BOOL.optionalFieldOf("canEatQuick", false).forGetter(obj -> obj.isFastFood())
	).apply(instance, (nutrition, saturation, isMeat, canAlwaysEat, canEatQuick) -> {
		FoodProperties.Builder builder = new FoodProperties.Builder();
		builder.nutrition(nutrition);
		builder.saturationMod(saturation);
		if (isMeat) builder.meat();
		if (canAlwaysEat) builder.alwaysEat();
		if (canEatQuick) builder.fast();

		return builder.build();
	}));

	public static final MapCodec<Item.Properties> ITEM_PROPERTIES = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.optionalFieldOf("maxStackSize", 64).forGetter(o -> 64),
			Codec.INT.optionalFieldOf("durability", 0).forGetter(o -> 0),
			Codec.BOOL.optionalFieldOf("fireResistant", false).forGetter(o -> false),
//			Codec.of(Encoder.<CreativeModeTab>empty().encoder(), ResourceLocation.CODEC.map(CreativeTabFixedData::getTab)).optionalFieldOf("creativeTab").forGetter(o -> Optional.empty()),
			FOOD.optionalFieldOf("food").forGetter(o -> Optional.empty()),
			RARITY.optionalFieldOf("rarity").forGetter(o -> Optional.empty())
			//TODO craftingRemainingItem
	).apply(instance, (count, durability, fireResistant/*, creativeTab*/, food, rarity) -> {
		Item.Properties props = new Item.Properties();

		if (count != 64) props.stacksTo(count);
		if (durability != 0) props.durability(durability);
		if (fireResistant) props.fireResistant();
		if (food.isPresent()) props.food(food.get());
		if(rarity.isPresent()) props.rarity(rarity.get());
//		if(creativeTab.isPresent()) props.tab(creativeTab.get());

		return props;
	}));

	public static final MapCodec<Item> ITEM = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ItemCodecs.ITEM_PROPERTIES.codec().optionalFieldOf("properties", new Item.Properties()).forGetter(obj -> null)
	).apply(instance, (properties) -> new Item(properties)));

	public static final MapCodec<BlockItem> BLOCK_ITEM = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResourceLocation.CODEC.fieldOf("block").forGetter(obj -> null),
			ItemCodecs.ITEM_PROPERTIES.codec().optionalFieldOf("properties", new Item.Properties()).forGetter(obj -> null)
	).apply(instance, (block, properties) -> new BlockItem(BuiltInRegistries.BLOCK.get(block), properties)));
}
