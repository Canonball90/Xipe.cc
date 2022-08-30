package xipe.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import xipe.mixins.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vector4f;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class RenderUtil {
	
	public static MinecraftClient mc = MinecraftClient.getInstance();
	
	public static Frustum getFrustum() {
		return ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getFrustum();
	}
	
	 public static void bindTexture(Identifier identifier) {
	        RenderSystem.setShaderTexture(0, identifier);
	    }
	 
	 public static void shaderColor(int rgb) {
	        float alpha = (rgb >> 24 & 0xFF) / 255.0F;
	        float red = (rgb >> 16 & 0xFF) / 255.0F;
	        float green = (rgb >> 8 & 0xFF) / 255.0F;
	        float blue = (rgb & 0xFF) / 255.0F;
	        RenderSystem.setShaderColor(red, green, blue, alpha);
	    }
	
	 public static void setup3DRender(boolean disableDepth) {
	        RenderSystem.setShader(GameRenderer::getPositionColorShader);
	        RenderSystem.disableTexture();
	        RenderSystem.enableBlend();
	        RenderSystem.defaultBlendFunc();
	        if (disableDepth)
	            RenderSystem.disableDepthTest();
	        RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
	        RenderSystem.enableCull();
	    }
	 
	 public static void end3DRender() {
	        RenderSystem.enableTexture();
	        RenderSystem.disableCull();
	        RenderSystem.disableBlend();
	        RenderSystem.enableDepthTest();
	        RenderSystem.depthMask(true);
	        RenderSystem.setShader(GameRenderer::getPositionColorShader);
	}
	
	
	   public static void renderRoundedQuadInternal(Matrix4f matrix, float cr, float cg, float cb, float ca, double fromX, double fromY, double toX, double toY, double rad, double samples) { 
	        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer(); 
	        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR); 
	 
	        double toX1 = toX - rad; 
	        double toY1 = toY - rad; 
	        double fromX1 = fromX + rad; 
	        double fromY1 = fromY + rad; 
	        double[][] map = new double[][]{new double[]{toX1, toY1}, new double[]{toX1, fromY1}, new double[]{fromX1, fromY1}, new double[]{fromX1, toY1}}; 
	        for (int i = 0; i < 4; i++) { 
	            double[] current = map[i]; 
	            for (double r = i * 90d; r < (360 / 4d + i * 90d); r += (90 / samples)) { 
	                float rad1 = (float) Math.toRadians(r); 
	                float sin = (float) (Math.sin(rad1) * rad); 
	                float cos = (float) (Math.cos(rad1) * rad); 
	                bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F).color(cr, cg, cb, ca).next(); 
	            } 
	        } 
	        BufferRenderer.drawWithShader(bufferBuilder.end()); 
	    }
	   
	   @SuppressWarnings("resource")
	public static void line(Vec3d start, Vec3d end, Color color, MatrixStack matrices) {
	        float red = color.getRed() / 255f;
	        float green = color.getGreen() / 255f;
	        float blue = color.getBlue() / 255f;
	        float alpha = color.getAlpha() / 255f;
	        Camera c = MinecraftClient.getInstance().gameRenderer.getCamera();
	        Vec3d camPos = c.getPos();
	        start = start.subtract(camPos);
	        end = end.subtract(camPos);
	        Matrix4f matrix = matrices.peek().getPositionMatrix();
	        float x1 = (float) start.x;
	        float y1 = (float) start.y;
	        float z1 = (float) start.z;
	        float x2 = (float) end.x;
	        float y2 = (float) end.y;
	        float z2 = (float) end.z;
	        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
	        RenderSystem.setShader(GameRenderer::getPositionColorShader);
	        GL11.glDepthFunc(GL11.GL_ALWAYS);
	        
	        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
	        RenderSystem.defaultBlendFunc();
	        RenderSystem.enableBlend();
	        buffer.begin(VertexFormat.DrawMode.DEBUG_LINES,
	                VertexFormats.POSITION_COLOR);
	        buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha).next();
	        buffer.vertex(matrix, x2, y2, z2).color(red, green, blue, alpha).next();

	        BufferRenderer.drawWithShader(buffer.end());
	        GL11.glDepthFunc(GL11.GL_LEQUAL);
	        RenderSystem.disableBlend();
	    }
	   
	   public static void drawFilledBox(MatrixStack matrixStack, Box bb, Color color, boolean draw) {
	        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
	        Color color1 = color;
	        setup3DRender(true);
	        
	        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
	        if (draw)
	        	bufferBuilder.begin(VertexFormat.DrawMode.QUADS/*QUADS*/, VertexFormats.POSITION_COLOR);
	        float minX = (float)bb.minX;
	        float minY = (float)bb.minY;
	        float minZ = (float)bb.minZ;
	        float maxX = (float)bb.maxX;
	        float maxY = (float)bb.maxY;
	        float maxZ = (float)bb.maxZ;

	        bufferBuilder.vertex(matrix4f, minX, minY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, minY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, minY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, minX, minY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();

	        bufferBuilder.vertex(matrix4f, minX, maxY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, minX, maxY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, maxY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();

	        bufferBuilder.vertex(matrix4f, minX, minY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, minX, maxY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, maxY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, minY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();

	        bufferBuilder.vertex(matrix4f, maxX, minY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, maxY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, minY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();

	        bufferBuilder.vertex(matrix4f, minX, minY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, minY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, maxX, maxY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, minX, maxY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();

	        bufferBuilder.vertex(matrix4f, minX, minY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, minX, minY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, minX, maxY, maxZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        bufferBuilder.vertex(matrix4f, minX, maxY, minZ).color(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha()).next();
	        if (draw) {
		        BufferRenderer.drawWithShader(bufferBuilder.end());
	        }
	        end3DRender();
	    }
	    
	    public static void renderRoundedQuad(MatrixStack matrices, Color c, double fromX, double fromY, double toX, double toY, double rad, double samples) { 
	        int color = c.getRGB(); 
	        Matrix4f matrix = matrices.peek().getPositionMatrix(); 
	        float f = (float) (color >> 24 & 255) / 255.0F; 
	        float g = (float) (color >> 16 & 255) / 255.0F; 
	        float h = (float) (color >> 8 & 255) / 255.0F; 
	        float k = (float) (color & 255) / 255.0F; 
	        RenderSystem.enableBlend(); 
	        RenderSystem.disableTexture(); 
	        RenderSystem.setShader(GameRenderer::getPositionColorShader); 
	 
	        renderRoundedQuadInternal(matrix, g, h, k, f, fromX, fromY, toX, toY, rad, samples); 
	 
	        RenderSystem.enableTexture(); 
	        RenderSystem.disableBlend(); 
	        RenderSystem.setShaderColor(1f, 1f, 1f, 1f); 
	    }
	    
	    public static void drawLine3D(MatrixStack matrixStack, Vec3d pos, Vec3d pos2, Color color) {
			Matrix4f matrix = matrixStack.peek().getPositionMatrix();
			BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
			RenderSystem.setShader(GameRenderer::getPositionShader);
			RenderSystem.setShaderColor(255, 0, 0, 1.0f);

			bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);
			{
				bufferBuilder.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z).next();
				bufferBuilder.vertex(matrix, (float) pos2.x, (float) pos2.y, (float) pos2.z).next();
			}
			bufferBuilder.end();
			BufferRenderer.drawWithShader(bufferBuilder.end()); 
		}
	    
	    public static MatrixStack matrixFrom(double x, double y, double z) {
			MatrixStack matrices = new MatrixStack();

			Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw() + 180.0F));

			matrices.translate(x - camera.getPos().x, y - camera.getPos().y, z - camera.getPos().z);

			return matrices;
		}
	    
	    @SuppressWarnings("resource")
		public static Vec3d center() {
			Vec3d pos = new Vec3d(0, 0, 1);
			
	        return new Vec3d(pos.x, -pos.y, pos.z)
	            .rotateX(-(float) Math.toRadians(MinecraftClient.getInstance().gameRenderer.getCamera().getPitch()))
	            .rotateY(-(float) Math.toRadians(MinecraftClient.getInstance().gameRenderer.getCamera().getYaw()))
	            .add(MinecraftClient.getInstance().gameRenderer.getCamera().getPos());
		}
	    
	    public static void drawBoxOutline(Box box, QuadColor color, float lineWidth, Direction... excludeDirs) {
			if (!getFrustum().isVisible(box)) {
				return;
			}

			setup3DRender(true);

			MatrixStack matrices = matrixFrom(box.minX, box.minY, box.minZ);

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();

			// Outline
			RenderSystem.disableCull();
			RenderSystem.setShader(GameRenderer::getRenderTypeLinesShader);
			RenderSystem.lineWidth(lineWidth);

			buffer.begin(VertexFormat.DrawMode.LINES, VertexFormats.LINES);
			Vertexer.vertexBoxLines(matrices, buffer, Boxes.moveToZero(box), color, excludeDirs);
			tessellator.draw();

			RenderSystem.enableCull();

			end3DRender();
		}
	    
	    public static void drawEntityBox(MatrixStack matrixstack, Entity entity, double x, double y, double z, Color color) {
	        setup3DRender(true);
	        matrixstack.translate(x, y, z);
	        matrixstack.multiply(new Quaternion(new Vec3f(0, -1, 0), 0, true));
	        matrixstack.translate(-x, -y, -z);

	        Box bb = new Box(x - entity.getWidth() + 0.25, y, z - entity.getWidth() + 0.25, x + entity.getWidth() - 0.25, y + entity.getHeight() + 0.1, z + entity.getWidth() - 0.25);
	        if (entity instanceof ItemEntity)
	            bb = new Box(x - 0.15, y + 0.1f, z - 0.15, x + 0.15, y + 0.5, z + 0.15);


	        drawFilledBox(matrixstack, bb, new Color(color.getRed(), color.getGreen(), color.getBlue(), 130), true);
	        RenderSystem.lineWidth(1.5f);

	        //drawOutlineBox(matrixstack, bb, color, true);

	        end3DRender();
	        matrixstack.translate(x, y, z);
	        matrixstack.multiply(new Quaternion(new Vec3f(0, 1, 0), 0, true));
	        matrixstack.translate(-x, -y, -z);
	    }
	    
	    public static void drawBoxFill(Box box, QuadColor color, Direction... excludeDirs) {
			if (!getFrustum().isVisible(box)) {
				return;
			}

			setup3DRender(true);

			MatrixStack matrices = matrixFrom(box.minX, box.minY, box.minZ);

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();

			// Fill
			RenderSystem.setShader(GameRenderer::getPositionColorShader);

			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			Vertexer.vertexBoxQuads(matrices, buffer, Boxes.moveToZero(box), color, excludeDirs);
			tessellator.draw();

			end3DRender();
		}
		
	    public static void setup2DRender(boolean disableDepth) {
	        RenderSystem.enableBlend();
	        RenderSystem.disableTexture();
	        RenderSystem.defaultBlendFunc();
	        if (disableDepth)
	            RenderSystem.disableDepthTest();
	}

	public static void end2DRender() {
	        RenderSystem.disableBlend();
	        RenderSystem.enableTexture();
	        RenderSystem.enableDepthTest();
	}
	
	public static Vec3d getEntityRenderPosition(Entity entity, double partial) {
        double x = entity.prevX + ((entity.getX() - entity.prevX) * partial) - mc.getEntityRenderDispatcher().camera.getPos().x;
        double y = entity.prevY + ((entity.getY() - entity.prevY) * partial) - mc.getEntityRenderDispatcher().camera.getPos().y;
        double z = entity.prevZ + ((entity.getZ() - entity.prevZ) * partial) - mc.getEntityRenderDispatcher().camera.getPos().z;
        return new Vec3d(x, y, z);
}

  
}
