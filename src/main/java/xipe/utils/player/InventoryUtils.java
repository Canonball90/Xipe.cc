package xipe.utils.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import xipe.utils.mixin.IClientPlayerInteractionManager;
import xipe.utils.world.FindItemResult;

import java.util.stream.IntStream;
import java.util.Comparator;
import net.minecraft.screen.slot.SlotActionType;
import net.fabricmc.loader.impl.game.GameProviderHelper.FindResult;
import net.minecraft.block.BlockState;
import java.util.function.Predicate;
import net.minecraft.item.Item;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.SmokerScreenHandler;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import java.util.Iterator;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.item.ItemStack;
import java.util.Map;
import net.minecraft.client.MinecraftClient;

public class InventoryUtils
{
    public static MinecraftClient mc;
    private static final Action ACTION;
    public static int previousSlot;
    public static final int HOTBAR_START = 0;
    public static final int HOTBAR_END = 8;
    public static final int OFFHAND = 45;
    public static final int MAIN_START = 9;
    public static final int MAIN_END = 35;
    public static final int ARMOR_START = 36;
    public static final int ARMOR_END = 39;
    
    public static Map<Integer, ItemStack> getSlots(final int start, final int end) {
        final Map<Integer, ItemStack> slots = new ConcurrentHashMap<Integer, ItemStack>();
        for (int i = start; i < end; ++i) {
            slots.put(i, InventoryUtils.mc.player.getInventory().getStack(i));
        }
        return slots;
    }
    
    public static Map<Integer, ItemStack> getSlots(final ScreenHandler container, final int start, final int end) {
        final Map<Integer, ItemStack> slots = new ConcurrentHashMap<Integer, ItemStack>();
        for (int i = start; i < end; ++i) {
            slots.put(i, container.getSlot(i).getStack());
        }
        return slots;
    }
    
