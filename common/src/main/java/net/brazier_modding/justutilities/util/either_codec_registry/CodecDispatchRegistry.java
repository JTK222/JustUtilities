package net.brazier_modding.justutilities.util.either_codec_registry;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CodecDispatchRegistry<T> {
	private Map<ResourceLocation, CodecDispatchType<T>> typeRegistry = new HashMap<>();
	private Map<Class<T>, ResourceLocation> reverseTypeRegistry = new HashMap<>();

	private Codec<T> codec = ResourceLocation.CODEC.dispatch(ingredient -> reverseTypeRegistry.get(ingredient.getClass()), key -> typeRegistry.get(key).getCodec());

	public Codec<T> getCodec(){
		return this.codec;
	}

	public void register(CodecDispatchType<T> value){
		typeRegistry.put(value.getTypeKey(), value);
		reverseTypeRegistry.put(value.getTypeToken(), value.getTypeKey());
	}

}
