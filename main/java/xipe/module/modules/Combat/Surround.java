package xipe.module.modules.Combat;

import java.util.ArrayList;

import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class Surround extends Mod{
	
	ModeSetting block = new ModeSetting("Mode", "Obsidian", "Obsidian", "Bedrock", "CobbleStone");

	public Surround() {
		super("Surround", "Surrounds u with blocks", Category.COMBAT);
		addSetting(block);
	}
	
	int obiSlot;
    int oldSlot;

    @Override
    public void onTick() {
        if (mc.player == null) return;
        oldSlot = mc.player.getInventory().selectedSlot;
        obiSlot = -1;
        if (!mc.player.isOnGround()) {onDisable(); return;}
        ArrayList<BlockPos> positions = new ArrayList<>();
        positions.add(mc.player.getBlockPos().north());
        positions.add(mc.player.getBlockPos().east());
        positions.add(mc.player.getBlockPos().south());
        positions.add(mc.player.getBlockPos().west());
        for (int i = 0; i < 9; i++) {
           if(block.isMode("Obsidian")) {
        	if (mc.player.getInventory().getStack(i).getItem().equals(Items.OBSIDIAN)) {
                obiSlot = i;
                break;
            }
           }else if(block.isMode("Bedrock")) {
        	   if (mc.player.getInventory().getStack(i).getItem().equals(Items.BEDROCK)) {
                   obiSlot = i;
                   break;
               }
           }else if(block.isMode("CobbleStone")) {
        	   if (mc.player.getInventory().getStack(i).getItem().equals(Items.COBBLESTONE)) {
                   obiSlot = i;
                   break;
               }
        	   
           }
        }
        if (obiSlot == -1) {onDisable(); return;}
        for (BlockPos pos : positions) {
            if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) continue;
            for (Direction direction : Direction.values()) {
                if (!mc.world.getBlockState(pos.offset(direction)).getMaterial().isReplaceable()) {
                    mc.player.getInventory().selectedSlot = obiSlot;
                    mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(pos), direction, pos, false));
                    mc.player.getInventory().selectedSlot = oldSlot;
                }
            }
        }
    }

}
