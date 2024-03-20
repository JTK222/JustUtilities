package net.brazier_modding.justutilities.mixin.accessors;

import com.mojang.serialization.Lifecycle;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BuiltInRegistries.class)
public interface RegistryAccessor {

	@Final
	@Accessor("WRITABLE_REGISTRY")
	static WritableRegistry<WritableRegistry<?>> justutilities_getRootRegistry(){ throw new AssertionError(); }
}
