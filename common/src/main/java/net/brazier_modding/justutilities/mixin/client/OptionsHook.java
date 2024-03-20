package net.brazier_modding.justutilities.mixin.client;

import net.brazier_modding.justutilities.events.hooks.client.ClientRuntimeHooks;
import net.brazier_modding.justutilities.mixin.accessors.KeyMappingAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Mixin(Options.class)
public abstract class OptionsHook {

	@Shadow
	@Mutable
	@Final
	public KeyMapping[] keyMappings;

	@Shadow public abstract OptionInstance<Boolean> allowServerListing();


	@Inject(
			method = "load()V",
			at = @At("HEAD")
	)
	private void justutilities_loadOptions(CallbackInfo ci) {
		Set<KeyMapping> keybinds = ClientRuntimeHooks.gatherKeyMappingsHook();

		this.keyMappings = ArrayUtils.addAll(keyMappings, keybinds.toArray(new KeyMapping[keybinds.size()]));

		Map<String, Integer> categories = KeyMappingAccessor.justutilities_getKeybindCategories();

		for(KeyMapping keybind : keybinds)
			categories.computeIfAbsent(keybind.getCategory(), key -> 10);
	}
}
