package net.brazier_modding.justutilities.events.event_types.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;

public class HudPostRenderEvent {

	private GuiGraphics graphics;
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


	public GuiGraphics getGraphics() {
		return graphics;
	}

	public void setGraphics(GuiGraphics graphics) {
		this.graphics = graphics;
	}

	public void setPoseStack(PoseStack poseStack) {
		this.poseStack = poseStack;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}
}
