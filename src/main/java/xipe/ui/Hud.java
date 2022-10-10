package xipe.ui;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import xipe.Client;
import xipe.module.Mod;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.module.modules.hud.Coords;
import xipe.module.modules.hud.HUD;
import xipe.module.modules.hud.Helper;
import xipe.module.modules.hud.Notifications;
import xipe.module.modules.hud.OnlineTime;
import xipe.module.modules.hud.PotionHud;
import xipe.module.modules.hud.SessionInfo;
import xipe.module.modules.hud.TargetHud;
import xipe.ui.screens.utils.Particle;
import xipe.utils.font.FontRenderer;
import xipe.utils.player.Notification;
import xipe.utils.player.NotificationUtil;
import xipe.utils.player.PlayerUtil;
import xipe.utils.render.ColorUtils;
import xipe.utils.render.RenderUtils;
import xipe.utils.world.EntityUtils;
import xipe.utils.world.TimerUtil;

public class Hud {

	private static MinecraftClient mc = MinecraftClient.getInstance();
	
	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	
	static int y = 10;
	static int color = new Color(ModuleManager.INSTANCE.getModule(HUD.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(HUD.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(HUD.class).blue.getValueInt(), 255).getRGB();
	
	 private static long timeOnline;
	 
	 static ArrayList<Particle> particles = new ArrayList();
		
		static TimerUtil pTimer = new TimerUtil();
		
		
	 public void reset() {
	        timeOnline = 0;
	    }

	    public void start() {
	        Client.EventBus.register(this);
	        timeOnline = System.currentTimeMillis();
	    }
	
	
	 public static long getTimeOnline() {
	        return (System.currentTimeMillis() - timeOnline) / 1000;
	    }

	    public static String convertTime(long time) {
	        return String.format("%s hours, %s minutes, %s seconds", time / 3600, (time - (time / 3600 * 3600) - (time % 60)) / 60, time % 60);
	    }
	
	
	public static void render(MatrixStack matrices, float tickDelta) {
		
		if(!(mc.currentScreen == null)) {
			DrawableHelper.fill(matrices, -1000, -1000, 1000, 1000, new Color(255,255,255,55).getRGB());
		 if (pTimer.hasTimeElapsed(5L, true)) {
	            particles.add(new Particle());
	        }
		 for(Particle particle : particles) {
	            particle.render(matrices);
	        }
		}
		
		if (ModuleManager.INSTANCE.getModule(TargetHud.class).isEnabled()) TargetHudRender(matrices);
		
		if (ModuleManager.INSTANCE.getModule(HUD.class).obsidianCount.isEnabled()) GappleCount(matrices);
		if (ModuleManager.INSTANCE.getModule(HUD.class).crystalCount.isEnabled()) CrystalCount(matrices);
		if (ModuleManager.INSTANCE.getModule(HUD.class).totemCount.isEnabled()) TotemCount(matrices);
		if (ModuleManager.INSTANCE.getModule(HUD.class).gappleCount.isEnabled()) GappleCount(matrices);
		if (ModuleManager.INSTANCE.getModule(xipe.module.modules.Render.LSD.class).isEnabled()) LSD(matrices, 2f);
		if (ModuleManager.INSTANCE.getModule(xipe.module.modules.Render.Ambience.class).isEnabled()) Ambience(matrices);
		if (ModuleManager.INSTANCE.getModule(xipe.module.modules.hud.Indicators.class).isEnabled()) Indicators(matrices);
		if (ModuleManager.INSTANCE.getModule(xipe.module.modules.hud.Notifications.class).isEnabled()) Notifs(matrices);
		if (ModuleManager.INSTANCE.getModule(Coords.class).isEnabled()) XYZ(matrices);
		if (ModuleManager.INSTANCE.getModule(PotionHud.class).isEnabled()) potionEffect(matrices);
		
		int index = 0;
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		if (ModuleManager.INSTANCE.getModule(SessionInfo.class).isEnabled()) seshInfo(matrices);
		if (ModuleManager.INSTANCE.getModule(OnlineTime.class).isEnabled()) onTime(matrices);
		
		renderArrayList(matrices);
		
		final Formatting gray = Formatting.GRAY;
		final Formatting r = Formatting.RESET;
		
		if(ModuleManager.INSTANCE.getModule(HUD.class).markTheme.isMode("Background")) {
			DrawableHelper.fill(matrices, 7, 6, 134,  21 + (index * mc.textRenderer.fontHeight), Color.gray.getRGB());
			DrawableHelper.fill(matrices, 8, 7, 133,  20 + (index * mc.textRenderer.fontHeight), new Color(0, 0, 0, 255).getRGB());
			if(ModuleManager.INSTANCE.getModule(HUD.class).custom.isEnabled()) {
				DrawableHelper.fill(matrices, 7, 5, 134,  7 + (index * mc.textRenderer.fontHeight), color);
			}else {
				DrawableHelper.fill(matrices, 7, 5, 134,  7 + (index * mc.textRenderer.fontHeight), rainbow(400));
			}
			mc.textRenderer.draw(matrices, "Xipe.cc v1 | " + mc.player.getName().getString(), 10, 10, -1);
			mc.textRenderer.draw(matrices, "Xipe", 10, 10, Color.green.getRGB());
			
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).markTheme.isMode("Normal")) {
			TimerUtil afterTimer = new TimerUtil();
			nuitofont.draw(matrices,ModuleManager.INSTANCE.getModule(HUD.class).markName.getMode() + " "+ gray + "v1", 10 - (afterTimer.convertMillisToSec(afterTimer.getSec())), 10, -1, false);
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
			int offset = 0 + index* (fHeight+slideroption);
			int offset2 = 0;
			
			
		if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Line")) {
			DrawableHelper.fill(matrices, sWidth,  offset+ 3, sWidth - 2, slideroption + fHeight + offset, -1);
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Rainbow & Line")) {
			DrawableHelper.fill(matrices, sWidth - fWidth - 5, offset, sWidth, slideroption + fHeight + offset, 0x90000000);//0x90000000
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 2) - mc.textRenderer.getWidth(mod.getDisplayName()), 1+(index * mc.textRenderer.fontHeight), -1);
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Normal")) {
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1);
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("Rainbow")) {
			DrawableHelper.fill(matrices, sWidth - fWidth - 9, offset, sWidth - fWidth +76, slideroption + fHeight + offset+1, rainbow(300));
			DrawableHelper.fill(matrices, sWidth - fWidth - 8, offset, sWidth, slideroption + fHeight + offset, new Color(15,15,15,250).getRGB());//0x90000000
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 1+(index * mc.textRenderer.fontHeight), -1);
		}else if(ModuleManager.INSTANCE.getModule(HUD.class).theme.isMode("B&W")) {
			DrawableHelper.fill(matrices, sWidth - fWidth - 9, offset, sWidth - fWidth +76, slideroption + fHeight + offset+1, new Color(255,255,255).getRGB());
			DrawableHelper.fill(matrices, sWidth - fWidth - 8, offset, sWidth, slideroption + fHeight + offset, new Color(0,0,0).getRGB());//0x90000000
			mc.textRenderer.draw(matrices, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 1+(index * mc.textRenderer.fontHeight), -1);
		}
	
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
		
		int red = ModuleManager.INSTANCE.getModule(TargetHud.class).color.getValueInt();
		int green = ModuleManager.INSTANCE.getModule(TargetHud.class).color1.getValueInt();
		int blue = ModuleManager.INSTANCE.getModule(TargetHud.class).color2.getValueInt();
		
		if (mc.player != null) {
			if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
			    if (((EntityHitResult) hit).getEntity() instanceof PlayerEntity player) {
			        target = player;
			    }
			} else if (target == null) return;
			
			int maxDistance = ModuleManager.INSTANCE.getModule(TargetHud.class).range.getValueInt();
			if (!(target == null)) {
				if (target.isDead() || mc.player.squaredDistanceTo(target) > maxDistance) target = null;
			}

			if (target != null) {
				RenderUtils.renderRoundedQuad(matrices, new Color(red,green,blue, 200), 10+sWidth/2, 10+sHeight/2, 10+sWidth/2+90, 5+sHeight/2+55, 5, 10);
				mc.textRenderer.drawWithShadow(matrices, target.getName(), 10+sWidth/2+6, 10+sHeight/2+5, -1);
				RenderUtils.renderRoundedQuad(matrices, new Color(24,24,24), 10+sWidth/2+5, 10+sHeight/2+18, 10+sWidth/2+85, 10+sHeight/2 + 28, 3, 10);
				RenderUtils.renderRoundedQuad(matrices, target.getHealth() < 10 ? new Color(244,24,24) : new Color(24,244,24), 10+sWidth/2+5, 10+sHeight/2+18, 10+sWidth/2 + (target.getHealth()*4) + 5, 10+sHeight/2+ 28, 3, 10);
				mc.textRenderer.drawWithShadow(matrices, "Distance: ", 10+sWidth/2+6, 10+sHeight/2+30, -1);
				RenderUtils.renderRoundedQuad(matrices, new Color(24,24,24), 10+sWidth/2+5, 10+sHeight/2+40, 10+sWidth/2+75, 10+sHeight/2 + 45, 3, 10);
				RenderUtils.renderRoundedQuad(matrices, new Color(24,244,24), 10+sWidth/2+5, 10+sHeight/2+40, 10+sWidth/2 + (mc.player.distanceTo(target)*10) + 5, 10+sHeight/2+ 45, 3, 10);
				//Client.INSTANCE.logger.info(target);
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
		
		//RenderUtils.drawItem(Items.END_CRYSTAL.getDefaultStack(),  -485+sWidth/2+6, 10+sHeight/2+5);
		mc.textRenderer.drawWithShadow(matrices, "Crystals: " + count,  -485+sWidth/2+6, 10+sHeight/2+5, -1);
	}
	public static void TotemCount(MatrixStack matrices) {
		
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		int count = mc.player.getInventory().count(Items.TOTEM_OF_UNDYING);
		
		mc.textRenderer.drawWithShadow(matrices, "Totems: " + count,  -485+sWidth/2+6, 30+sHeight/2+5, -1);
	}
	
	public static void seshInfo(MatrixStack matrices) {
		
		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();
		
		int x1 = ModuleManager.INSTANCE.getModule(SessionInfo.class).x.getValueInt();
		int y1 = ModuleManager.INSTANCE.getModule(SessionInfo.class).y.getValueInt();
		
		DrawableHelper.fill(matrices, -x1+sWidth/2, y1 + 5+sHeight/2, -x1+sWidth/2+140, y1+sHeight/2+60, new Color(0,0,0,150).getRGB());
		DrawableHelper.fill(matrices, -x1+sWidth/2, y1 + 5+sHeight/2, -x1+sWidth/2+140, y1+sHeight/2+7, rainbow(300));
		
		nuitofont.draw(matrices, "SESSION INFO", -x1 + 28 +sWidth/2, y1 + 5 +sHeight/2, -1, false);
		nuitofont.draw(matrices, " Name: " + mc.player.getName().getString(), -x1+sWidth/2, y1 + 15 +sHeight/2, -1, false);
		nuitofont.draw(matrices, " FPS: " + mc.fpsDebugString.split(" ")[0], -x1+sWidth/2, y1 + 35 +sHeight/2, -1, false);
		if(mc.world.getServer() == null) {
			nuitofont.draw(matrices, " Server: " + mc.world.getServer(), -x1+sWidth/2, y1 + 25 +sHeight/2, -1, false);
		}else {
		nuitofont.draw(matrices, " Server: " + mc.world.getServer().getServerIp(), -x1+sWidth/2, y1 + 25 +sHeight/2, -1, false);
		}
	}
	
	public static void onTime(MatrixStack matrices) {
	
		int x = ModuleManager.INSTANCE.getModule(OnlineTime.class).x.getValueInt();
		int y = ModuleManager.INSTANCE.getModule(OnlineTime.class).y.getValueInt();
		
	        if(mc.player != null && mc.world != null) {
	        	String text = "Online for: " + convertTime(getTimeOnline());
	            nuitofont.draw(matrices, text, x + 3, y + 2, -1, false);
	        }
	}
	
	 private static double animation(double current, double end, double factor, double start) {
	        double progress = (end - current) * factor;

	        if (progress > 0) {
	            progress = Math.max(start, progress);
	            progress = Math.min(end - current, progress);
	        } else if (progress < 0) {
	            progress = Math.min(-start, progress);
	            progress = Math.max(end - current, progress);
	        }

	        return current + progress;
	    }
	 
	    public static void LSD(final MatrixStack ms, final float td) {
	        final Color c = Color.getHSBColor((float)(System.currentTimeMillis() % 10000L / 10000.0), 1.0f, 1.0f);
	        final int w = mc.getWindow().getScaledWidth();
	        final int h = mc.getWindow().getScaledHeight();
	        DrawableHelper.fill(ms, 0, 0, w, h, new Color(c.getRed(), c.getGreen(), c.getBlue(), 50).getRGB());
	    }
	    
	    public static void Ambience(final MatrixStack ms) {
	    	
	    	int r = ModuleManager.INSTANCE.getModule(xipe.module.modules.Render.Ambience.class).r.getValueInt();
	    	int b = ModuleManager.INSTANCE.getModule(xipe.module.modules.Render.Ambience.class).b.getValueInt();
	    	int g = ModuleManager.INSTANCE.getModule(xipe.module.modules.Render.Ambience.class).g.getValueInt();
	    	
	        final Color c = new Color(r,g,b);
	        final int w = mc.getWindow().getScaledWidth();
	        final int h = mc.getWindow().getScaledHeight();
	        DrawableHelper.fill(ms, 0, 0, w, h, new Color(r, g, b, 50).getRGB());
	    }
	    
	    public static void Indicators(MatrixStack matrices) {
			
			int sWidth = mc.getWindow().getScaledWidth();
			int sHeight = mc.getWindow().getScaledHeight();
			
			int x1 = ModuleManager.INSTANCE.getModule(xipe.module.modules.hud.Indicators.class).x.getValueInt();
			int y1 = ModuleManager.INSTANCE.getModule(xipe.module.modules.hud.Indicators .class).y.getValueInt();
			
			DrawableHelper.fill(matrices, -x1+sWidth/5, y1 + 5+sHeight/5, -x1+sWidth/5+140, y1+sHeight/5+60, new Color(0,0,0,150).getRGB());
			DrawableHelper.fill(matrices, -x1+sWidth/5, y1 + 5+sHeight/5, -x1+sWidth/5+140, y1+sHeight/5+7, rainbow(300));
			
			nuitofont.draw(matrices, "Speed", -x1 + 10 +sWidth/5, y1 + 5 +sHeight/5, -1, false);
	
			RenderUtils.renderRoundedQuad(matrices, new Color(24,244,24), -x1+sWidth/5+5, y1+sHeight/5+22, -x1+sWidth/5 + (EntityUtils.getSpeed() *200) + 10, y1+sHeight/5+ 27, 3, 10);

			nuitofont.draw(matrices, "Health", -x1 + 10 +sWidth/5, y1 + 25 +sHeight/5, -1, false);
			
			RenderUtils.renderRoundedQuad(matrices, mc.player.getHealth() < 10 ? new Color(244,24,24) : new Color(24,244,24), -x1+sWidth/5+5, y1+sHeight/5+42, -x1+sWidth/5 + (mc.player.getHealth()*6) + 5, y1+sHeight/5+ 47, 3, 10);
			}
	    
	    public static void Notifs(MatrixStack matrices) {
	    	
			int index = 0;
			int sWidth = mc.getWindow().getScaledWidth();
			int sHeight = mc.getWindow().getScaledHeight();
	    	
	    	
			int fHeight = mc.textRenderer.fontHeight;
			
			int slideroption = 0;
			int offset = 7 + index* (fHeight+slideroption);
			int offset2 = 9;

	        ArrayList<Notification> notifications;
	        NotificationUtil.update();
	        
	        TimerUtil afterTimer = new TimerUtil();

	        notifications = NotificationUtil.get_notifications();

	        int renderY = ModuleManager.INSTANCE.getModule(Notifications.class).y.getValueInt();
	        for (Notification n : notifications) {
	        	
	        	int fWidth = mc.textRenderer.getWidth(n.getMessage());
	        	
	            int messageWidth = (int) (nuitofont.getStringWidth(n.getMessage(), false) + 20);
	            int nWidth = Math.max(messageWidth, 125);
	            if(ModuleManager.INSTANCE.getModule(Notifications.class).background.isEnabled()) {
	            DrawableHelper.fill(matrices, -1025 + sWidth, renderY, (int) (sWidth - 850 + nuitofont.getStringWidth(n.getMessage(), false)) - 95 , renderY+40, new Color(0,0,0,150).getRGB());
	            }
	            //DrawableHelper.fill(matrices, -1025 + sWidth, renderY + 38, (int) ((int) (sWidth - 850 + nuitofont.getStringWidth(n.getMessage(), false)) - 95 + (System.currentTimeMillis() - n.getTimeCreated() - 5000)), renderY+40, new Color(150,150,150,255).getRGB());
	            DrawableHelper.fill(matrices,-960 + sWidth, renderY,  (int) (sWidth - 850 - 107), renderY + 40, new Color(n.getR(), n.getG(), n.getB()).getRGB());
	            nuitofont.draw(matrices, n.getMessage(), -955 + sWidth, renderY + 10, -1, false);
	            if(ModuleManager.INSTANCE.getModule(Notifications.class).animationMode.isMode("X")) {
	            	DrawableHelper.fill(matrices, (int) (-1025 + sWidth + (afterTimer.lastMS - n.getTimeCreated() - 200)), renderY, (int) ((int) (sWidth - 850 + nuitofont.getStringWidth(n.getMessage(), false)) - 95 + (afterTimer.lastMS - n.getTimeCreated() - 200)), renderY+40, new Color(n.getR(), n.getG(), n.getB(), 130).getRGB());
	            }
	            if(ModuleManager.INSTANCE.getModule(Notifications.class).animationMode.isMode("Y")) {
	            	DrawableHelper.fill(matrices, -1025 + sWidth, (int) (renderY +  (afterTimer.lastMS - n.getTimeCreated())), (int) ((int) (sWidth - 850 + nuitofont.getStringWidth(n.getMessage(), false)) - 95), (int) (renderY+40 + (afterTimer.lastMS - n.getTimeCreated())), new Color(n.getR(), n.getG(), n.getB(), 130).getRGB());
	            }
	            if(ModuleManager.INSTANCE.getModule(Notifications.class).animationMode.isMode("End Slide")) {
	            	DrawableHelper.fill(matrices, -1025 + sWidth, renderY + 35, (int) ((int) (sWidth - 850 + nuitofont.getStringWidth(n.getMessage(), false)) - 95 - (afterTimer.lastMS - n.getTimeCreated()) / animation(0, renderY+260 / nuitofont.getStringWidth(n.getMessage(), false) ,0.3,0)), renderY+40, new Color(n.getR(), n.getG(), n.getB(), 190).getRGB());
	            }
	            renderY += 42;
	        }
	    }
	    
	    public static void XYZ(MatrixStack mats) {
	    	
	    	final Formatting Gray = Formatting.GRAY;
	    	
	    	String xyzString = "XYZ " + Gray + round(mc.player.getX(), 1) + ", " + round(mc.player.getY(), 1) + ", " + round(mc.player.getZ(), 1);
			
	    	int getX = ModuleManager.INSTANCE.getModule(Coords.class).x.getValueInt();
	    	int getY = ModuleManager.INSTANCE.getModule(Coords.class).y.getValueInt();
	    	
	    	Color c = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(),ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(),ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt());

			nuitofont.drawWithShadow(mats,xyzString,getX,getY, c.getRGB(), false);
	    }
	    
	    public static double round(double num, double increment) {
	        if (increment < 0) {
	            throw new IllegalArgumentException();
	        }
	        BigDecimal bd = new BigDecimal(num);
	        bd = bd.setScale((int) increment, RoundingMode.HALF_UP);
	        return bd.doubleValue();
	    }
	    
	    public static void potionEffect(MatrixStack matrices) {
	    	
	    	int sWidth = mc.getWindow().getScaledWidth();
			int sHeight = mc.getWindow().getScaledHeight();
			
	    	int getX = ModuleManager.INSTANCE.getModule(PotionHud.class).x.getValueInt();
	    	int getY = ModuleManager.INSTANCE.getModule(PotionHud.class).y.getValueInt();
	    	
	    	DrawableHelper.fill(matrices, -getX+sWidth/5, getY + 5+sHeight/5 - 30, -getX+sWidth/5+170, getY+sHeight/5+60, new Color(0,0,0,150).getRGB());
			DrawableHelper.fill(matrices, -getX+sWidth/5, getY + 5+sHeight/5 - 30, -getX+sWidth/5+170, getY+sHeight/5-27, rainbow(300));
			DrawableHelper.fill(matrices, -getX+sWidth/5, getY + 58+sHeight/5, -getX+sWidth/5+170, getY+sHeight/5+60, rainbow(300));
			DrawableHelper.fill(matrices, -getX+sWidth/5 + 168, getY + 58+sHeight/5, -getX+sWidth/5+170, getY+sHeight/5-27, rainbow(300));
			DrawableHelper.fill(matrices, -getX+sWidth/5, getY + 58+sHeight/5, -getX+sWidth/5+2, getY+sHeight/5-27, rainbow(300));
			if(mc.player.getStatusEffect(StatusEffects.ABSORPTION) == null) {
				nuitofont.draw(matrices, " Absorption: " + "0", -getX+sWidth/5 + 3, 390, -1, false);
			}else {
				nuitofont.draw(matrices, " Absorption: " + mc.player.getStatusEffect(StatusEffects.ABSORPTION).getDuration(), -getX+sWidth/5 + 3, 390, -1, false);
				DrawableHelper.fill(matrices,  -getX+sWidth/5+5, getY+sHeight/5 - 10, -getX+sWidth/5 + (mc.player.getStatusEffect(StatusEffects.ABSORPTION).getDuration()/16) + 10, getY+sHeight/5 - 8, new Color(24,244,24).getRGB());
			}if(mc.player.getStatusEffect(StatusEffects.RESISTANCE) == null) {
				nuitofont.draw(matrices, " Resistance: " + "0", -getX+sWidth/5 + 3, 407, -1, false);
			}else {
				nuitofont.draw(matrices, " Resistance: " + mc.player.getStatusEffect(StatusEffects.RESISTANCE).getDuration(), -getX+sWidth/5 + 3, 407, -1, false);
				DrawableHelper.fill(matrices,  -getX+sWidth/5+5, getY+sHeight/5 + 5, -getX+sWidth/5 + (mc.player.getStatusEffect(StatusEffects.RESISTANCE).getDuration()/40) + 10, getY+sHeight/5 + 7, new Color(24,244,24).getRGB());
			}if(mc.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE) == null) {
				nuitofont.draw(matrices, " Fire-Resistance: " + "0", -getX+sWidth/5 + 3, 422, -1, false);
			}else {
				nuitofont.draw(matrices, " Fire-Resistance: " + mc.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE).getDuration(), -getX+sWidth/5 + 3, 422, -1, false);
				DrawableHelper.fill(matrices,  -getX+sWidth/5+5, getY+sHeight/5 + 21, -getX+sWidth/5 + (mc.player.getStatusEffect(StatusEffects.FIRE_RESISTANCE).getDuration()/40) + 10, getY+sHeight/5 + 23, new Color(24,244,24).getRGB());
			}if(mc.player.getStatusEffect(StatusEffects.REGENERATION) == null) {
				nuitofont.draw(matrices, " Regeneration: " + "0", -getX+sWidth/5 + 3, 438, -1, false);
			}else {
				nuitofont.draw(matrices, " Regeneration: " + mc.player.getStatusEffect(StatusEffects.REGENERATION).getDuration(), -getX+sWidth/5 + 3, 438, -1, false);
				DrawableHelper.fill(matrices,  -getX+sWidth/5+5, getY+sHeight/5 + 37, (int) (-getX+sWidth/5 + (mc.player.getStatusEffect(StatusEffects.REGENERATION).getDuration()/2.5) + 10), getY+sHeight/5 + 39, new Color(24,244,24).getRGB());
			}if(mc.player.getStatusEffect(StatusEffects.STRENGTH) == null) {
				nuitofont.draw(matrices, " Strength: " + "0", -getX+sWidth/5 + 3, 453, -1, false);
			}else {
				nuitofont.draw(matrices, " Strength: " + mc.player.getStatusEffect(StatusEffects.STRENGTH).getDuration(), -getX+sWidth/5 + 3, 448, -1, false);
				DrawableHelper.fill(matrices,  -getX+sWidth/5+5, getY+sHeight/5 + 37, (int) (-getX+sWidth/5 + (mc.player.getStatusEffect(StatusEffects.STRENGTH).getDuration()/2.5) + 10), getY+sHeight/5 + 39, new Color(24,244,24).getRGB());
			}
			}
	    }
	    


