package net.dark_roleplay.just_utilities.api.events.client;

import com.mojang.blaze3d.vertex.PoseStack;

public class HudPostRenderEvent {

	private PoseStack poseStack;
	private float partialTicks;
	private int width;
	private int height;

	public PoseStack poseStack() {
		return poseStack;
	}

	public float partialTicks() {
		return partialTicks;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public void setDimensions(int width, int height){
		this.width = width;
		this.height = height;
	}

	public void setPoseStack(PoseStack poseStack) {
		this.poseStack = poseStack;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}
}
