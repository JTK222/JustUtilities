package net.dark_roleplay.just_utilities.impl.util;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ServiceUtil {

	public static <T> T getService(Class<T> clazz){
		return  ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NoSuchElementException("Unable to find service of type: " + clazz.getCanonicalName()));
	}

	public static <T> List<T> getServices(Class<T> clazz){
		return  ServiceLoader.load(clazz).stream().map(prov -> prov.get()).collect(Collectors.toList());
	}
}