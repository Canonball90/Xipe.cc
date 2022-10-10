package xipe.module.modules.Render;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.util.registry.Registry;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;


public class XRay extends Mod {

    public boolean enabledXray;
    public static ArrayList<Block> blocks = new ArrayList<>();
    
    BooleanSetting lava = new BooleanSetting("Lava", true);
    BooleanSetting gold = new BooleanSetting("Gold", true);
    BooleanSetting netherite = new BooleanSetting("Netherite", true);
    BooleanSetting diamonds = new BooleanSetting("Diamonds", true);
    BooleanSetting iron = new BooleanSetting("Iron", true);
    BooleanSetting water = new BooleanSetting("Water", true);
    BooleanSetting grass = new BooleanSetting("Grass", false);
    BooleanSetting dirt = new BooleanSetting("Dirt", false);
    BooleanSetting Obsidian = new BooleanSetting("Obsidian", false);
    BooleanSetting stone = new BooleanSetting("Stone", false);
    BooleanSetting cobbleStone = new BooleanSetting("Cobble Stone", false);
    
    
    public XRay() {
        super("Xray", "See ores through blocks", Category.RENDER);
       // this.setKey(GLFW.GLFW_KEY_X);
        Registry.BLOCK.forEach(block -> {
            if (isGoodBlock(block)) blocks.add(block);
        });
    }
    
    boolean isGoodBlock(Block block) {
        boolean c1 = block == Blocks.LAVA || block == Blocks.CHEST || block == Blocks.FURNACE || block == Blocks.END_GATEWAY || block == Blocks.COMMAND_BLOCK || block == Blocks.ANCIENT_DEBRIS || block == Blocks.NETHER_PORTAL;
        boolean c2 = block instanceof OreBlock || block instanceof RedstoneOreBlock;
        return c1 || c2;
    }
    
    @Override
    public void onEnable() {
        enabledXray = true;
        mc.options.getGamma().setValue(10d);
        mc.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        enabledXray = true;
        mc.options.getGamma().setValue(1d);
        mc.worldRenderer.reload();
    }
    
}
