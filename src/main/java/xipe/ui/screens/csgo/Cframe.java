package xipe.ui.screens.csgo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

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

public class Cframe {

	public int x, y, width, height, dragX, dragY;
	public Category category;
	public int color, sgmaCol;
	public boolean modNum;
	
	
	
	public boolean dragging, extended;
	
	public List<ModuleButton> buttons;
	
	protected MinecraftClient mc = MinecraftClient.getInstance();
	
	int offset = ((height / 2) - mc.textRenderer.fontHeight / 2);
	
	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	protected static FontRenderer yahoo = new FontRenderer("Yahoo.ttf", new Identifier("xipe", "fonts"), 25);
	protected static FontRenderer logoFont2 = new FontRenderer("logo.ttf", new Identifier("xipe", "fonts"), 20);
	protected static FontRenderer logoFont = new FontRenderer("logo.ttf", new Identifier("xipe", "fonts"), 10);
	
	public Cframe(Category category, int x, int y, int width, int height) {
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
			//buttons.add(new ModuleButton(mod, this, offset));
			offset += height;
		}
	}
	
	//If shit dont work look at this
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		final int n = this.height / 2;
		
		final int offsetY2 = n - 9 / 2;
		int offset = ((height / 2) - mc.textRenderer.fontHeight / 2);
	
		if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
				nuitofont.draw(matrices, category.name, (float)(11 + this.x + offsetY2), this.y + offsetY2 - nuitofont.getStringHeight(this.category.name, false) / 2.0f, -1,false);
		}else {
				mc.textRenderer.drawWithShadow(matrices, category.name, x + offset,y + offset, -1);
		/*
		if(extended) {
			for (ModuleButton button : buttons) {
				button.render(matrices, mouseX, mouseY, delta);
			}
		}
		*/
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
			//mb.mouseClicked(mouseX, mouseY, button);
		}
	}
	}
	
	public void mouseReleased(double mouseX, double mouseY, int button) {
		if(button == 0 && dragging == true) {
			dragging = false;
		}
		for(ModuleButton mb : buttons) {
			//mb.mouseReleased(mouseX, mouseY, button);
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
			//button.offset = offset;
			offset += height;
			/*
			if(button.extended) {
				for(Component component : button.components) {
					if(component.setting.isVisible()) {
						offset += height;//+2
					}
					
				}
			}
			*/
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
           // mb.keyPressed(key);
        }
    }
}