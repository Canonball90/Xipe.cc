package xipe.ui.screens.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xipe.module.Mod;
import xipe.module.Mod.Category;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.ui.screens.clickgui.setting.Component;
import xipe.utils.font.FontRenderer;

public class Frame {

	public int x, y, width, height, dragX, dragY;
	public Category category;
	public int color, sgmaCol;
	public boolean modNum;
	
	
	
	public boolean dragging, extended;
	
	public List<ModuleButton> buttons;
	
	protected MinecraftClient mc = MinecraftClient.getInstance();
	
	int offset = ((height / 2) - mc.textRenderer.fontHeight / 2);
	
	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	protected static FontRenderer nuitofontL = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	protected static FontRenderer logoFont2 = new FontRenderer("logo.ttf", new Identifier("xipe", "fonts"), 20);
	protected static FontRenderer logoFont = new FontRenderer("logo.ttf", new Identifier("xipe", "fonts"), 10);
	
	public Frame(Category category, int x, int y, int width, int height) {
		this.category = category;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dragging = false;
		this.extended = true;
		
		buttons = new ArrayList<>();
		
		int offset = height;
		for(Mod mod : ModuleManager.INSTANCE.getModulesInCategory(category)) {
			buttons.add(new ModuleButton(mod, this, offset));
			offset += height;
		}
	}
	
