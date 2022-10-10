package xipe.module.modules.Player;

import net.minecraft.client.MinecraftClient;
import xipe.module.Mod;

public class AutoRespawn extends Mod
{
    public static MinecraftClient mc;
    
    public AutoRespawn() {
        super("AutoRespawn", "Automatically makes you respawn", Category.EXPLOIT);
    }
    
    @Override
    public void onTick() {
        if (this.isEnabled() && AutoRespawn.mc.player.isDead()) {
            AutoRespawn.mc.player.requestRespawn();
            AutoRespawn.mc.player.closeScreen();
        }
        super.onTick();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    static {
        AutoRespawn.mc = MinecraftClient.getInstance();
    }
}

