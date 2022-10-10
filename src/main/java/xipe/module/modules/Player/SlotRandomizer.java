package xipe.module.modules.Player;

import java.util.Random;

import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class SlotRandomizer extends Mod{
	
	public NumberSetting perTick = new NumberSetting("Per-Tick", 1, 10, 2, 1);
	public Random random = new Random();

	public SlotRandomizer() {
		super("SlotRandomizer", "Randomizes slots", Category.WORLD);
		addSetting(perTick);
	}

	@Override
    public void onTick() {
        for (int i = 0; i < perTick.getValue(); i++) {
            mc.player.getInventory().selectedSlot = random.nextInt(0, 8);
        }
    }
}
