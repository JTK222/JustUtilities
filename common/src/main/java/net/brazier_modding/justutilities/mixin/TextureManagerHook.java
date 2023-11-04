package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.LifecycleHooks;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(TextureManager.class)
public class TextureManagerHook {

	@Inject(
			method = "<init>",
			at = @At(value = "TAIL")
	)
	private void justutilities_init(ResourceManager resourceManager, CallbackInfo callback) {
		Set<PreparableReloadListener> listeners = LifecycleHooks.registerReloadListeners(PackType.CLIENT_RESOURCES, (TextureManager)((Object)this));

		if(resourceManager instanceof ReloadableResourceManager reloadableResourceManager)
			for (PreparableReloadListener listener : listeners)
				reloadableResourceManager.registerReloadListener(listener);
	}
}
