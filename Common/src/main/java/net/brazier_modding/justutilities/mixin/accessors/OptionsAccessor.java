package net.brazier_modding.justutilities.mixin.accessors;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Options.class)
public interface OptionsAccessor {
	@Mutable
	@Accessor("keyMappings")
	KeyMapping[] justutilities_getKeyMappings();

	@Mutable
	@Accessor("keyMappings")
	void justutilities_setKeyMappings(KeyMapping[] mappings);
}
