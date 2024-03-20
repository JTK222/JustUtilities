package net.brazier_modding.justutilities.events;

import net.brazier_modding.justutilities.JustUtilitiesConstants;
import net.brazier_modding.justutilities.events.event_types.*;
import net.brazier_modding.justutilities.events.event_types.client.*;
import net.minecraft.resources.ResourceLocation;

public class Events {
	public static class Init {
		public static final Event<Void> MOD_INIT = simpleEvent("mod_init");
		public static final Event<Void> BUILTIN_REGISTRIES = simpleEvent("builtin_registry_init");

		public static final Event<Void> CLIENT_INIT = simpleEvent("client_init");

		public static final Event<RegisterCommandsEvent> REGISTER_COMMANDS = simpleEvent("register_commands");
		public static final Event<RegisterReloadListenersEvent> REGISTER_RELOAD_LISTENERS = simpleEvent("client_register_reload_listeners");
		public static final Event<RegisterKeybindsEvent> CLIENT_REGISTER_KEYBINDS = simpleEvent("client_register_keybinds");
		public static final Event<RenderTypeLookupsEvent> CLIENT_RENDER_TYPE_LOOKUPS = simpleEvent("client_render_type_lookup");
		public static final Event<RegisterEntityRenderersEvent> CLIENT_REGISTER_ENTITY_RENDERERS = simpleEvent("client_register_entity_renderers");
	}

	public static class Runtime {
		public static final PhasedEvent<Void> CLIENT_TICK = phasedEvent("client_tick");

		public static final Event<Void> CLIENT_TICK_PRE = simpleEvent("client_tick_pre");
		public static final Event<Void> CLIENT_TICK_POST = simpleEvent("client_tick_post");
		public static final Event<Void> CLIENT_HANDLE_KEYBINDS = simpleEvent("client_handle_keybinds");
		public static final Event<HudPostRenderEvent> HUD_POST_RENDER = simpleEvent("hud_post_render");
		public static final Event<LevelChangeEvent> LEVEL_CHANGE = simpleEvent("level_change");
		public static final Event<ChunkRenderEvent> CHUNK_RENDER = simpleEvent("chunk_render");
		public static final Event<CreativeModeTabGatherItemsEvent> CREATIVE_MODE_TABS_GATHER_ITEMS = simpleEvent("creative_mode_tab_gather_items");
		public static final Event<ChunkIOEvent> CHUNK_READ_EVENT = simpleEvent("chunk_read");
		public static final Event<ChunkIOEvent> CHUNK_WRITE_EVENT = simpleEvent("chunk_write");
	}

	private static final  <E> Event<E> simpleEvent(String identifier){
		return Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, identifier));
	}

	private static final  <E> PhasedEvent<E> phasedEvent(String identifier){
		return PhasedEvent.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, identifier));
	}
}
