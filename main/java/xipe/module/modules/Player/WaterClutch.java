package xipe.module.modules.Player;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;

public class WaterClutch extends Mod {

	public WaterClutch() {
		super("Water Clutch", "Places water below you to negate fall damage", Category.WORLD);
	}
	
	 @Override
	    public void onTick() {
	        Vec3d ppos = mc.player.getPos();
	        Vec3d np = ppos.subtract(0, MathHelper.clamp(-mc.player.getVelocity().getY(), 0, 6), 0);
	        BlockPos bp = new BlockPos(np);
	        BlockState bs = mc.world.getBlockState(bp);
	        if (bs.getFluidState().getLevel() == 0) return;
	        int selIndex = mc.player.getInventory().selectedSlot;
	        if (!(mc.player.getInventory().getStack(selIndex).getItem() instanceof BlockItem))
	            for (int i = 0; i < 9; i++) {
	                ItemStack is = mc.player.getInventory().getStack(i);
	                if (is.getItem() == Items.AIR) continue;
	                if (is.getItem() instanceof BlockItem) {
	                    selIndex = i;
	                    break;
	                }
	            }
	        if (mc.player.getInventory().getStack(selIndex).getItem() != Items.AIR) {
	            // fucking multithreading moment
	            int finalSelIndex = selIndex;
	            //Rotations.lookAtV3(new Vec3d(bp.getX()+.5,bp.getY()+.5, bp.getZ()+.5));
	            mc.execute(() -> {
	                int c = mc.player.getInventory().selectedSlot;
	                mc.player.getInventory().selectedSlot = finalSelIndex;
	                BlockHitResult bhr = new BlockHitResult(np, Direction.DOWN, bp, false);
	                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, bhr);
	                mc.player.getInventory().selectedSlot = c;
	            });
	        }
	    }

}
