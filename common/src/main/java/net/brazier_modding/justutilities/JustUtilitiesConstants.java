package net.brazier_modding.justutilities;

import net.brazier_modding.justutilities.api.IModInterface;
import net.brazier_modding.justutilities.util.ServiceUtil;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JustUtilitiesConstants {
	public static final String MOD_ID = "justutilities";
	public static final String MOD_NAME = "Just Utilities";
	public static final ResourceLocation NETWORK_IDENTIFIER = new ResourceLocation(MOD_ID, "handler");
	public static final Logger LOG = LogManager.getLogger(MOD_NAME);

	public static final IModInterface MOD_PROVIDER = ServiceUtil.getService(IModInterface.class);
}
