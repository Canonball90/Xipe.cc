package xipe.ui.screens.clickgui.setting;
/*
import net.minecraft.util.Identifier;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.Tessellator;
import java.util.function.Supplier;
import net.minecraft.client.render.GameRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.MathHelper;
import xipe.module.settings.ColorSetting;
import xipe.module.settings.Setting;
import xipe.ui.screens.clickgui.ModuleButton;
import xipe.utils.FontRenderer;
import xipe.utils.RenderUtils;

import java.awt.Color;
import net.minecraft.client.gui.DrawableHelper;
import java.util.Objects;
import net.minecraft.client.util.math.MatrixStack;

public class ColorBox extends Component
{
    protected static FontRenderer fontSmall;
    private ColorSetting colorSet;
    private boolean lmDown;
    private boolean rmDown;
    public boolean open;
    public float h;
    public float s;
    public float v;
    int sx;
    int sy;
    int ex;
    int ey;
    
    public ColorBox(final Setting setting, final ModuleButton parent, final int offset) {
        super(setting, parent, offset);
        this.colorSet = (ColorSetting)this.setting;
        this.lmDown = false;
        this.rmDown = false;
        this.open = false;
        this.offset = offset;
        this.colorSet = (ColorSetting)setting;
        this.colorSet.name = this.colorSet.name;
        this.h = this.colorSet.hue;
        this.s = this.colorSet.sat;
        this.v = this.colorSet.bri;
    }
    
    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        this.offset = 75;
        this.colorSet.name = this.colorSet.name;
        this.sx = this.parent.parent.getX() + 5;
        this.sy = this.parent.parent.getY() + 4 + this.parent.offset + this.offset + this.parent.parent.height + 12;
        this.ex = this.parent.parent.getX() + this.parent.parent.width - 17;
        this.ey = this.parent.parent.getY() + 4 + this.parent.offset + this.offset + this.parent.parent.height + this.getHeight(this.parent.parent.width) + 8;
        final int n = this.parent.parent.height / 2;
        Objects.requireNonNull(this.mc.textRenderer);
        final int offsetY = n - 9 / 2;
        DrawableHelper.fill(matrices, this.parent.parent.x, this.parent.parent.y + this.parent.offset + this.offset, this.parent.parent.x + this.parent.parent.width, this.parent.parent.y + this.parent.offset + this.offset + this.parent.parent.height * (this.open ? 7 : 1), -14277082);
        ColorBox.fontSmall.draw(matrices, invokedynamic(makeConcatWithConstants:"(Ljava/lang/String;)Ljava/lang/String;", this.colorSet.name), (float)(this.parent.parent.x + offsetY), (float)(this.parent.parent.y + this.parent.offset + this.offset + offsetY - 6), -9145228, false);
        ColorBox.fontSmall.draw(matrices, invokedynamic(makeConcatWithConstants:"(Ljava/lang/String;)Ljava/lang/String;", this.colorSet.getHex().toUpperCase()), this.parent.parent.x + 5 + offsetY + ColorBox.fontSmall.getStringWidth(this.colorSet.name, false) + (this.open ? 12 : 2), (float)(this.parent.parent.y + this.parent.offset + this.offset + offsetY - 6), this.colorSet.getRGB(), false);
        if (this.hovered(mouseX, mouseY, (int)(this.parent.parent.x + ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:"(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", this.colorSet.name, this.colorSet.getHex().toUpperCase()), false) + 8.0f), this.parent.parent.y + this.parent.offset + this.offset + offsetY, (int)(this.parent.parent.x + ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:"(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String", this.colorSet.name, this.colorSet.getHex().toUpperCase()), false) + 25.0f), (int)(this.parent.parent.y + this.parent.offset + this.offset + offsetY + 8.5)) && !this.open && this.rmDown) {
            this.open = true;
        }
        if (!this.open) {
            RenderUtils.fill(matrices, this.parent.parent.x + ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name, this.colorSet.getHex().toUpperCase()), false) + 8.0f, this.parent.parent.y + this.parent.offset + this.offset + offsetY, this.parent.parent.x + this.parent.parent.width - 3.5, this.parent.parent.y + this.parent.offset + this.offset + offsetY + 8.5, this.colorSet.getColor().getRGB());
            return;
        }
        RenderUtils.fill(matrices, this.sx + 3 + (int)ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name, this.colorSet.getHex().toUpperCase()), false) + 17, this.sy - 4, this.sx + 27 + (int)ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name, this.colorSet.getHex().toUpperCase()), false), this.sy - 12, new Color(0, 0, 0, 200).getRGB());
        RenderUtils.fill(matrices, this.sx, this.sy, this.ex, this.ey, -1);
        final int satColor = MathHelper.hsvToRgb(this.colorSet.hue, 1.0f, 1.0f);
        final int red = satColor >> 16 & 0xFF;
        final int green = satColor >> 8 & 0xFF;
        final int blue = satColor & 0xFF;
        RenderSystem.disableBlend();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader((Supplier)GameRenderer::getPositionColorShader);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex((double)this.ex, (double)this.sy, 0.0).color(red, green, blue, 255).next();
        bufferBuilder.vertex((double)this.sx, (double)this.sy, 0.0).color(red, green, blue, 0).next();
        bufferBuilder.vertex((double)this.sx, (double)this.ey, 0.0).color(red, green, blue, 0).next();
        bufferBuilder.vertex((double)this.ex, (double)this.ey, 0.0).color(red, green, blue, 255).next();
        tessellator.draw();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex((double)this.ex, (double)this.sy, 0.0).color(0, 0, 0, 0).next();
        bufferBuilder.vertex((double)this.sx, (double)this.sy, 0.0).color(0, 0, 0, 0).next();
        bufferBuilder.vertex((double)this.sx, (double)this.ey, 0.0).color(0, 0, 0, 255).next();
        bufferBuilder.vertex((double)this.ex, (double)this.ey, 0.0).color(0, 0, 0, 255).next();
        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        if (this.hovered(mouseX, mouseY, this.sx, this.sy, this.ex, this.ey) && this.lmDown) {
            this.colorSet.bri = 1.0f - 1.0f / ((this.ey - this.sy) / (float)(mouseY - this.sy));
            this.colorSet.sat = 1.0f / ((this.ex - this.sx) / (float)(mouseX - this.sx));
        }
        final int briY = (int)(this.ey - (this.ey - this.sy) * this.colorSet.bri);
        final int satX = (int)(this.sx + (this.ex - this.sx) * this.colorSet.sat);
        RenderUtils.fill(matrices, satX - 2, briY - 2, satX + 2, briY + 2, Color.GRAY.brighter().getRGB(), Color.WHITE.darker().getRGB(), Color.WHITE.getRGB());
        RenderUtils.fill(matrices, this.parent.parent.x + ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name), false) + 5.0f, this.parent.parent.y + this.parent.offset + this.offset + offsetY, this.parent.parent.x + ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name), false) + 13.0f, this.parent.parent.y + this.parent.offset + this.offset + offsetY + 8.5, this.colorSet.getColor().getRGB());
        if (this.hovered(mouseX, mouseY, (int)(this.parent.parent.x + ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name), false) + 5.0f), this.parent.parent.y + this.parent.offset + this.offset + offsetY, (int)(this.parent.parent.x + ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name), false) + 13.0f), (int)(this.parent.parent.y + this.parent.offset + this.offset + offsetY + 8.5)) && this.open && this.rmDown) {
            this.open = false;
        }
        if (this.hovered(mouseX, mouseY, this.sx + 3 + (int)ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name, this.colorSet.getHex().toUpperCase()), false) + 17, this.sy - 12, this.sx + 27 + (int)ColorBox.fontSmall.getStringWidth(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, this.colorSet.name, this.colorSet.getHex().toUpperCase()), false), this.sy - 4) && this.lmDown && this.colorSet.getColor() != ColorUtils.hexToRgb(this.mc.keyboard.getClipboard())) {
            final Color hexColor = ColorUtils.hexToRgb(this.mc.keyboard.getClipboard());
            final float[] vals = this.colorSet.rgbToHsv((float)hexColor.getRed(), (float)hexColor.getGreen(), (float)hexColor.getBlue(), (float)hexColor.getAlpha());
            this.colorSet.setHSV(vals[0], vals[1], vals[2]);
            this.h = vals[0];
            this.s = vals[1];
            this.v = vals[2];
        }
        this.sx = this.ex + 5;
        this.ex += 12;
        for (int i = this.sy; i < this.ey; ++i) {
            final float curHue = 1.0f / ((this.ey - this.sy) / (float)(i - this.sy));
            DrawableHelper.fill(matrices, this.sx, i, this.ex, i + 1, 0xFF000000 | MathHelper.hsvToRgb(curHue, 1.0f, 1.0f));
        }
        if (this.hovered(mouseX, mouseY, this.sx, this.sy, this.ex, this.ey) && this.lmDown) {
            this.colorSet.hue = 1.0f / ((this.ey - this.sy) / (float)(mouseY - this.sy));
        }
        final int hueY = (int)(this.sy + (this.ey - this.sy) * this.colorSet.hue);
        RenderUtils.fill(matrices, this.sx, hueY - 2, this.ex, hueY + 2, Color.GRAY.brighter().getRGB(), Color.WHITE.darker().getRGB(), Color.WHITE.getRGB());
        super.render(matrices, mouseX, mouseY, this.parent.offset + this.offset);
    }
    
    public int getHeight(final int len) {
        return len - len / 4 - 1;
    }
    
    public boolean hovered(final int mouseX, final int mouseY, final int x1, final int y1, final int x2, final int y2) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }
    
    @Override
    public void mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (button == 0) {
            this.lmDown = true;
        }
        if (button == 1) {
            this.rmDown = true;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public void mouseReleased(final double mouseX, final double mouseY, final int button) {
        if (button == 0) {
            this.lmDown = false;
        }
        if (button == 1) {
            this.rmDown = false;
        }
        super.mouseReleased(mouseX, mouseY, button);
    }
    
    static {
        ColorBox.fontSmall = new FontRenderer("Montserrat.otf", new Identifier("xipe", "fonts"), 18.0f);
    }
}
*/

