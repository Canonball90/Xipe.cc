package xipe.module.modules.Player;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.text.LiteralTextContent;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class AutoLog extends Mod{
	
	List<Entity> entities;
	ModeSetting mode = new ModeSetting("Mode", "Hp", "Hp", "Creeper");
	NumberSetting hp = new NumberSetting("Health", 1, 32, 10, 1);
	NumberSetting range = new NumberSetting("Range", 1.0, 6.0, 5.0, 0.1);

	public AutoLog() {
		super("AutoLog", "Auto disconnects you when your health is low", Category.WORLD);
		addSettings(mode,hp,range);
	}
	
	 public void onTick() {
	        entities = new ArrayList<>();
	        mc.world.getEntities().forEach(entities::add);

	        if (mc.player.getHealth() <= hp.getValue() && mode.isMode("Hp")) {
	            mc.player.networkHandler.getConnection()
	                    .disconnect(null);
	            toggle();
	        } else if (mode.isMode("Creeper")) {
	            for (Entity e : entities) {
	                if (e instanceof CreeperEntity && mc.player.distanceTo(e) <= range.getValue()) {
	                    mc.player.networkHandler.getConnection().disconnect(null);
	                    toggle();
	                }
	            }
	        }

	    }

}
