package xipe.module.modules.Combat;

import com.google.common.collect.Streams;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.Mod.Category;
import xipe.module.settings.NumberSetting;

import java.util.stream.Collectors;

public class AutoAnchor extends Mod {
   
    NumberSetting range = new NumberSetting("Range", 0.1, 5.0, 4.0, 0.1);
    NumberSetting delay = new NumberSetting("Delay", 0.1, 40.0, 2.0, 1);
	
	public AutoAnchor() {
    	super("AutoAnchor","Automatically places and breaks anchors", Category.COMBAT);
    	addSettings(range,delay);
    	}
    
    int anchorSlot = -1;
    int glowStoneSlot = -1;
    int oldSlot = -1;

    int ticks = 0;

    @Override
    public void onTick() {
        if (mc.world == null || mc.player == null) {onDisable(); return;}
        for (int i = 0; i < 9; i++) {if (mc.player.getInventory().getStack(i).getItem().equals(Items.RESPAWN_ANCHOR)) {anchorSlot = i;} else if (mc.player.getInventory().getStack(i).getItem().equals(Items.GLOWSTONE)) {glowStoneSlot = i;}}
        if (anchorSlot == -1 || glowStoneSlot == -1) {onDisable(); return;}
        if (ticks != delay.getValue()) {ticks++; return;}
        else ticks = 0;
        try {
            PlayerEntity player = (PlayerEntity) Streams.stream(mc.world.getEntities()).filter(e -> e instanceof PlayerEntity && mc.player.distanceTo(e) <= range.getValue() && e != mc.player).collect(Collectors.toList()).get(0);
            for (Direction direction : Direction.values()) {
                BlockPos blockPos = null;
                if (player.getBlockPos().getSquaredDistance(mc.player.getX(), mc.player.getY(), mc.player.getZ()) < 6.0f && !(mc.world.getBlockState(player.getBlockPos()).getBlock() != Blocks.RESPAWN_ANCHOR && mc.world.getBlockState(player.getBlockPos()).getBlock() != Blocks.AIR && mc.world.getBlockState(player.getBlockPos()).getMaterial().isReplaceable())) blockPos = player.getBlockPos();
                else if (player.getBlockPos().offset(direction).getSquaredDistance(mc.player.getX(), mc.player.getY(), mc.player.getZ()) < 6.0f && (mc.world.getBlockState(player.getBlockPos().offset(direction)).getBlock() == Blocks.RESPAWN_ANCHOR || mc.world.getBlockState(player.getBlockPos().offset(direction)).getMaterial().isReplaceable())) blockPos = player.getBlockPos().offset(direction);
                if (blockPos == null) continue;
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(blockPos), Direction.DOWN, blockPos, false));
                if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.RESPAWN_ANCHOR)) {
                    mc.player.getInventory().selectedSlot = glowStoneSlot;
                    mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(blockPos), Direction.DOWN, blockPos, true));
                    mc.interactionManager.interactBlock(mc.player, Hand.OFF_HAND, new BlockHitResult(Vec3d.of(blockPos), Direction.DOWN, blockPos, true));
                }
                mc.player.getInventory().selectedSlot = oldSlot;
                break;
            }
        } catch (Exception ignored) {}
    }
}
