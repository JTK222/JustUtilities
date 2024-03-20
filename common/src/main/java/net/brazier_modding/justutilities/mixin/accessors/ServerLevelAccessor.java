package net.brazier_modding.justutilities.mixin.accessors;


import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftServer.class)
public interface ServerLevelAccessor {


	@Mutable
	@Accessor("storageSource")
	LevelStorageSource.LevelStorageAccess justutilities_getStorageSource();
}
