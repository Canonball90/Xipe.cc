package xipe.module.modules.Combat;

import java.util.Objects;

import net.minecraft.util.hit.EntityHitResult;
import xipe.module.Mod;
import net.minecraft.util.Hand;



public class Trigger extends Mod{
	
	public Trigger() {
		super("Trigger", "Automaticly attack the entity you look at", Category.COMBAT);
		//this.setKey(GLFW.GLFW_KEY_O);
	}
	
	int cooldown = 4;
	
	@Override
	public void onTick() {
		if (!(mc.crosshairTarget instanceof EntityHitResult) || Objects.requireNonNull(mc.player)
            	.getAttackCooldownProgress(0) < 1) {
    		return;
    	}
    
    //mc.player.swingHand(Hand.MAIN_HAND);
    	if (cooldown <=- 0) {
    		cooldown = 4;
    		Objects.requireNonNull(mc.interactionManager)
        	.attackEntity(mc.player, ((EntityHitResult) mc.crosshairTarget).getEntity());
    		mc.player.swingHand(Hand.MAIN_HAND);
    	}
    	cooldown--;
	}
}
