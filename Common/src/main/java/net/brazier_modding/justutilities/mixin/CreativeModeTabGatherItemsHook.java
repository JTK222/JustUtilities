package net.brazier_modding.justutilities.mixin;

import net.brazier_modding.justutilities.events.hooks.ClientLifecycleHooks;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabGatherItemsHook {

	@Inject(
			method = "Lnet/minecraft/world/item/CreativeModeTab;buildContents(Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;)V",
			at = @At(value = "FIELD", target="Lnet/minecraft/world/item/CreativeModeTab;displayItems:Ljava/util/Collection;", opcode = 181), //putfield
			locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void justutilities_buildCreativeModeTabContents(CreativeModeTab.ItemDisplayParameters displayParameters, CallbackInfo ci, @Coerce CreativeModeTab.Output itemDisplayBuilder) {
		ClientLifecycleHooks.creativeModeTabGatherItems((CreativeModeTab)((Object) this), displayParameters, itemDisplayBuilder);
	}
}