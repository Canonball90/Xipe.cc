package xipe.utils.world;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class EntityUtils {
	
	 public static boolean isAttackable(final Entity e, final boolean ignoreFriends) {
	        return e instanceof LivingEntity && e.isAlive() && e != MinecraftClient.getInstance().player && !e.isConnectedThroughVehicle((Entity)MinecraftClient.getInstance().player);
	    }
	 
	 public static float getFullHealth(LivingEntity entity) {
	        return entity.getHealth() + entity.getAbsorptionAmount();
	    }

		//get player speed
		public static double getSpeed() {
			return Math.sqrt(MinecraftClient.getInstance().player.getVelocity().x * MinecraftClient.getInstance().player.getVelocity().x + MinecraftClient.getInstance().player.getVelocity().z * MinecraftClient.getInstance().player.getVelocity().z);
		}
}
