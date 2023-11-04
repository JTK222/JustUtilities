package net.brazier_modding.justutilities.api;

import net.brazier_modding.justutilities.api.IEventRegistrar;

/**
 * This EventRegistrar, is only run on Dedicated Servers,
 * it runs after the normal IEventRegistrar
 */
@FunctionalInterface
public interface IServerEventRegistrar extends IEventRegistrar {
}
