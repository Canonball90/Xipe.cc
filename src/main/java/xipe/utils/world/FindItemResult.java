package xipe.utils.world;

import net.minecraft.util.Hand;
import net.minecraft.client.MinecraftClient;

public class FindItemResult
{
    public static MinecraftClient mc;
    private final int slot;
    private final int count;
    
    public FindItemResult(final int slot, final int count) {
        this.slot = slot;
        this.count = count;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public boolean found() {
        return this.slot != -1;
    }
    
    public Hand getHand() {
        if (this.slot == 45) {
            return Hand.OFF_HAND;
        }
        if (this.slot == FindItemResult.mc.player.getInventory().selectedSlot) {
            return Hand.MAIN_HAND;
        }
        return null;
    }
    
    public boolean isMainHand() {
        return this.getHand() == Hand.MAIN_HAND;
    }
    
    public boolean isOffhand() {
        return this.getHand() == Hand.OFF_HAND;
    }
    
    public boolean isHotbar() {
        return this.slot >= 0 && this.slot <= 8;
    }
    
    public boolean isMain() {
        return this.slot >= 9 && this.slot <= 35;
    }
    
    public boolean isArmor() {
        return this.slot >= 36 && this.slot <= 39;
    }
    
    static {
        FindItemResult.mc = MinecraftClient.getInstance();
    }
}

