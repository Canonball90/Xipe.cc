package xipe.module.modules.Player;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import xipe.module.Mod;
import xipe.module.settings.ModeSetting;

public class ClientsideGameMode extends Mod {
    
    GameMode old;
    public static GameMode getGameMode(PlayerEntity player) {
        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid()); 
        if (playerListEntry != null) return playerListEntry.getGameMode(); 
        return GameMode.DEFAULT;
    }
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public ModeSetting mode = new ModeSetting("Mode", "Survival", "Survival", "Creative", "Spectator");
    
    public ClientsideGameMode() {
        super("SetGamemode", "Contributed/Given by: Nitaki", Category.EXPLOIT);
        addSettings(mode);
    }

    @Override
    public void onTick() {

        super.onTick();
    }
    
    @Override
    public void onEnable() {
        old = getGameMode(mc.player);
        
        if (mode.isMode("Survival")) {
            mc.interactionManager.setGameMode(GameMode.SURVIVAL);
        } else if (mode.isMode("Creative")) {
            mc.interactionManager.setGameMode(GameMode.CREATIVE);
        } else if (mode.isMode("Spectator")) {
            mc.interactionManager.setGameMode(GameMode.SPECTATOR);
        }
        
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        mc.interactionManager.setGameMode(old);
        super.onDisable();
    }
}