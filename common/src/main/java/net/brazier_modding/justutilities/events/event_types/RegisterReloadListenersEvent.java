package net.brazier_modding.justutilities.events.event_types;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.jetbrains.annotations.ApiStatus;

/** --- INTERNAL USE ONLY --- **/
public final class RegisterReloadListenersEvent extends RegistryEvent<PreparableReloadListener> {

	private PackType packType;
	private TextureManager textureManager;

	private RegisterReloadListenersEvent() {}

	public PackType getPackType() {
		return packType;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	public void setTextureManager(TextureManager textureManager) {
		this.textureManager = textureManager;
	}

	@Override
	@ApiStatus.Internal
	public void reset() {
		super.reset();
		this.textureManager = null;
	}

	@ApiStatus.Internal
	public void setPackType(PackType packType){
		this.packType = packType;
	}

	@ApiStatus.Internal
	public static final RegisterReloadListenersEvent INSTANCE = new RegisterReloadListenersEvent();
}
