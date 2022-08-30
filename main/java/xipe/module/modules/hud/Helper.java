package xipe.module.modules.hud;

import net.minecraft.client.MinecraftClient;
import xipe.module.Mod;

public class Helper extends Mod {
	
	public static MinecraftClient mc = MinecraftClient.getInstance();

    public Helper() {
        super("Helper", "basic tutorial", Category.WORLD);
    }
}
