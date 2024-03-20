package net.brazier_modding.justutilities.mixin.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.brazier_modding.nails_and_screws.DecorHitResult;
import net.brazier_modding.nails_and_screws.DecorProvider;
import net.brazier_modding.nails_and_screws.DecorInstance;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class SelectionBoxHook {

	@Shadow
	private RenderBuffers renderBuffers;

	@Inject(
			method = "renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;)V",
			at = @At(
					value = "FIELD",
					target="Lnet/minecraft/client/Minecraft;hitResult:Lnet/minecraft/world/phys/HitResult;",
					ordinal = 1,
					opcode = 180 //GETFIELD
			)
	)
	private void just_utilties_renderSelectionBox(PoseStack poseStack, float partialTick, long $$2, boolean hudEnabled, Camera camera, GameRenderer renderer, LightTexture lightTexture, Matrix4f matrix, CallbackInfo ci){
		if(!hudEnabled) return;
		Minecraft.getInstance().level.getProfiler().popPush("Decor Selection Box");

		if(Minecraft.getInstance().hitResult instanceof DecorHitResult decorHit){
			DecorInstance decor = DecorProvider.getDecor(Minecraft.getInstance().level, decorHit.getBlockPos(), decorHit.getDecorIndex());
			VoxelShape shape = decor.getShape();

			Vec3i globalPos = decor.getGlobalPos();
			Vector3f localPos = decor.getLocalPos();

			Vec3 cameraPos = camera.getPosition();
			double d = globalPos.getX() - cameraPos.x() + localPos.x;
			double e = globalPos.getY() - cameraPos.y() + localPos.y;
			double g = globalPos.getZ() - cameraPos.z() + localPos.z;
			renderShape(poseStack, renderBuffers.bufferSource().getBuffer(RenderType.lines()), shape, d, e, g, 0.0F, 0.0F, 0.0F, 0.4F);
		}
	}

	private static void renderShape(PoseStack poseStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double posX, double posY, double posZ, float r, float g, float b, float a) {
		PoseStack.Pose posestack$pose = poseStack.last();
		voxelShape.forAllEdges((p_234280_, p_234281_, p_234282_, p_234283_, p_234284_, p_234285_) -> {
			float f = (float)(p_234283_ - p_234280_);
			float f1 = (float)(p_234284_ - p_234281_);
			float f2 = (float)(p_234285_ - p_234282_);
			float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
			f /= f3;
			f1 /= f3;
			f2 /= f3;
			vertexConsumer.vertex(posestack$pose.pose(), (float)(p_234280_ + posX), (float)(p_234281_ + posY), (float)(p_234282_ + posZ)).color(r, g, b, a).normal(posestack$pose.normal(), f, f1, f2).endVertex();
			vertexConsumer.vertex(posestack$pose.pose(), (float)(p_234283_ + posX), (float)(p_234284_ + posY), (float)(p_234285_ + posZ)).color(r, g, b, a).normal(posestack$pose.normal(), f, f1, f2).endVertex();
		});
	}
}
