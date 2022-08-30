package xipe.module.modules.Combat;

import java.util.stream.Collectors;

import com.google.common.collect.Streams;

import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import xipe.module.Mod;
import xipe.module.settings.NumberSetting;

public class CrystalAura extends Mod{
	
	static EndCrystalEntity target = null;

	NumberSetting range = new NumberSetting("Range", 1.0, 6.0, 5.0, 0.1);
	
	public CrystalAura() {
		super("CrystalAura", "Autoly destroys crystals", Category.COMBAT);
		addSetting(range);
	}
	
	@Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        
        try {
	    	EndCrystalEntity entity = (EndCrystalEntity) Streams.stream(mc.world.getEntities()).filter(e -> e instanceof EndCrystalEntity && mc.player.distanceTo(e) <= range.getValue()).collect(Collectors.toList()).get(0);
    		if (entity != null) {
    			if(!entity.isAttackable()) return;             
                mc.interactionManager.attackEntity(mc.player, entity);
            }
        	
        } catch (Exception ignored) {}
    }

}
