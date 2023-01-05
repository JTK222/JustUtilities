package net.dark_roleplay.just_utilities.mixin.accessors;

import net.minecraft.world.level.material.MaterialColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MaterialColor.class)
public interface MaterialColorAccessor {

	@Accessor("MATERIAL_COLORS")
	static MaterialColor[] justutilities_getMaterialColors(){ throw new AssertionError(); }
}
