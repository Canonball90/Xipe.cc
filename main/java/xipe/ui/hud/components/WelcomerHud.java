package xipe.ui.hud.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import xipe.ui.hud.HudComponent;

import java.util.Objects;


public class WelcomerHud extends HudComponent {
    public  WelcomerHud() {
        super("WelcomerHud", "says hi");
    }

    private static String welcomeMessage;

    public static void setMessage(String string) {
        welcomeMessage = string;
    }

    @Override
    public void render(MatrixStack matrixStack) {
        String Welcomer = (welcomeMessage + MinecraftClient.getInstance().player.getName().toString() + " :3");
        int length = MinecraftClient.getInstance().textRenderer.getWidth(welcomeMessage + MinecraftClient.getInstance().getName().toString() + " :3") / 2;
        MinecraftClient.getInstance().textRenderer.draw(matrixStack, Welcomer, getPosX() - length, getPosY(), -1);
    }

}
