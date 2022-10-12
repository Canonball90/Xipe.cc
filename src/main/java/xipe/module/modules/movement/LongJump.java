package xipe.module.modules.movement;

import net.minecraft.util.math.MathHelper;
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class LongJump extends Mod{
	
	NumberSetting speed = new NumberSetting("Factor", 1, 10, 3, 1);

	public LongJump() {
		super("LongJump", "Makes you jump really far", Category.MOVEMENT);
		addSetting(speed);
	}
	
	  @Override
	    public void onTick() {
	        if (mc.player == null) return;
	        float yaw = (float) Math.toRadians(mc.player.getYaw());
	        double vSpeed = speed.getValue() / 5;
	        if (!mc.player.isOnGround()) {
	            mc.player.addVelocity(-MathHelper.sin(yaw) * vSpeed, 0.2F, MathHelper.cos(yaw) * vSpeed);
	        } else if (mc.player.isOnGround()) {
	            mc.player.setVelocity(0, 0, 0);
	        }
	        toggle();
	    }
	}

