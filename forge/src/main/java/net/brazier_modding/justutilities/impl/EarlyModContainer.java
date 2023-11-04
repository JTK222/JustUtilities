package net.brazier_modding.justutilities.impl;

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
