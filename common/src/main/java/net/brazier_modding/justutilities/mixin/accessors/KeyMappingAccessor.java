package net.brazier_modding.justutilities.mixin.accessors;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor {

	@Final
	@Accessor("CATEGORY_SORT_ORDER")
	static Map<String, Integer> justutilities_getKeybindCategories(){ throw new AssertionError(); }
}
