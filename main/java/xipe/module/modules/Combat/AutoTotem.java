package xipe.module.modules.Combat;

import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class AutoTotem extends Mod{

	ModeSetting item = new ModeSetting("Item", "Gapple", "Gapple", "Totem", "Crystal", "Shield", "Nether Sword");
	
	public AutoTotem() {
		super("OffHand", "Puts the shit in your offhand", Category.COMBAT);
		addSetting(item);
	}

	@Override
	public void onTick() {
		  nullCheck();
	        int i;
	        Boolean found = false;
	        if(item.isMode("Totem")) {
	        if (!mc.player.getOffHandStack().getItem().equals(Items.TOTEM_OF_UNDYING)) {
	            for (i = 9; i <= 36; i++) {
	                if (mc.player.getInventory().getStack(i).getItem().equals(Items.TOTEM_OF_UNDYING)) {
	                    found = true;
	                    break;
	                }
	            }
	            if (!(mc.player.getOffHandStack().getItem().equals(Items.TOTEM_OF_UNDYING)) && found) {
	                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
	                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
	            }
	        }
	        }else if (item.isMode("Gapple")) {
	        	if (!mc.player.getOffHandStack().getItem().equals(Items.GOLDEN_APPLE)) {
		            for (i = 9; i <= 36; i++) {
		                if (mc.player.getInventory().getStack(i).getItem().equals(Items.GOLDEN_APPLE)) {
		                    found = true;
		                    break;
		                }
		            }
		            if (!(mc.player.getOffHandStack().getItem().equals(Items.GOLDEN_APPLE)) && found) {
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
		            }
		        }
	        }
	        else if (item.isMode("Crystal")) {
	        	if (!mc.player.getOffHandStack().getItem().equals(Items.END_CRYSTAL)) {
		            for (i = 9; i <= 36; i++) {
		                if (mc.player.getInventory().getStack(i).getItem().equals(Items.END_CRYSTAL)) {
		                    found = true;
		                    break;
		                }
		            }
		            if (!(mc.player.getOffHandStack().getItem().equals(Items.END_CRYSTAL)) && found) {
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
		            }
		        }
	        }
	        else if (item.isMode("Shield")) {
	        	if (!mc.player.getOffHandStack().getItem().equals(Items.SHIELD)) {
		            for (i = 9; i <= 36; i++) {
		                if (mc.player.getInventory().getStack(i).getItem().equals(Items.SHIELD)) {
		                    found = true;
		                    break;
		                }
		            }
		            if (!(mc.player.getOffHandStack().getItem().equals(Items.SHIELD)) && found) {
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
		            }
		        }
	        }
	        else if (item.isMode("Nether Sword")) {
	        	if (!mc.player.getOffHandStack().getItem().equals(Items.NETHERITE_SWORD)) {
		            for (i = 9; i <= 36; i++) {
		                if (mc.player.getInventory().getStack(i).getItem().equals(Items.NETHERITE_SWORD)) {
		                    found = true;
		                    break;
		                }
		            }
		            if (!(mc.player.getOffHandStack().getItem().equals(Items.NETHERITE_SWORD)) && found) {
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
		            }
		        }
	        }
	        if(mc.player.fallDistance == 12) {
	        	if (!mc.player.getOffHandStack().getItem().equals(Items.TOTEM_OF_UNDYING)) {
		            for (i = 9; i <= 36; i++) {
		                if (mc.player.getInventory().getStack(i).getItem().equals(Items.TOTEM_OF_UNDYING)) {
		                    found = true;
		                    break;
		                }
		            }
		            if (!(mc.player.getOffHandStack().getItem().equals(Items.TOTEM_OF_UNDYING)) && found) {
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.PICKUP, mc.player);
		                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, mc.player);
		            }
		        }
	        }
		super.onTick();
	}
}
