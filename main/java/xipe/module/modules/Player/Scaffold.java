package xipe.module.modules.Player;

import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;

public class Scaffold extends Mod {
    public Scaffold() {
        super("Scaffold", "",Category.WORLD);

    }
    
    @Override
    public void onTick() {
    	int original_slot = mc.player.getInventory().selectedSlot;
    	
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() instanceof BlockItem)
                mc.player.getInventory().selectedSlot = i;
        }
        if (mc.player == null || mc.world == null) {onDisable(); return;}
        BlockPos pos = mc.player.getBlockPos().down();
        if (mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(pos), Direction.DOWN, pos, false));
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        
        mc.player.getInventory().selectedSlot = original_slot;
    }
}
