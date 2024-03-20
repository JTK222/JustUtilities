package net.brazier_modding.justutilities.mixin.client;

import net.brazier_modding.justutilities.events.hooks.client.ClientRuntimeHooks;
import net.brazier_modding.nails_and_screws.DecorHitResult;
import net.brazier_modding.justutilities.events.hooks.LifecycleHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftHooks {

	@Inject(
			method = "setLevel",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;",
					shift = At.Shift.BEFORE
			)
	)
	private void justutilities_setLevel(ClientLevel newLevel, CallbackInfo callback) {
		LifecycleHooks.changeLevelHook(Minecraft.getInstance().level, newLevel);
	}

	@Inject(
			method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;",
					shift = At.Shift.BEFORE
			)
	)
	private void justutilities_setLevel(Screen screen, CallbackInfo callback) {
		LifecycleHooks.changeLevelHook(Minecraft.getInstance().level, null);
	}

	@Inject(
			method = "tick",
			at = @At(value = "HEAD")
	)
	private void justutilities_clientTickPre(CallbackInfo ci) {
		ClientRuntimeHooks.clientTickHook(true);
	}

	@Inject(
			method = "tick",
			at = @At(value = "TAIL")
	)
	private void justutilities_clientTickPost(CallbackInfo ci) {
		ClientRuntimeHooks.clientTickHook(false);
	}


	@Inject(
			method = "handleKeybinds()V",
			at = @At(value = "TAIL")
	)
	private void justutilities_handleKeybinds(CallbackInfo callback) {
		ClientRuntimeHooks.handleKeybindsHook();
	}

	//TODO Extract into event
	@Inject(
		method = "startAttack",
		at = @At(value = "INVOKE", target="Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V")
	)
	private void justutilities_startAttack(CallbackInfoReturnable<Boolean> cir){
		Player player = Minecraft.getInstance().player;
		Level level = Minecraft.getInstance().level;
		ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);

		if (itemStack.isItemEnabled(level.enabledFeatures())
			&& Minecraft.getInstance().hitResult instanceof DecorHitResult decorHit) {

//			level.

//			BlockHitResult blockHitResult = (BlockHitResult)this.hitResult;
//			BlockPos blockPos = blockHitResult.getBlockPos();
//			if (!this.level.getBlockState(blockPos).isAir()) {
//				this.gameMode.startDestroyBlock(blockPos, blockHitResult.getDirection());
//				if (this.level.getBlockState(blockPos).isAir()) {
//					bl = true;
//				}
//				break;
//			}
		}
	}

	@Inject(
			method = "startUseItem",
			at = @At(value = "TAIL")
	)
	private void justutilities_startUseItem(CallbackInfo callback){

	}
}