    public static boolean isInventoryFull() {
        for (final Integer slot : getSlots(0, 35).keySet()) {
            if (InventoryUtils.mc.player.getInventory().getStack((int)slot).getItem() == Items.AIR) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isContainerEmpty(final ScreenHandler container) {
        for (final Integer slot : getSlots(0, 35).keySet()) {
            if (container.getSlot((int)slot).getStack().getItem() == Items.AIR) {
                return false;
            }
        }
        return true;
    }
    
    public static int indexToId(final int i) {
        if (InventoryUtils.mc.player == null) {
            return -1;
        }
        final ScreenHandler handler = InventoryUtils.mc.player.currentScreenHandler;
        if (handler instanceof PlayerScreenHandler) {
            return survivalInventory(i);
        }
        if (handler instanceof GenericContainerScreenHandler) {
            return genericContainer(i, ((GenericContainerScreenHandler)handler).getRows());
        }
        if (handler instanceof CraftingScreenHandler) {
            return craftingTable(i);
        }
        if (handler instanceof FurnaceScreenHandler) {
            return furnace(i);
        }
        if (handler instanceof BlastFurnaceScreenHandler) {
            return furnace(i);
        }
        if (handler instanceof SmokerScreenHandler) {
            return furnace(i);
        }
        if (handler instanceof Generic3x3ContainerScreenHandler) {
            return generic3x3(i);
        }
        if (handler instanceof EnchantmentScreenHandler) {
            return enchantmentTable(i);
        }
        if (handler instanceof BrewingStandScreenHandler) {
            return brewingStand(i);
        }
        if (handler instanceof MerchantScreenHandler) {
            return villager(i);
        }
        if (handler instanceof BeaconScreenHandler) {
            return beacon(i);
        }
        if (handler instanceof AnvilScreenHandler) {
            return anvil(i);
        }
        if (handler instanceof HopperScreenHandler) {
            return hopper(i);
        }
        if (handler instanceof ShulkerBoxScreenHandler) {
            return genericContainer(i, 3);
        }
        if (handler instanceof CartographyTableScreenHandler) {
            return cartographyTable(i);
        }
        if (handler instanceof GrindstoneScreenHandler) {
            return grindstone(i);
        }
        if (handler instanceof LecternScreenHandler) {
            return lectern();
        }
        if (handler instanceof LoomScreenHandler) {
            return loom(i);
        }
        if (handler instanceof StonecutterScreenHandler) {
            return stonecutter(i);
        }
        return -1;
    }
    
    private static int survivalInventory(final int i) {
        if (isHotbar(i)) {
            return 36 + i;
        }
        if (isArmor(i)) {
            return 5 + (i - 36);
        }
        return i;
    }
    
    private static int genericContainer(final int i, final int rows) {
        if (isHotbar(i)) {
            return (rows + 3) * 9 + i;
        }
        if (isMain(i)) {
            return rows * 9 + (i - 9);
        }
        return -1;
    }
    
    private static int craftingTable(final int i) {
        if (isHotbar(i)) {
            return 37 + i;
        }
        if (isMain(i)) {
            return i + 1;
        }
        return -1;
    }
    
    private static int furnace(final int i) {
        if (isHotbar(i)) {
            return 30 + i;
        }
        if (isMain(i)) {
            return 3 + (i - 9);
        }
        return -1;
    }
    
    private static int generic3x3(final int i) {
        if (isHotbar(i)) {
            return 36 + i;
        }
        if (isMain(i)) {
            return i;
        }
        return -1;
    }
    
    private static int enchantmentTable(final int i) {
        if (isHotbar(i)) {
            return 29 + i;
        }
        if (isMain(i)) {
            return 2 + (i - 9);
        }
        return -1;
    }
    
    private static int brewingStand(final int i) {
        if (isHotbar(i)) {
            return 32 + i;
        }
        if (isMain(i)) {
            return 5 + (i - 9);
        }
        return -1;
    }
    
    private static int villager(final int i) {
        if (isHotbar(i)) {
            return 30 + i;
        }
        if (isMain(i)) {
            return 3 + (i - 9);
        }
        return -1;
    }
    
    private static int beacon(final int i) {
        if (isHotbar(i)) {
            return 28 + i;
        }
        if (isMain(i)) {
            return 1 + (i - 9);
        }
        return -1;
    }
    
    private static int anvil(final int i) {
        if (isHotbar(i)) {
            return 30 + i;
        }
        if (isMain(i)) {
            return 3 + (i - 9);
        }
        return -1;
    }
    
    private static int hopper(final int i) {
        if (isHotbar(i)) {
            return 32 + i;
        }
        if (isMain(i)) {
            return 5 + (i - 9);
        }
        return -1;
    }
    
    private static int cartographyTable(final int i) {
        if (isHotbar(i)) {
            return 30 + i;
        }
        if (isMain(i)) {
            return 3 + (i - 9);
        }
        return -1;
    }
    
    private static int grindstone(final int i) {
        if (isHotbar(i)) {
            return 30 + i;
        }
        if (isMain(i)) {
            return 3 + (i - 9);
        }
        return -1;
    }
    
    private static int lectern() {
        return -1;
    }
    
    private static int loom(final int i) {
        if (isHotbar(i)) {
            return 31 + i;
        }
        if (isMain(i)) {
            return 4 + (i - 9);
        }
        return -1;
    }
    
    private static int stonecutter(final int i) {
        if (isHotbar(i)) {
            return 29 + i;
        }
        if (isMain(i)) {
            return 2 + (i - 9);
        }
        return -1;
    }
    
    public static boolean isHotbar(final int i) {
        return i >= 0 && i <= 8;
    }
    
    public static boolean isMain(final int i) {
        return i >= 9 && i <= 35;
    }
    
    public static boolean isArmor(final int i) {
        return i >= 36 && i <= 39;
    }
    /*
    public static FindItemResult find(final Item... items) {
        final int length;
        int i = 0;
        Item item;
        return find(itemStack -> {
            length = items.length;
            while (i < length) {
                item = items[i];
                if (itemStack.getItem() == item) {
                    return true;
                }
                else {
                    ++i;
                }
            }
            return false;
        });
    }
    */
    
    public static FindItemResult find(final Predicate<ItemStack> isGood) {
        return find(isGood, 0, InventoryUtils.mc.player.getInventory().size());
    }
    
    public static FindItemResult find(final Predicate<ItemStack> isGood, final int start, final int end) {
        int slot = -1;
        int count = 0;
        for (int i = start; i <= end; ++i) {
            final ItemStack stack = InventoryUtils.mc.player.getInventory().getStack(i);
            if (isGood.test(stack)) {
                if (slot == -1) {
                    slot = i;
                }
                count += stack.getCount();
            }
        }
        return new FindItemResult(slot, count);
    }
    
    public static FindItemResult find(Item... items) {
        return find(itemStack -> {
            for (Item item : items) {
                if (itemStack.getItem() == item) return true;
            }
            return false;
        });
    }

    public static int findSlot(Item item) {
        for (int j = 0; j < 9; j++) {
            if (mc.player.getInventory().getStack(j).getItem() == item) {
                return j;
            }
        }
        return -1;
    }
    
    public static FindItemResult findFastestTool(final BlockState state) {
        float bestScore = -1.0f;
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final float score = InventoryUtils.mc.player.getInventory().getStack(i).getMiningSpeedMultiplier(state);
            if (score > bestScore) {
                bestScore = score;
                slot = i;
            }
        }
        return new FindItemResult(slot, 1);
    }
    
    public static Action click() {
        InventoryUtils.ACTION.type = SlotActionType.PICKUP;
        return InventoryUtils.ACTION;
    }
    
    public static Action move() {
        InventoryUtils.ACTION.type = SlotActionType.PICKUP;
        InventoryUtils.ACTION.two = true;
        return InventoryUtils.ACTION;
    }
    
    public static boolean swap(final int slot, final boolean swapBack) {
        if (slot < 0 || slot > 8) {
            return false;
        }
        if (swapBack && InventoryUtils.previousSlot == -1) {
            InventoryUtils.previousSlot = InventoryUtils.mc.player.getInventory().selectedSlot;
        }
        InventoryUtils.mc.player.getInventory().selectedSlot = slot;
        ((IClientPlayerInteractionManager)InventoryUtils.mc.interactionManager).syncSelected();
        return true;
    }
    
    public static boolean swapBack() {
        if (InventoryUtils.previousSlot == -1) {
            return false;
        }
        final boolean return_ = swap(InventoryUtils.previousSlot, false);
        InventoryUtils.previousSlot = -1;
        return return_;
    }
    /*
    public static FindItemResult findInHotbar(final Item... items) {
        final int length;
        int i = 0;
        Item item;
        return findInHotbar(itemStack -> {
            //length = items.length;
            while (i < length) {
                item = items[i];
                if (itemStack.getItem() == item) {
                    return true;
                }
                else {
                    ++i;
                }
            }
            return false;
        });
    }
    */
    
    public static FindItemResult findInHotbar(final Predicate<ItemStack> isGood) {
        if (isGood.test(InventoryUtils.mc.player.getOffHandStack())) {
            return new FindItemResult(45, InventoryUtils.mc.player.getOffHandStack().getCount());
        }
        if (isGood.test(InventoryUtils.mc.player.getMainHandStack())) {
            return new FindItemResult(InventoryUtils.mc.player.getInventory().selectedSlot, InventoryUtils.mc.player.getMainHandStack().getCount());
        }
        return find(isGood, 0, 8);
    }
    
    public static int findInHotbarInt(final Item item) {
        int index = -1;
        for (int i = 0; i < 9; ++i) {
            if (InventoryUtils.mc.player.getInventory().getStack(i).getItem() == item) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    public static int getSlot(final boolean offhand, final boolean reverse, final Comparator<Integer> comparator) {
        return IntStream.of(getInventorySlots(offhand)).boxed().min(comparator.reversed()).orElse(-1);
    }
    
    public static Hand selectSlot(final boolean offhand, final boolean reverse, final Comparator<Integer> comparator) {
        return selectSlot(getSlot(offhand, reverse, comparator));
    }
    
    public static Hand selectSlot(final boolean offhand, final Predicate<Integer> filter) {
        return selectSlot(getSlot(offhand, filter));
    }
    
    public static int getSlot(final boolean offhand, final Predicate<Integer> filter) {
        return IntStream.of(getInventorySlots(offhand)).boxed().filter(filter).findFirst().orElse(-1);
    }
    
    public static Hand selectSlot(final int slot) {
        if (slot >= 0 && slot <= 36) {
            if (slot < 9) {
                if (slot != InventoryUtils.mc.player.getInventory().selectedSlot) {
                    InventoryUtils.mc.player.getInventory().selectedSlot = slot;
                    InventoryUtils.mc.player.networkHandler.sendPacket((Packet)new UpdateSelectedSlotC2SPacket(slot));
                }
                return Hand.MAIN_HAND;
            }
            if (InventoryUtils.mc.player.playerScreenHandler == InventoryUtils.mc.player.currentScreenHandler) {
                for (int i = 0; i <= 8; ++i) {
                    if (InventoryUtils.mc.player.getInventory().getStack(i).isEmpty()) {
                        InventoryUtils.mc.interactionManager.clickSlot(InventoryUtils.mc.player.currentScreenHandler.syncId, slot, 0, SlotActionType.QUICK_MOVE, (PlayerEntity)InventoryUtils.mc.player);
                        if (i != InventoryUtils.mc.player.getInventory().selectedSlot) {
                            InventoryUtils.mc.player.getInventory().selectedSlot = i;
                            InventoryUtils.mc.player.networkHandler.sendPacket((Packet)new UpdateSelectedSlotC2SPacket(i));
                        }
                        return Hand.MAIN_HAND;
                    }
                }
                InventoryUtils.mc.interactionManager.clickSlot(InventoryUtils.mc.player.currentScreenHandler.syncId, slot, 0, SlotActionType.PICKUP, (PlayerEntity)InventoryUtils.mc.player);
                InventoryUtils.mc.interactionManager.clickSlot(InventoryUtils.mc.player.currentScreenHandler.syncId, 36 + InventoryUtils.mc.player.getInventory().selectedSlot, 0, SlotActionType.PICKUP, (PlayerEntity)InventoryUtils.mc.player);
                InventoryUtils.mc.interactionManager.clickSlot(InventoryUtils.mc.player.currentScreenHandler.syncId, slot, 0, SlotActionType.PICKUP, (PlayerEntity)InventoryUtils.mc.player);
                return Hand.MAIN_HAND;
            }
        }
        else if (slot == 40) {
            return Hand.OFF_HAND;
        }
        return null;
    }
    
    private static int[] getInventorySlots(final boolean offhand) {
        final int[] i = new int[offhand ? 38 : 37];
        i[0] = InventoryUtils.mc.player.getInventory().selectedSlot;
        i[1] = 40;
        for (int j = 0; j < 36; ++j) {
            if (j != InventoryUtils.mc.player.getInventory().selectedSlot) {
                i[offhand ? (j + 2) : (j + 1)] = j;
            }
        }
        return i;
    }
    
    public static int getSlotIndex(final int index) {
        return (index < 9) ? (index + 36) : index;
    }
    
    static {
        InventoryUtils.mc = MinecraftClient.getInstance();
        ACTION = new Action();
        InventoryUtils.previousSlot = -1;
    }
    
    public static class Action
    {
        private SlotActionType type;
        private boolean two;
        private int from;
        private int to;
        private int data;
        private boolean isRecursive;
        
        private Action() {
            this.type = null;
            this.two = false;
            this.from = -1;
            this.to = -1;
            this.data = 0;
            this.isRecursive = false;
        }
        
        public Action fromId(final int id) {
            this.from = id;
            return this;
        }
        
        public Action from(final int index) {
            return this.fromId(InventoryUtils.indexToId(index));
        }
        
        public Action fromHotbar(final int i) {
            return this.from(0 + i);
        }
        
        public Action fromOffhand() {
            return this.from(45);
        }
        
        public Action fromMain(final int i) {
            return this.from(9 + i);
        }
        
        public Action fromArmor(final int i) {
            return this.from(36 + (3 - i));
        }
        
        public void toId(final int id) {
            this.to = id;
            this.run();
        }
        
        public void to(final int index) {
            this.toId(InventoryUtils.indexToId(index));
        }
        
        public void toHotbar(final int i) {
            this.to(0 + i);
        }
        
        public void toOffhand() {
            this.to(45);
        }
        
        public void toMain(final int i) {
            this.to(9 + i);
        }
        
        public void toArmor(final int i) {
            this.to(36 + (3 - i));
        }
        
        public void slotId(final int id) {
            this.to = id;
            this.from = id;
            this.run();
        }
        
        public void slot(final int index) {
            this.slotId(InventoryUtils.indexToId(index));
        }
        
        public void slotHotbar(final int i) {
            this.slot(0 + i);
        }
        
        public void slotOffhand() {
            this.slot(45);
        }
        
        public void slotMain(final int i) {
            this.slot(9 + i);
        }
        
        public void slotArmor(final int i) {
            this.slot(36 + (3 - i));
        }
        
        private void run() {
            final boolean hadEmptyCursor = InventoryUtils.mc.player.currentScreenHandler.getCursorStack().isEmpty();
            if (this.type != null && this.from != -1 && this.to != -1) {
                this.click(this.from);
                if (this.two) {
                    this.click(this.to);
                }
            }
            final SlotActionType preType = this.type;
            final boolean preTwo = this.two;
            final int preFrom = this.from;
            final int preTo = this.to;
            this.type = null;
            this.two = false;
            this.from = -1;
            this.to = -1;
            this.data = 0;
            if (!this.isRecursive && hadEmptyCursor && preType == SlotActionType.PICKUP && preTwo && preFrom != -1 && preTo != -1 && !InventoryUtils.mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
                this.isRecursive = true;
                InventoryUtils.click().slotId(preFrom);
                this.isRecursive = false;
            }
        }
        
        private void click(final int id) {
            InventoryUtils.mc.interactionManager.clickSlot(InventoryUtils.mc.player.currentScreenHandler.syncId, id, this.data, this.type, (PlayerEntity)InventoryUtils.mc.player);
        }
    }
    
    public static FindItemResult findInHotbar(Item... items) {
        return findInHotbar(itemStack -> {
            for (Item item : items) {
                if (itemStack.getItem() == item) return true;
            }
            return false;
        });
    }
    
   
    
}
