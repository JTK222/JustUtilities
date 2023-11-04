package net.brazier_modding.justutilities.events.event_types;

import org.jetbrains.annotations.ApiStatus;

import java.util.HashSet;
import java.util.Set;

public class RegistryEvent<T> {

	private Set<T> objects = new HashSet<>();

	public void register(T... objects) {
		for(int i = 0; i < objects.length; i++)
			this.objects.add(objects[i]);
	}

	/** --- INTERNAL USE ONLY --- **/

	@ApiStatus.Internal
	public Set<T> getObjects() {
		return this.objects;
	}

	@ApiStatus.Internal
	public void reset(){
		this.objects.clear();
	}
}
