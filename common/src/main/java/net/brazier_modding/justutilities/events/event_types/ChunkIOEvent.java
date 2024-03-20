package net.brazier_modding.justutilities.events.event_types;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

public record ChunkIOEvent(ServerLevel level, ChunkPos chunkPos) {
}
