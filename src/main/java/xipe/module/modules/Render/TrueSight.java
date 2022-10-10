package xipe.module.modules.Render;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import xipe.module.Mod;

public class TrueSight extends Mod {

	private List<Entity> invisibles = new ArrayList<>();
	
	public TrueSight() {
		super("TrueSight", "Allows you to see invisible entities", Category.RENDER);
	}
	
	@Override
	public void onTick() {
		mc.world.getEntities().forEach(entity -> {
			if (entity.isInvisible() && !invisibles.contains(entity)) {
				invisibles.add(entity);
				entity.setInvisible(false);
			}
		});
		
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		invisibles.forEach(entity -> {
			entity.setInvisible(true);
		});
		invisibles.clear();
		super.onDisable();
	}
}