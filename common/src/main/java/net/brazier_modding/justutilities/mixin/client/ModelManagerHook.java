package net.brazier_modding.justutilities.mixin.client;

import com.mojang.datafixers.util.Pair;
import net.brazier_modding.justutilities.api.models.JutilModelLoader;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

@Mixin(ModelManager.class)
public class ModelManagerHook {

	@Shadow private BakedModel missingModel;

	@Inject(
			method = {
					"lambda$loadBlockModels$8(Ljava/util/Map$Entry;)Lcom/mojang/datafixers/util/Pair;",
					"m_246478_(Ljava/util/concurrent/Executor;Ljava/util/Map;)Ljava/util/concurrent/CompletionStage;",
					"method_45898(Ljava/util/concurrent/Executor;Ljava/util/Map;)Ljava/util/concurrent/CompletionStage;"
			},
			cancellable = true,
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/datafixers/util/Pair;of(Ljava/lang/Object;Ljava/lang/Object;)Lcom/mojang/datafixers/util/Pair;"
			),
			remap = false
	)
	private static void justutilities_interceptModelLoading(Map.Entry entry, CallbackInfoReturnable<Pair> cir) {
		if(entry.getKey() instanceof ResourceLocation location){
			if(location.getPath().endsWith(".jutil.json")){
				//Create our own reader, for fabric compat
				try(Reader reader2 = ((Resource) entry.getValue()).openAsReader()) {
					cir.setReturnValue(Pair.of(location, JutilModelLoader.loadModel(location, reader2)));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
