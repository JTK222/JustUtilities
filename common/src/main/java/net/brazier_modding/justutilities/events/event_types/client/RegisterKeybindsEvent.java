package net.brazier_modding.justutilities.events.event_types.client;

import net.brazier_modding.justutilities.events.event_types.RegistryEvent;
import net.minecraft.client.KeyMapping;
import org.jetbrains.annotations.ApiStatus;

public final class RegisterKeybindsEvent extends RegistryEvent<KeyMapping> {

	public RegisterKeybindsEvent() {}

	/** --- INTERNAL USE ONLY --- **/

	@ApiStatus.Internal
	public static final RegisterKeybindsEvent INSTANCE = new RegisterKeybindsEvent();
}