	//If shit dont work look at this
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		final int n = this.height / 2;
		this.color = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 255).getRGB();
		this.sgmaCol = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 150).getRGB();
		this.modNum = ModuleManager.INSTANCE.getModule(Gui.class).modNum.isEnabled();
		
		 final int offsetY2 = n - 9 / 2;
		int offset = ((height / 2) - mc.textRenderer.fontHeight / 2);
	
	
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			this.OriginalThemeFrame(matrices, mouseX, mouseY, delta);
		}
		else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
			this.Window98(matrices, mouseX, mouseY, delta);
		}
		else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Sigma Old")) {
			this.SigmaBalls(matrices, mouseX, mouseY, delta);
		}
			
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
				nuitofont.draw(matrices, category.name, (float)(11 + this.x + offsetY2), this.y + offsetY2 - nuitofont.getStringHeight(this.category.name, false) / 2.0f, -1,false);
		}else {
				mc.textRenderer.drawWithShadow(matrices, category.name, x + offset,y + offset, -1);
		}
		if(extended) {
			for (ModuleButton button : buttons) {
				//animate
				button.render(matrices, mouseX, mouseY, delta);
			}
		}
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).topBar.isEnabled()) {
			DrawableHelper.fill(matrices, 0, 0, 1000, 32, Color.pink.getRGB());
			DrawableHelper.fill(matrices, 0, 0, 1000, 30, Color.darkGray.getRGB());
			nuitofont.draw(matrices, "WELCOME: " + mc.player.getName().getString(),250, 5, -1, false);
			nuitofont.draw(matrices, "XIPE.CC",5, 5, -1, false);
			nuitofont.draw(matrices, "Tps:" + mc.getServer().getTicks(),555, 5, -1, false);
			//nuitofont.draw(matrices, "more stuff on this soon!",555, 5, -1, false);
			//mc.textRenderer.drawWithShadow(matrices, "", mouseX, mouseY, offset);
		}

		//DrawableHelper.drawTexture(matrices, x + offset + 120, y + offset + 8, x + width, y + height, 10, 10, 10, 10);
	}
	
	public void mouseClicked(double mouseX, double mouseY, int button) {
		
		if(isHovered(mouseX, mouseY)) {
			if(button == 0) {
			dragging = true;
			dragX = (int) (mouseX - x);
			dragY = (int) (mouseY - y);
			}else if(button == 1) {
				extended = !extended;
			}
		}
		if(extended) {
		for (ModuleButton mb : buttons) {
			mb.mouseClicked(mouseX, mouseY, button);
		}
	}
	}
	
	public void mouseReleased(double mouseX, double mouseY, int button) {
		if(button == 0 && dragging == true) {
			dragging = false;
		}
		for(ModuleButton mb : buttons) {
			mb.mouseReleased(mouseX, mouseY, button);
		}
	}
	
	public boolean isHovered(double mouseX, double mouseY) {
		return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
	}
	
	public void updatePosition(double mouseX, double mouseY) {
		if(dragging) {
			x = (int) (mouseX - dragX);
			y = (int) (mouseY - dragY);
		}
	}

	public void updateButtons() {
		int offset = height;
		
		for(ModuleButton button : buttons) {
			button.offset = offset;
			offset += height;
			if(button.extended) {
				for(Component component : button.components) {
					if(component.setting.isVisible()) {
						offset += height;//+2
					}
					
				}
			}
		}
		
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int y) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void keyPressed(int key) {
        for (ModuleButton mb : buttons) {
            mb.keyPressed(key);
        }
    }
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
	
	/*
	 * Themes 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	public void OriginalThemeFrame(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		final int n = this.height / 2;
		final int offsetY2 = n - 9 / 2;
		if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
			Screen.fill(matrices, x -2, y, x + width + 2, y + height, color);
			DrawableHelper.fill(matrices, x, y, x + width, y + height, color);
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
		Screen.fill(matrices, x -2, y, x + width + 2, y + height, rainbow(300));
		DrawableHelper.fill(matrices, x, y, x + width, y + height, rainbow(300));
		}else {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
			//Screen.fill(matrices, x -2, y, x + width + 2, y + height, new Color(28,158,158).getRGB());
			//DrawableHelper.fill(matrices, x, y, x + width, y + height, Color.pink.getRGB());
			 	if (this.category.name == "Movement") {
			 		Screen.fill(matrices, x -1, y -1, x + width + 1, y + height + 1, new Color(61,29,158).getRGB());
					DrawableHelper.fill(matrices, x, y, x + width, y + height, new Color(32,32,32).getRGB());
			 	}
		        else if (this.category.name == "Combat") {
		        	Screen.fill(matrices, x -1, y -1, x + width + 1, y + height + 1, new Color(213,100,100).getRGB());
					DrawableHelper.fill(matrices, x, y, x + width, y + height, new Color(32,32,32).getRGB());
		        }
		        else if (this.category.name == "Render") {
		        	Screen.fill(matrices, x -1, y -1, x + width + 1, y + height + 1, new Color(158,28,158).getRGB());
					DrawableHelper.fill(matrices, x, y, x + width, y + height, new Color(32,32,32).getRGB());
		        }
		        else if (this.category.name == "Exploit") {
		        	Screen.fill(matrices, x -1, y -1, x + width + 1, y + height + 1, new Color(43,169,102).getRGB());
					DrawableHelper.fill(matrices, x, y, x + width, y + height, new Color(32,32,32).getRGB());
		        }
		        else if (this.category.name == "World") {
		        	Screen.fill(matrices, x -1, y -1, x + width + 1, y + height + 1, Color.orange.getRGB());
					DrawableHelper.fill(matrices, x, y, x + width, y + height, new Color(32,32,32).getRGB());
		        }
		        else if (this.category.name == "Hud") {
		        	Screen.fill(matrices, x -1, y -1, x + width + 1, y + height + 1, new Color(255,35,35).getRGB());
					DrawableHelper.fill(matrices, x, y, x + width, y + height, new Color(32,32,32).getRGB());
		        }
			}else {
				DrawableHelper.fill(matrices, x, y, x + width, y + height, new Color(32,32,32).getRGB());
			}
		}
		
		if(modNum) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
				nuitofont.draw(matrices, "["+ ModuleManager.INSTANCE.getAmountPerCat(category) + "]", x + offset + 110,y + offset + 4, -1, false);
			}else {
			mc.textRenderer.drawWithShadow(matrices, "["+ ModuleManager.INSTANCE.getAmountPerCat(category) + "]", x + offset + 120,y + offset + 10, -1);
			}
		}else {
			 if (category.name == "Movement") {
		            logoFont2.draw(matrices, "C", x + offsetY2-4, y + offsetY2 - logoFont2.getStringHeight(category.name, false)/2+2, -1, false);
		        } else if (category.name == "Combat") {
		            logoFont2.draw(matrices, "F", x + offsetY2-4, y + offsetY2 - logoFont2.getStringHeight(category.name, false)/2+2, -1, false);
		        } else if (category.name == "Render") {
		            logoFont2.draw(matrices, "G", x + offsetY2-4, y + offsetY2 - logoFont2.getStringHeight(category.name, false)/2+2, -1, false);
		        } else if (category.name == "Exploit") {
		            logoFont2.draw(matrices, "E", x + offsetY2-4, y + offsetY2 - logoFont2.getStringHeight(category.name, false)/2+2, -1, false);
		        } else if (category.name == "World") {
		            logoFont2.draw(matrices, "D", x + offsetY2-4, y + offsetY2 - logoFont2.getStringHeight(category.name, false)/2+2, -1, false);
		        } else if (category.name == "Hud") {
		            logoFont2.draw(matrices, "G", x + offsetY2-4, y + offsetY2 - logoFont2.getStringHeight(category.name, false)/2+2, -1, false);
		        }
			 logoFont.draw(matrices, this.extended ? "A" : "B", (float)(this.x + this.width - offsetY2 - 4 - this.mc.textRenderer.getWidth("--")), (float)(this.y + offsetY2), -1, false);
		}
		
	}
	
	public void Window98(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Screen.fill(matrices, x - 4, y +1, x + width + 4, y + height - 2, Color.blue.getRGB());
		DrawableHelper.fill(matrices, x, y +1, x + width, y + height, Color.blue.getRGB());
		
		if(modNum) {
			mc.textRenderer.drawWithShadow(matrices, "["+ ModuleManager.INSTANCE.getAmountPerCat(category) + "]", x + offset + 120,y + offset + 8, -1);
		}else {
			DrawableHelper.drawTexture(matrices, x + offset + 120, y + offset + 8, x + width, y + height, 10, 10, 10, 10);
			//this.FrameMinimizeButton(matrices, mouseX, mouseY, delta);
		}
	}
	
	public void FrameMinimizeButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		//Everything else
		DrawableHelper.fill(matrices, x + offset + 122, y + offset + 6, x + width - 2, y + height - 5, Color.lightGray.getRGB());
		DrawableHelper.fill(matrices, x + offset + 123, y + offset + 7, x + width - 2, y + height - 5, Color.darkGray.getRGB());
		DrawableHelper.fill(matrices, x + offset + 123, y + offset + 7, x + width - 3, y + height - 6, Color.gray.getRGB());
		
		//Line button thing
		DrawableHelper.fill(matrices, x + offset + 127, y + offset + 12, x + width - 8, y + height - 10, Color.darkGray.getRGB());
	}
	
	public void SigmaBalls(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		Screen.fill(matrices, x-2, y-2, x + width + 2, y + height + 2, new Color(255,255,255,130).getRGB());
		Screen.fill(matrices, x, y, x + width, y + height - 2, new Color(25,25,25,180).getRGB());
		Screen.fill(matrices, x + offset + 124, y + offset +17, x + width - 9, y + height - 18, new Color(25,25,25,180).getRGB());
		Screen.fill(matrices, x + offset + 121, y + offset +17, x + width - 35, y + height - 18, new Color(25,25,25,180).getRGB());
	}
	}

