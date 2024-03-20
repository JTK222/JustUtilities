package net.brazier_modding.justutilities.events;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PhasedEvent<E> extends Event<E>{

	protected List<Consumer<E>> preListeners = new ArrayList<>();
	protected List<Consumer<E>> postListeners = new ArrayList<>();

	public static <E> PhasedEvent<E> create(ResourceLocation identifier){
		return new PhasedEvent<E>(identifier);
	}

	protected PhasedEvent(ResourceLocation eventName) {
		super(eventName);
	}

	public void subscribePre(Consumer<E> listener){
		this.preListeners.add(listener);
	}

	public void invokePreEvent(E eventObject) {
		for(Consumer<E> listener : preListeners)
			listener.accept(eventObject);
	}

	public void subscribePost(Consumer<E> listener){
		this.postListeners.add(listener);
	}

	public void invokePostEvent(E eventObject) {
		for(Consumer<E> listener : postListeners)
			listener.accept(eventObject);
	}
}
