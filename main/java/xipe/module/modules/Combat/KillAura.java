package xipe.module.modules.Combat;

import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.glfw.GLFW;

import com.google.common.collect.Streams;

import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.NumberSetting;
import xipe.utils.RotationUtil;

public class KillAura extends Mod{
	
	NumberSetting range = new NumberSetting("Range", 0.1, 6.0, 4.0, 1.0);
	BooleanSetting switchItem = new BooleanSetting("Switch", false);
	BooleanSetting allEntities = new BooleanSetting("AllEntities", true);
	BooleanSetting multiAura = new BooleanSetting("Multi", false);
	BooleanSetting spam = new BooleanSetting("Spam", false);
	
	public KillAura() {
		super("Aura", "Kills shit for you", Category.COMBAT);
		this.setKey(GLFW.GLFW_KEY_R);
		addSettings(range,switchItem,allEntities,multiAura,spam);
	}


    @Override
    public void onTick() {
        nullCheck();
        if(mc.player.getAttackCooldownProgress(0) < 1 || spam.isEnabled()) return;
        
        try {
	    	List<Entity> filtered;
	    	if(!allEntities.isEnabled()) {
	    		filtered = Streams.stream(mc.world.getEntities()).filter(e -> e instanceof PlayerEntity && mc.player.distanceTo(e) <= range.getValue() && e != mc.player).collect(Collectors.toList());
	    	} else {
	    		filtered = Streams.stream(mc.world.getEntities()).filter(e -> e instanceof Entity && mc.player.distanceTo(e) <= range.getValue() && e != mc.player).collect(Collectors.toList());
	    	}
	        
	    	for(Entity entity : filtered) {
	    		if(entity != null) {
	    			// Don't attack dead/non living entities, ones we can't attack, and end crystals
	    			if(entity.isLiving() && entity.isAttackable() && !(entity.getClass() == EndCrystalEntity.class)) {
		                if (switchItem.isEnabled()) {
		                    for (int i = 0; i < 9; i++) {
		                        if (mc.player.getInventory().getStack(i).getItem() instanceof SwordItem)
		                            mc.player.getInventory().selectedSlot = i;
		                    }
		                }
		                mc.interactionManager.attackEntity(mc.player, entity);
		                mc.player.swingHand(Hand.MAIN_HAND);
		                
		                if(!multiAura.isEnabled()) break;
 	    			}
	            }
	    	}
        	
        } catch (Exception ignored) {}
    }
}
