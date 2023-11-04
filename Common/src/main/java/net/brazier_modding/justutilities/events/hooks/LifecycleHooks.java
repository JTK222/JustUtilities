package net.brazier_modding.justutilities.events.hooks;

import net.brazier_modding.justutilities.api.IClientEventRegistrar;
import net.brazier_modding.justutilities.api.IEventRegistrar;
import net.brazier_modding.justutilities.api.IServerEventRegistrar;
import net.brazier_modding.justutilities.events.Events;
import net.brazier_modding.justutilities.events.event_types.LevelChangeEvent;
import net.brazier_modding.justutilities.events.event_types.RegisterReloadListenersEvent;
import net.brazier_modding.justutilities.fixed_data.blocks.VoxelShapeUtil;
import net.brazier_modding.justutilities.fixed_data.creative_tab.CreativeTabFixedData;
import net.brazier_modding.justutilities.fixed_data.items.ItemFixedData;
import net.brazier_modding.justutilities.util.DistributionUtil;
import net.brazier_modding.justutilities.util.ServiceUtil;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LifecycleHooks {
	public static final void bootstrapHook(){
		List<IEventRegistrar> registrars = ServiceUtil.getServices(IEventRegistrar.class);
		for(IEventRegistrar reg : registrars) reg.registerEvents();

		if(DistributionUtil.isClient()){
			List<IClientEventRegistrar> clientRegistrars = ServiceUtil.getServices(IClientEventRegistrar.class);
			for(IClientEventRegistrar reg : clientRegistrars) reg.registerEvents();
		}else{
			List<IServerEventRegistrar> serverRegistrars = ServiceUtil.getServices(IServerEventRegistrar.class);
			for(IServerEventRegistrar reg : serverRegistrars) reg.registerEvents();
		}
	}


	public static final void postBootstrapHook() {

	}


	private static boolean isBootstrapped = false;

	public static final void registryBootstrapHook(){
		if(isBootstrapped) return;
		isBootstrapped = true;
		VoxelShapeUtil.load();

		Events.Init.BUILTIN_REGISTRIES.postEvent(null);

		CreativeTabFixedData.load();
		ItemFixedData.load();
	}

	public static final void postRegistryBootstrapHook(){	}


	public static void clientInitHook(){
		Events.Init.CLIENT_INIT.postEvent(null);
	}


	public static void changeLevelHook(Level prevLevel, Level newLevel){
		LevelChangeEvent event = new LevelChangeEvent();
		event.setPreviousLevel(prevLevel);
		event.setLevel(newLevel);
		Events.Runtime.LEVEL_CHANGE.postEvent(event);
	}

	public static Set<PreparableReloadListener> registerReloadListeners(PackType type, TextureManager textureManager){
		RegisterReloadListenersEvent event = RegisterReloadListenersEvent.INSTANCE;
		if(type == PackType.CLIENT_RESOURCES && textureManager == null) return new HashSet<>();

		event.reset();
		event.setPackType(type);
		event.setTextureManager(textureManager);
		Events.Init.REGISTER_RELOAD_LISTENERS.postEvent(event);
		return event.getObjects();
	}
}
