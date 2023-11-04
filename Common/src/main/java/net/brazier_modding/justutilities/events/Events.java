package net.brazier_modding.justutilities.events;

import net.brazier_modding.justutilities.JustUtilitiesConstants;
import net.brazier_modding.justutilities.events.event_types.*;
import net.brazier_modding.justutilities.events.event_types.client.*;
import net.minecraft.resources.ResourceLocation;

public class Events {
	public static class Init {
		public static final Event<Void> MOD_INIT = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "mod_init"));
		public static final Event<Void> BUILTIN_REGISTRIES = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "builtin_registry_init"));

		public static final Event<RegisterCommandsEvent> REGISTER_COMMANDS = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "register_commands"));

		public static final Event<Void> CLIENT_INIT = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_init"));
		public static final Event<RegisterReloadListenersEvent> REGISTER_RELOAD_LISTENERS = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_register_reload_listeners"));
		public static final Event<RegisterKeybindsEvent> CLIENT_REGISTER_KEYBINDS = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_register_keybinds"));
		public static final Event<RenderTypeLookupsEvent> CLIENT_RENDER_TYPE_LOOKUPS = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_render_type_lookup"));
		public static final Event<RegisterEntityRenderersEvent> CLIENT_REGISTER_ENTITY_RENDERERS = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_register_entity_renderers"));
	}

	public static class Runtime {
		public static final Event<Void> CLIENT_TICK_PRE = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_tick_pre"));
		public static final Event<Void> CLIENT_TICK_POST = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_tick_post"));
		public static final Event<Void> CLIENT_HANDLE_KEYBINDS = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "client_handle_keybinds"));
		public static final Event<HudPostRenderEvent> HUD_POST_RENDER = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "hud_post_render"));
		public static final Event<LevelChangeEvent> LEVEL_CHANGE = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "level_change"));
		public static final Event<ChunkRenderEvent> CHUNK_RENDER = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "chunk_render"));
		public static final Event<CreativeModeTabGatherItemsEvent> CREATIVE_MODE_TABS_GATHER_ITEMS = Event.create(new ResourceLocation(JustUtilitiesConstants.MOD_ID, "creative_mode_tab_gather_items"));
	}
}
