package net.brazier_modding.justutilities.api.models;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Collectors;

public class JutilModelLoader {

	public static BlockModel loadModel(ResourceLocation location, Reader reader){
		return BlockModel.fromStream(reader);
	}

	public static BlockModel loadModel(ResourceLocation location, JsonObject reader){
		return null;
	}
}
