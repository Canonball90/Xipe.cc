package xipe.module.modules.movement;

import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class TridentPlus extends Mod{
	
	NumberSetting factor = new NumberSetting("Factor", 1, 10, 3, 1);
	NumberSetting upFactor = new NumberSetting("Up Factor", 1, 10, 3, 1);

	public TridentPlus() {
		super("Trident+", "Better trident", Category.MOVEMENT);
		addSettings(factor,upFactor);
	}
	
	@Override
	public void onTick() {
		float yaw = (float) Math.toRadians(mc.player.getYaw());
		float pitch = (float) Math.toRadians(mc.player.getPitch());
		double vSpeed = factor.getValue() / 5;
		if(mc.player.isUsingRiptide()) {
			mc.player.setVelocity(-MathHelper.sin(yaw) * vSpeed, -MathHelper.sin(pitch) * vSpeed * upFactor.getValue(), MathHelper.cos(yaw) * vSpeed);
		}
		super.onTick();
	}	

}
