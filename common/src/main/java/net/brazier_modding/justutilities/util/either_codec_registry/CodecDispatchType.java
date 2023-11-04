package net.brazier_modding.justutilities.util.either_codec_registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

public class CodecDispatchType<T> {
	private final ResourceLocation typeKey;
	private final Class<T> typeToken;
	private final Codec<T> codec;

	public CodecDispatchType(ResourceLocation typeKey, Class<T> typeToken, MapCodec<T> codec) {
		this.typeKey = typeKey;
		this.typeToken = typeToken;
		this.codec = codec.codec();
	}
	public ResourceLocation getTypeKey() {
		return typeKey;
	}

	public Class<T> getTypeToken() {
		return typeToken;
	}


	public Codec<T> getCodec() {
		return codec;
	}
}
