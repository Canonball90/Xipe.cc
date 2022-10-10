package xipe.module.modules.Combat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.AirBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;

public class AutoTotem extends Mod{

	List<Entity> entities;
	ModeSetting item = new ModeSetting("Item", "Gapple", "Gapple", "Totem", "Crystal", "Shield", "Nether Sword");
	BooleanSetting safetyCheck = new BooleanSetting("SafteyMode", true);
	NumberSetting health = new NumberSetting("Health", 3, 20, 10, 1);
	NumberSetting offset = new NumberSetting("Offset", 10, 50, 30, 1);
	
	public AutoTotem() {
		super("OffHand", "Puts the shit in your offhand", Category.COMBAT);
		addSettings(item,safetyCheck,health,offset);
	}
	
	private static final Formatting Gray = Formatting.GRAY;

	@Override
	public void onTick() {
		this.setDisplayName("OffHand" + Gray + "["+item.getMode()+"]");
		  nullCheck();
	        int i;
	        
	        Item L;
	        
	        if(item.isMode("Totem")) {
	        	L = Items.TOTEM_OF_UNDYING;
	        }else if(item.isMode("Gapple")) {
	        	L = Items.ENCHANTED_GOLDEN_APPLE;
		    }else if(item.isMode("Crystal")) {
	        	L = Items.END_CRYSTAL;
		    }else if(item.isMode("Nether Sword")) {
	        	L = Items.NETHERITE_SWORD;
		    }else if(item.isMode("Shield")) {
		    	L = Items.SHIELD;
		    }else {
		    	L = Items.AIR;
		    }
	       
	        Boolean found = false;
	        
	        if (!mc.player.getOffHandStack().getItem().equals(L)) {
	            for (i = 9; i <= 36; i++) {
	                if (mc.player.getInventory().getStack(i).getItem().equals(L)) {
	                    found = true;
	                    break;
	                }
	            }
	            if (!(mc.player.getOffHandStack().getItem().equals(L)) && found) {
	                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
	                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
	            }
	        }
	        
		super.onTick();
	}
}
