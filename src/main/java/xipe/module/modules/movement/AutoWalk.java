package xipe.module.modules.movement;

import net.minecraft.client.option.KeyBinding;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;

public class AutoWalk extends Mod{

	BooleanSetting sprint = new BooleanSetting("Sprint", true);

	public AutoWalk() {
		super("AutoWalk", "Automatically makes you walk", Category.MOVEMENT);
		addSetting(sprint);
	}
	
	@Override
	public void onTick() {
		KeyBinding.setKeyPressed(mc.options.forwardKey.getDefaultKey(), true);
		if(sprint.isEnabled()) {
			mc.player.setSprinting(true);
		}
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		KeyBinding.setKeyPressed(mc.options.forwardKey.getDefaultKey(), false);
		super.onDisable();
	}

}
