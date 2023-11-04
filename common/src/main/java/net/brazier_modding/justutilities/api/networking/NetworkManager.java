package net.brazier_modding.justutilities.api.networking;

import com.mojang.logging.LogUtils;
import net.brazier_modding.justutilities.JustUtilitiesConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.function.Function;

public class NetworkManager {
//	private Map<String, Function<

//	public <T extends PacketListener> void sendClientPacket(ServerPlayer player, Packet<T> packet){
//
//		player.connection.send(new ClientboundCustomPayloadPacket(JustUtilitiesConstants.NETWORK_IDENTIFIER, generateBufferForPacket()));
//
//	}
//
//	public void sendServerPacket(ServerPlayer player, Packet<T> packet){
//		Minecraft.getInstance().getConnection().send(new ClientboundCustomPayloadPacket(JustUtilitiesConstants.NETWORK_IDENTIFIER, generateBufferForPacket()));
//
//	}
//
//	private FriendlyByteBuf generateBufferForPacket(){
//		return null;
//	}
//
//	public <P extends Packet<T>> ConnectionProtocol.PacketSet<T> addPacket(Class<P> p_178331_, Function<FriendlyByteBuf, P> p_178332_) {
//		int i = this.idToDeserializer.size();
//		int j = this.classToId.put(p_178331_, i);
//		if (j != -1) {
//			String s = "Packet " + p_178331_ + " is already registered to ID " + j;
//			LOGGER.error(LogUtils.FATAL_MARKER, s);
//			throw new IllegalArgumentException(s);
//		} else {
//			this.idToDeserializer.add(p_178332_);
//			return this;
//		}
//	}
}
