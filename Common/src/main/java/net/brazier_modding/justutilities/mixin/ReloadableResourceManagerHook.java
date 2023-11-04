package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.LifecycleHooks;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ReloadableResourceManager.class)
public class ReloadableResourceManagerHook {

	@Inject(
			method = "<init>",
			at = @At(value = "TAIL")
	)
	private void justutilities_init(PackType packType, CallbackInfo callback) {
		Set<PreparableReloadListener> listeners = LifecycleHooks.registerReloadListeners(packType, null);

		ReloadableResourceManager resourceManager = (ReloadableResourceManager) (Object) this;
		for (PreparableReloadListener listener : listeners)
			resourceManager.registerReloadListener(listener);
	}
}
