package xipe.ui;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import xipe.Client;
import xipe.module.Mod;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.module.modules.hud.HUD;
import xipe.module.modules.hud.Helper;
import xipe.module.modules.hud.TargetHud;
import xipe.utils.FontRenderer;
import xipe.utils.RenderUtil;

public class Hud {

	private static MinecraftClient mc = MinecraftClient.getInstance();
	
	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	
	static int y = 10;
	static int color = new Color(ModuleManager.INSTANCE.getModule(HUD.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(HUD.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(HUD.class).blue.getValueInt(), 255).getRGB();
	
	public static void render(MatrixStack matrices, float tickDelta) {
		
		if (ModuleManager.INSTANCE.getModule(TargetHud.class).isEnabled()) TargetHudRender(matrices);
		
		if (ModuleManager.INSTANCE.getModule(HUD.class).obsidianCount.isEnabled()) GappleCount(matrices);
		if (ModuleManager.INSTANCE.getModule(HUD.class).crystalCount.isEnabled()) CrystalCount(matrices);
		if (ModuleManager.INSTANCE.getModule(HUD.class).totemCount.isEnabled()) TotemCount(matrices);
		if (ModuleManager.INSTANCE.getModule(HUD.class).gappleCount.isEnabled()) GappleCount(matrices);
		
		int index = 0;
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		renderArrayList(matrices);
		
		//DrawableHelper.drawWithShadow(matrices, null, null, index, sWidth, sHeight);
		
		
		if(ModuleManager.INSTANCE.getModule(HUD.class).markTheme.isMode("Background")) {
			DrawableHelper.fill(matrices, 7, 6, 134,  21 + (index * mc.textRenderer.fontHeight), Color.gray.getRGB());
			DrawableHelper.fill(matrices, 8, 7, 133,  20 + (index * mc.textRenderer.fontHeight), new Color(0, 0, 0, 255).getRGB());
			if(ModuleManager.INSTANCE.getModule(HUD.class).custom.isEnabled()) {
				DrawableHelper.fill(matrices, 7, 5, 134,  7 + (index * mc.textRenderer.fontHeight), color);
			}else {
				DrawableHelper.fill(matrices, 7, 5, 134,  7 + (index * mc.textRenderer.fontHeight), rainbow(400));
			}
			mc.textRenderer.draw(matrices, "Xipe.cc v1 | " + "CanonBall90", 10, 10, -1);
			mc.textRenderer.draw(matrices, "Xipe", 10, 10, Color.green.getRGB());
			
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).markTheme.isMode("Normal")) {
			nuitofont.draw(matrices, "Xipe.cc v1", 10, 10, -1, false);
		}
		
	}
	
	public static void renderArrayList(MatrixStack matrices) {
		int index = 0;
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		if (ModuleManager.INSTANCE.getModule(Helper.class).isEnabled() && mc.currentScreen == null) {
			mc.textRenderer.drawWithShadow(matrices, "Helper: Press RSHIFT to open the click GUI", 3, 30, -1);
			mc.textRenderer.drawWithShadow(matrices, "Right click the frames to open/close them", 3, 30+mc.textRenderer.fontHeight, -1);
			mc.textRenderer.drawWithShadow(matrices, "Right click a module to access the settings", 3, 30+mc.textRenderer.fontHeight+mc.textRenderer.fontHeight, -1);
			mc.textRenderer.drawWithShadow(matrices, "Left click a module to enable/disable it", 3, 30+mc.textRenderer.fontHeight+mc.textRenderer.fontHeight+mc.textRenderer.fontHeight, -1);
		}
		
		List<Mod> enabled = ModuleManager.INSTANCE.getEnabledModules();
		
		enabled.sort(Comparator.comparing(m -> (int)mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());
		
		
		for(Mod mod : enabled) {
			int fWidth = mc.textRenderer.getWidth(mod.getDisplayName());
			int fHeight = mc.textRenderer.fontHeight;
			
			int slideroption = 0;
			int offset = 9 + index*(fHeight+slideroption);
			
		if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Line")) {
			DrawableHelper.fill(matrices, sWidth, 9, sWidth - 2,  20 + (index * 13), -1);
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * 13), -1);
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Rainbow & Line")) {
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), rainbow(300));
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Normal")) {
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Rainbow")) {
			DrawableHelper.fill(matrices, sWidth - fWidth - 8, offset, sWidth - fWidth-10, slideroption + fHeight + offset, rainbow(300));
			DrawableHelper.fill(matrices, sWidth - fWidth - 8, offset, sWidth, slideroption + fHeight + offset, 0x90000000);
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
		}
			//mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
			index++;
			y += 10;
		}
	}
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 1f, 1f).getRGB();
    }
	
	static PlayerEntity target = null;
	public static void TargetHudRender(MatrixStack matrices) {
		HitResult hit = mc.crosshairTarget;
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		if (mc.player != null) {
			if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
			    if (((EntityHitResult) hit).getEntity() instanceof PlayerEntity player) {
			        target = player;
			    }
			} else if (target == null) return;
			
			int maxDistance = 32;
			if (!(target == null)) {
				if (target.isDead() || mc.player.squaredDistanceTo(target) > maxDistance) target = null;
			}

			if (target != null) {
				RenderUtil.renderRoundedQuad(matrices, new Color(41,41,41), 10+sWidth/2, 10+sHeight/2, 10+sWidth/2+70, 5+sHeight/2+40, 5, 10);
				mc.textRenderer.drawWithShadow(matrices, target.getName(), 10+sWidth/2+6, 10+sHeight/2+5, -1);
				RenderUtil.renderRoundedQuad(matrices, new Color(24,24,24), 10+sWidth/2+5, 10+sHeight/2+18, 10+sWidth/2+65, 10+sHeight/2 + 28, 3, 10);
				RenderUtil.renderRoundedQuad(matrices, new Color(172,24,150), 10+sWidth/2+5, 10+sHeight/2+18, 10+sWidth/2 + (target.getHealth()*3) + 5, 10+sHeight/2+ 28, 3, 10);
				Client.INSTANCE.logger.info(target);
			}
		}
	}
	
	public static void GappleCount(MatrixStack matrices) {
	
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		int count = mc.player.getInventory().count(Items.GOLDEN_APPLE);
		
		mc.textRenderer.drawWithShadow(matrices, "Gapples: " + count,  -485+sWidth/2+6, 20+sHeight/2+5, -1);
	}
	public static void CrystalCount(MatrixStack matrices) {
		
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		int count = mc.player.getInventory().count(Items.END_CRYSTAL);
		
		mc.textRenderer.drawWithShadow(matrices, "Crystals: " + count,  -485+sWidth/2+6, 10+sHeight/2+5, -1);
	}
	public static void TotemCount(MatrixStack matrices) {
		
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		int count = mc.player.getInventory().count(Items.TOTEM_OF_UNDYING);
		
		mc.textRenderer.drawWithShadow(matrices, "Totems: " + count,  -485+sWidth/2+6, 30+sHeight/2+5, -1);
	}
}
