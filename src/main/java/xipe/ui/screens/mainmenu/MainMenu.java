package xipe.ui.screens.mainmenu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class MainMenu extends Screen {

    public MainMenu() {
        super(Text.translatable("narrator.screen.title"));
    }

    public void render(int mouseX, int mouseY, float delta) {
        MinecraftClient.getInstance().textRenderer.draw(new MatrixStack(), "Presented By: CanonBall90, Nitaki_, and xracer", 10, 10, -1);
    }
}


