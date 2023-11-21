package net.brazier_modding.justutilities.mixin.client;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/datafixers/util/Pair;of(Ljava/lang/Object;Ljava/lang/Object;)Lcom/mojang/datafixers/util/Pair;"
			),
			locals = LocalCapture.CAPTURE_FAILSOFT,
			remap = false
	)
	private static void justutilities_interceptModelLoading(Map.Entry entry, CallbackInfoReturnable<Pair> cir, Reader reader) {

		if(entry.getKey() instanceof ResourceLocation location){
			if(location.getPath().endsWith(".jutil.json")){
				System.out.println(location);
				cir.setReturnValue(Pair.of(location, Minecraft.getInstance().getModelManager().getMissingModel()));
			}
		}
	}
}
