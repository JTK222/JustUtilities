package net.brazier_modding.justutilities.util;

public class DistributionUtil {

	private static Distribution dist = null;

	public static boolean isServer(){
		return !isClient();
	}

	public static boolean isClient(){
		if(DistributionUtil.dist == null){
			try{
				Class.forName("net.minecraft.client.main.Main");
				dist = Distribution.CLIENT;
			} catch (Exception e) {
				dist = Distribution.SERVER;
			}
		}

		return DistributionUtil.dist == Distribution.CLIENT;
	}

	public enum Distribution{
		CLIENT,
		SERVER;
	}
}
