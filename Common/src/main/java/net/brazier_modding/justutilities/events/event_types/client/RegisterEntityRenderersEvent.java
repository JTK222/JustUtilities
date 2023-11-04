package net.brazier_modding.justutilities.events.event_types.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.BiConsumer;

public class RegisterEntityRenderersEvent {

	protected BiConsumer<EntityType<?>, EntityRendererProvider<?>> registerCallback;

	public RegisterEntityRenderersEvent(BiConsumer<EntityType<?>, EntityRendererProvider<?>> registerCallback) {
		this.registerCallback = registerCallback;
	}

	public <T> void register(EntityType<?> type, EntityRendererProvider<?> renderer){
		this.registerCallback.accept(type, renderer);
	}
}
