package net.brazier_modding.justutilities.api.networking;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Function;

public class ChannelWrapper {
	private final ResourceLocation channelIdentifier;
	private final Object2IntMap<Class<? extends Packet<?>>> classToId = Util.make(new Object2IntOpenHashMap<>(), (p_129613_) -> {
		p_129613_.defaultReturnValue(-1);
	});

	private final List<Function<FriendlyByteBuf, ? extends Packet<?>>> idToDeserializer = Lists.newArrayList();


	public ChannelWrapper(ResourceLocation channelIdentifier) {
		this.channelIdentifier = channelIdentifier;
	}

	public ResourceLocation getChannelIdentifier() {
		return channelIdentifier;
	}
}
