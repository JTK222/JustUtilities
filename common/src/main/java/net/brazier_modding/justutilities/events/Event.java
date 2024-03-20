package net.brazier_modding.justutilities.events;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Event<E> {

	private ResourceLocation identifier;

	protected List<Consumer<E>> listeners = new ArrayList<>();

	public static <E> Event<E> create(ResourceLocation identifier){
		return new Event<E>(identifier);
	}

	protected Event(ResourceLocation eventName){
		this.identifier = eventName;
	}

	public void subscribe(Consumer<E> listener){
		this.listeners.add(listener);
	}

	public void postEvent(E eventObject) {
		for(Consumer<E> listener : listeners)
			listener.accept(eventObject);
	}

	public ResourceLocation getIdentifier() {
		return identifier;
	}
}
