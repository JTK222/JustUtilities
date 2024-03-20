package net.brazier_modding.justutilities.storage;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.storage.SectionStorage;

import java.nio.file.Path;
import java.util.function.Function;

public abstract class DynamicSectionStorage<T> extends SectionStorage<T> {
	public DynamicSectionStorage(Path path, Function<Runnable, Codec<T>> $$1, Function<Runnable, T> $$2, DataFixer dataFixer, DataFixTypes dataFixerType, boolean $$5, RegistryAccess regAccess, LevelHeightAccessor levelHeighAccess) {
		super(path, $$1, $$2, dataFixer, dataFixerType, $$5, regAccess, levelHeighAccess);
	}
}
