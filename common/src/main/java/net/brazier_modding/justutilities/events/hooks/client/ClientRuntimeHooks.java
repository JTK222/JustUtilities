package net.brazier_modding.justutilities.events.hooks.client;

import net.brazier_modding.justutilities.events.Events;
import net.brazier_modding.justutilities.events.event_types.client.RegisterKeybindsEvent;
import net.minecraft.client.KeyMapping;

import java.util.Set;

public class ClientRuntimeHooks {
	public static void clientTickHook(boolean pre) {
		if(pre) Events.Runtime.CLIENT_TICK.invokePreEvent(null);
		else Events.Runtime.CLIENT_TICK.invokePostEvent(null);
	}

	public static final Set<KeyMapping> gatherKeyMappingsHook(){
		RegisterKeybindsEvent event = new RegisterKeybindsEvent();
		Events.Init.CLIENT_REGISTER_KEYBINDS.postEvent(event);

		Set<KeyMapping> keys = event.getObjects();

		return keys;
	}

	public static final void handleKeybindsHook(){
		Events.Runtime.CLIENT_HANDLE_KEYBINDS.postEvent(null);
	}
}
