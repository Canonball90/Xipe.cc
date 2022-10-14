package xipe.module.modules.Render;

import java.awt.Color;
import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import xipe.event.events.EventRender3D;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.utils.render.RenderUtils;

public class ESP extends Mod {

	public ModeSetting mode = new ModeSetting("Mode", "Rect", "Rect", "Box", "Test", "Outline");
	public ModeSetting color = new ModeSetting("Color","Red", "Red", "Green", "Blue");
	
	public BooleanSetting fillBox = new BooleanSetting("Box Filled", true);
	public BooleanSetting outlineBox = new BooleanSetting("Outline box", true);
	
	public ESP() {
		super("ESP", "ESP hack", Category.RENDER);
		addSettings(mode,color,fillBox,outlineBox);
	}
	
	private static final Formatting Gray = Formatting.GRAY;

    @Override
	public void onWorldRender(MatrixStack matrices) {
		this.setDisplayName("ESP" + Gray + "["+mode.getMode()+"]");

		if (this.isEnabled()) {
			for (Entity entity  : mc.world.getEntities()) {
				if (!(entity instanceof ClientPlayerEntity)) {
					if (mode.isMode("Rect")) {
						if(fillBox.isEnabled()) {
							if(color.isMode("Red")) {
								renderOutline1(entity, new Color(255, 20, 20), matrices);
							}else if(color.isMode("Green")) {
								renderOutline1(entity, new Color(20, 255, 20), matrices);
							}else if(color.isMode("Blue")) {
								renderOutline1(entity, new Color(20, 20, 255), matrices);
							}
						}
					if(outlineBox.isEnabled()) {
						if(color.isMode("Red")) {
								renderOutline(entity, new Color(255, 20, 20), matrices);
						}else if(color.isMode("Green")) {
								renderOutline(entity, new Color(20, 255, 20), matrices);
						}else if(color.isMode("Blue")) {
								renderOutline(entity, new Color(20, 20, 255), matrices);
						}
						}
					}
					Vec3d renderPos = RenderUtils.getEntityRenderPosition(entity, EventRender3D.getTickDelta());
//?					Box bb = new Box(renderPos.x - entity.getWidth() + 0.25, renderPos.y, renderPos.z - entity.getWidth() + 0.25, renderPos.x + entity.getWidth() - 0.25, renderPos.y + entity.getHeight() + 0.1, renderPos.z + entity.getWidth() - 0.25);
					//if (mode.is("Box")) RenderUtils.drawOutlineBox(matrices, bb, new Color(255, 0, 0), true);
					if (mode.isMode("Box")) RenderUtils.drawEntityBox(matrices, entity, renderPos.x, renderPos.y, renderPos.z, new Color(230, 30, 30));
				}
			}
		}
		super.onWorldRender(matrices);
	}
	
	void renderOutline(Entity e, Color color, MatrixStack stack) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;
        Camera c = mc.gameRenderer.getCamera();
        Vec3d camPos = c.getPos();
        Vec3d start = e.getPos().subtract(camPos);
        float x = (float) start.x;
        float y = (float) start.y;
        float z = (float) start.z;

        double r = Math.toRadians(-c.getYaw() + 90);
        float sin = (float) (Math.sin(r) * (e.getWidth() / 1.7));
        float cos = (float) (Math.cos(r) * (e.getWidth() / 1.7));
        stack.push();

        Matrix4f matrix = stack.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        RenderSystem.setShaderColor(1f, 1f, 1f, (float) 0.50);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();   //debug lines
        buffer.begin(VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION_COLOR);

        buffer.vertex(matrix, x + sin, y, z + cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y, z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y, z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y + e.getHeight(), z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y + e.getHeight(), z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x + sin, y + e.getHeight(), z + cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x + sin, y + e.getHeight(), z + cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x + sin, y, z + cos).color(red, green, blue, alpha).next();

        BufferRenderer.drawWithShader(buffer.end());
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableBlend();
        stack.pop();
    }
	
	void renderOutline1(Entity e, Color color, MatrixStack stack) {
        float red = color.getRed() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        float alpha = color.getAlpha() / 255f;
        Camera c = mc.gameRenderer.getCamera();
        Vec3d camPos = c.getPos();
        Vec3d start = e.getPos().subtract(camPos);
        float x = (float) start.x;
        float y = (float) start.y;
        float z = (float) start.z;

        double r = Math.toRadians(-c.getYaw() + 90);
        float sin = (float) (Math.sin(r) * (e.getWidth() / 1.7));
        float cos = (float) (Math.cos(r) * (e.getWidth() / 1.7));
        stack.push();

        Matrix4f matrix = stack.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        GL11.glDepthFunc(GL11.GL_ALWAYS);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableBlend();   //debug lines
        buffer.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION_COLOR);

        buffer.vertex(matrix, x + sin, y, z + cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y, z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y, z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y + e.getHeight(), z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x - sin, y + e.getHeight(), z - cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x + sin, y + e.getHeight(), z + cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x + sin, y + e.getHeight(), z + cos).color(red, green, blue, alpha).next();
        buffer.vertex(matrix, x + sin, y, z + cos).color(red, green, blue, alpha).next();

        BufferRenderer.drawWithShader(buffer.end());
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableBlend();
        stack.pop();
    }
}