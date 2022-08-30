package xipe.ui.hud;

import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public abstract class HudComponent {
    private final String name;
    private final String description;
    private static boolean draw;
    private static int posX;
    private static int posY;
    private static int r,g,b;
    private static boolean rainbow;
    private Color color;

    public HudComponent(String name, String description){
        this.name = name;
        this.description = description;
        this.color = new Color(255, 255, 255);
        this.draw = true;
        posX = 2;
        posY = 2;
    }

    public abstract void render(MatrixStack matrixStack);

    public static int getPosX(){ return posX; }

    public static int getPosY(){ return posY; }

    public static boolean getRainbow(){ return rainbow; }

    public final void setRainbow(boolean bool) { rainbow = bool; }

    public final String getName(){ return name; }

    public final String getDescription(){ return description; }

    public static boolean getDrawn(){ return draw; }

    public final void setPosX(int x){ posX = x; }

    public final void setPosY(int y){ posY = y; }

    public final void transfer(int x, int y, MatrixStack matrixStack, Color color){
        setPosX(x);
        setPosY(y);
        this.render(matrixStack);
        this.color = color;
    }

    public static void setDrawn(boolean bool){ draw = bool; }

    public final Color getColor() {
        return color;
    }

    public final void setColor(Color color) {
        this.color = color;
    }
}
