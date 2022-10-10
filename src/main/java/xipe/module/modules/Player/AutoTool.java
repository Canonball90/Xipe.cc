package xipe.module.modules.Player;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import xipe.mixins.ClientPlayerInteractionManagerAccessor;
import xipe.module.Mod;

import java.util.Objects;

public class AutoTool extends Mod {

    boolean prevState = true;
    int slot = 0;

    public AutoTool() {
        super("AutoTool", "Autoswaps to best possible tool in your hotbar", Category.WORLD);
    }

    @Override
    public void onEnable() {
        slot = mc.player.getInventory().selectedSlot;

        super.onEnable();
    }

    @Override
    public void onTick() {
        ClientPlayerInteractionManager interactionManager = mc.interactionManager;

        if(prevState == true && !interactionManager.isBreakingBlock()) {
            mc.player.getInventory().selectedSlot = slot;
        }
        else if(prevState != interactionManager.isBreakingBlock()) {
            slot = mc.player.getInventory().selectedSlot;
        }
        if(interactionManager.isBreakingBlock()) {
            BlockPos blockPos = ((ClientPlayerInteractionManagerAccessor) interactionManager).getCurrentBreakingPos();
            BlockState blockState = mc.world.getBlockState(blockPos);
            swap(blockState);
        }
        prevState = interactionManager.isBreakingBlock();
    }

    public void swap(BlockState state) {
        float best = 1f;
        int index = -1;
        int optAirIndex = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = Objects.requireNonNull(mc.player).getInventory().getStack(i);
            if (stack.getItem() == Items.AIR) {
                optAirIndex = i;
            }
            float s = stack.getMiningSpeedMultiplier(state);
            if (s > best) {
                index = i;
            }
        }
        if (index != -1) {
            mc.player.getInventory().selectedSlot = index;
        } else {
            if (optAirIndex != -1) {
                mc.player.getInventory().selectedSlot = optAirIndex;
            }
        }
    }
}
