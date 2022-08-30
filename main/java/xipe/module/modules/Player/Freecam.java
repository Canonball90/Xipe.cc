package xipe.module.modules.Player;

import net.minecraft.world.GameMode;
import xipe.module.Mod;

public class Freecam extends Mod{

	public Freecam() {
		super("Freecam", "?", Category.WORLD);
	}
	
	@Override
	public void onTick() {
		mc.player.getAbilities().allowFlying = true;
		mc.player.setInvisible(true);
		 mc.interactionManager.setGameMode(GameMode.SPECTATOR);
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		 mc.interactionManager.setGameMode(GameMode.SURVIVAL);
		super.onDisable();
	}

}
