package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.RuntimeHooks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSerializer.class)
public class ChunkIOHook {

	@Inject(
			method = "read",
			at = @At(value = "RETURN")
	)
	private static void justutilities_readChunk(ServerLevel level, PoiManager poiManager, ChunkPos chunkPos, CompoundTag compoundTag, CallbackInfoReturnable<ProtoChunk> cir) {
		RuntimeHooks.readChunk(level, chunkPos);
	}

	@Inject(
			method = "write",
			at = @At(value = "RETURN")
	)
	private static void justutilities_writeChunk(ServerLevel level, ChunkAccess chunk, CallbackInfoReturnable<CompoundTag> cir) {
		RuntimeHooks.writeChunk(level, chunk.getPos());
	}
}
