package net.dark_roleplay.just_utilities.impl;

import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.forgespi.language.IModInfo;

public class EarlyModContainer extends ModContainer {
	public EarlyModContainer(IModInfo info) {
		super(info);
		this.contextExtension = () -> null;
	}

	@Override
	public boolean matches(Object mod) {
		return false;
	}

	@Override
	public Object getMod() {
		return null;
	}
}
