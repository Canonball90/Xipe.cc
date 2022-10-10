package xipe.ui.screens.utils;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import xipe.Client;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;

public class Particle {
	int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
    int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
    
    int r = ModuleManager.INSTANCE.getModule(Gui.class).pRed.getValueInt();
    int g = ModuleManager.INSTANCE.getModule(Gui.class).pGreen.getValueInt();
    int b = ModuleManager.INSTANCE.getModule(Gui.class).pBlue.getValueInt();
    
    public int x;
    public int y;
    int yTicks = 0;

    public Particle() {
        this.x = Client.INSTANCE.getRandomNumber(-2, this.width);
        this.y = this.height;
    }

    public void render(MatrixStack matrices) {
        if (this.yTicks <= this.height) {
            ++this.yTicks;
            DrawableHelper.fill(matrices, this.x, this.y - this.yTicks, this.x + 3, this.y - 3 - this.yTicks,ModuleManager.INSTANCE.getModule(Gui.class).rParticals.isEnabled() ? rainbow(300) : new Color(r,g,b).getRGB());
        }

    }
    
    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}
