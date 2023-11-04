package net.brazier_modding.justutilities.fixed_data.creative_tab.types;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class BasicTabProvider {

	public void test(ResourceLocation name){
		CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 0);
		builder.title(Component.translatable("tab." + name.getNamespace() + "." + name.getPath()));
//		builder.icon(

	}

}// implements BiFunction<Integer, ResourceLocation, CreativeModeTab>{
//	public static final MapCodec<BasicTabProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
//			ResourceLocation.CODEC.fieldOf("icon").forGetter(tab -> tab.icon)
//	).apply(instance, BasicTabProvider::new));
//
//	private ResourceLocation icon;
//
//	public BasicTabProvider(ResourceLocation icon){
//		this.icon = icon;
//	}
//
//	@Override
//	public CreativeModeTab apply(Integer id, ResourceLocation name) {
//		return new CreativeModeTab(id, name.getNamespace() + "." + name.getPath()){
//			@Override
//			public ItemStack makeIcon() {
//				return Registry.ITEM.get(icon).getDefaultInstance();
//			}
//		};
//	}
//}
