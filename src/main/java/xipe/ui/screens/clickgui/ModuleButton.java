package xipe.ui.screens.clickgui;

import java.awt.Button;
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
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.KeybindSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.NumberSetting;
import xipe.module.settings.Setting;
import xipe.ui.screens.clickgui.setting.CheckBox;
import xipe.ui.screens.clickgui.setting.Component;
import xipe.ui.screens.clickgui.setting.KeyBind;
import xipe.ui.screens.clickgui.setting.ModeBox;
import xipe.ui.screens.clickgui.setting.Slider;
import xipe.utils.font.FontRenderer;

public class ModuleButton {

	public Mod module;
	public Frame parent;
	public int offset;
	public List<Component> components;
	public boolean extended;
	public int color;
	public int color2;
	public boolean dragging;


	//protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	
	public ModuleButton(Mod module, Frame parent, int offset){
		this.module = module;
		this.parent = parent;
		this.offset = offset;
		this.extended = false;
		this.components = new ArrayList<>();
		this.dragging = false;
		
		int setOffset = parent.height;
		for (Setting setting : module.getSettings()) {
			if(setting instanceof BooleanSetting) {
				components.add(new CheckBox(setting, this, setOffset));
			}else if(setting instanceof ModeSetting) {
				components.add(new ModeBox(setting, this, setOffset));
			}else if(setting instanceof NumberSetting) {
				components.add(new Slider(setting, this, setOffset));
			}else if(setting instanceof KeybindSetting) {
				components.add(new KeyBind(setting, this, setOffset));
			}
			setOffset += parent.height;
		}
	}
	
	@SuppressWarnings("resource")
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		int offsetY = ((parent.height / 2) - parent.mc.textRenderer.fontHeight / 2);
		
		this.color = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 255).getRGB();
		this.color2 = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 150).getRGB();
		int textOffset = ((parent.height / 2) - parent.mc.textRenderer.fontHeight / 2);
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			this.GUI(matrices, mouseX, mouseY, delta);
		}
		else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
			DrawableHelper.fill(matrices, parent.x - 2, parent.y + offset - 2, parent.x + parent.width + 2, parent.y + offset + parent.height + 2, Color.gray.getRGB());
			DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, Color.lightGray.getRGB());
			DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, module.isEnabled() ? new Color(0,0,0,160).getRGB() + Color.darkGray.getRGB() : Color.lightGray.getRGB());
			if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
				parent.nuitofont.draw(matrices, module.getName(), parent.x-1 + offsetY, parent.y-6 + offset + offsetY, -1, false);
			}else {
			parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x + textOffset, parent.y + offset + textOffset, module.isEnabled() ? -1 : -1);
			}
		}
		else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Sigma Old")) {
			Screen.fill(matrices, parent.x - 2, parent.y + offset+2, parent.x + parent.width + 2, parent.y + offset + parent.height + 2, new Color(255,255,255,130).getRGB());
			
			DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, module.isEnabled() ? new Color(0,0,0,190).getRGB() : new Color(0,0,0,180).getRGB());
			//DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, module.isEnabled() ? new Color(0,0,0,160).getRGB() + Color.darkGray.getRGB() : Color.lightGray.getRGB());
			if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
				parent.nuitofont.draw(matrices, module.getName(), parent.x-1 + offsetY, parent.y-6 + offset + offsetY, -1, false);
			}else {
			parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x + textOffset, parent.y + offset + textOffset, module.isEnabled() ? -1 : -1);
			}
		}

		//parent.mc.textRenderer.drawWithShadow(matrices, this.extended ? "-" : "+", parent.x + textOffset + 115, parent.y + offset + textOffset, -1);
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Sigma Old")) {
			
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).ke.isMode("+")) {
			parent.nuitofont.draw(matrices, this.extended ? "-" : "+", parent.x + textOffset + 115, parent.y + offset + textOffset - 6, -1, false);
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).ke.isMode("...")) {
			parent.nuitofont.draw(matrices, this.extended ? "..." : "...", parent.x + textOffset + 115, parent.y + offset + textOffset - 6, -1, false);
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).ke.isMode("keybind")) {
			parent.nuitofont.draw(matrices, "[" + module.getKey() + "]", parent.x + textOffset + 110, parent.y + offset + textOffset - 6, -1, false);
		}
		//parent.nuitofont.draw(matrices, this.extended ? "..." : "...", parent.x + textOffset + 115, parent.y + offset + textOffset - 6, -1, false);
		
		if(extended) {
		for(Component component : components) {
			component.render(matrices, mouseX, mouseY, delta);
		
		}
		}
		
		int sWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
		int sHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

		if(isHovered(mouseX, mouseY)) {
			
			DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0, 0, 0, 160).getRGB());
			
			//Description Box shit
				if(ModuleManager.INSTANCE.getModule(Gui.class).description.isMode("Mode1")) {
					DrawableHelper.fill(matrices, parent.mc.textRenderer.getWidth(module.getDescription()) + 289, 4, 275, 20, Color.darkGray.getRGB());
					if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
						parent.nuitofont.draw(matrices, module.getDescription(), 278, 2, -1, false);
					}else {
					MinecraftClient.getInstance().textRenderer.draw(matrices, module.getDescription(),280, 8, -1);
					}
				}
				else if(ModuleManager.INSTANCE.getModule(Gui.class).description.isMode("Mode2")) {
				parent.nuitofont.draw(matrices, module.getDescription(), parent.x, parent.y - 20, -1, false);
				}
				else if(ModuleManager.INSTANCE.getModule(Gui.class).description.isMode("Mode3")) {
					 DrawableHelper.fill(matrices, 0, sHeight, (int)parent.nuitofont.getStringWidth(this.module.getDescription(), false) + 5, sHeight - (int)parent.nuitofont.getStringHeight("|", false) - 3, 1879048192);
					 parent.nuitofont.draw(matrices, this.module.getDescription(), 2.0f, sHeight - parent.nuitofont.getStringHeight("|", false) - 5.0f, -1, false);
			        
				}
			
		}
		
		
	}
	
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(isHovered(mouseX, mouseY)) {
			if(button == 0) {
				module.toggle();
			} else if (button == 1){
				extended = !extended;
				parent.updateButtons();
			}
		}
		
		if (extended) {
            for (Component component : components) {
                component.mouseClicked(mouseX, mouseY, button);
            }
        }
	}
	
	public void keyPressed(int key) {
        for (Component component : components) {
            component.keyPressed(key);
        }
    }
	
	public void mouseReleased(double mouseX, double mouseY, int button) {
		for(Component component : components) {
			component.mouseReleased(mouseX, mouseY, button);
		}
	}
	
	public boolean isHovered(double mouseX, double mouseY) {
		return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
	}
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
	
	public void GUI(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		int offsetY = ((parent.height / 2) - parent.mc.textRenderer.fontHeight / 2);
		int textOffset = ((parent.height / 2) - parent.mc.textRenderer.fontHeight / 2);
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			//Outline
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
				if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled() && ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
					DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width +1, parent.y + offset + parent.height, color);
				}else if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled() && ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()){
					DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width +1, parent.y + offset + parent.height, rainbow(300));
				}else {
					if (parent.category.name == "Movement") {
						DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height, new Color(61,29,158).getRGB());
				 	}
			        else if (parent.category.name == "Combat") {
			        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height, new Color(213,100,100).getRGB());
			        }
			        else if (parent.category.name == "Render") {
			        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height, new Color(158,28,158).getRGB());
			        }
			        else if (parent.category.name == "Exploit") {
			        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height,  new Color(43,169,102).getRGB());
			        }
			        else if (parent.category.name == "World") {
			        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height,Color.orange.getRGB());
			        }
			        else if (parent.category.name == "Hud") {
			        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height,new Color(255,35,35).getRGB());
			        }
					//DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height, Color.black.getRGB());
				}
			}
			if(ModuleManager.INSTANCE.getModule(Gui.class).bottomOutline.isEnabled()) {
				if (parent.buttons.indexOf(this) == parent.buttons.size() - 1) {
					if(ModuleManager.INSTANCE.getModule(Gui.class).bottomOutline.isEnabled() && ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
						DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, color);
					}else if(ModuleManager.INSTANCE.getModule(Gui.class).bottomOutline.isEnabled() && ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
						DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, rainbow(300));
					}else {
						if (parent.category.name == "Movement") {
							DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, new Color(61,29,158).getRGB());
					 	}
				        else if (parent.category.name == "Combat") {
				        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, new Color(213,100,100).getRGB());
				        }
				        else if (parent.category.name == "Render") {
				        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, new Color(158,28,158).getRGB());
				        }
				        else if (parent.category.name == "Exploit") {
				        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1,  new Color(43,169,102).getRGB());
				        }
				        else if (parent.category.name == "World") {
				        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, Color.orange.getRGB());
				        }
				        else if (parent.category.name == "Hud") {
				        	DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, new Color(255,35,35).getRGB());
				        }
						//DrawableHelper.fill(matrices, parent.x - 1, parent.y + offset, parent.x + parent.width + 1, parent.y + offset + parent.height + 1, Color.black.getRGB());
					}
				}
			}
			//main
			if(ModuleManager.INSTANCE.getModule(Gui.class).transparent.isEnabled()) {
				DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height,0x90000000);
			}else {
				if (parent.category.name == "Movement") {
					DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height,module.isEnabled() ? new Color(61,29,158).getRGB() : Color.darkGray.getRGB());
			 	}
		        else if (parent.category.name == "Combat") {
		        	DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height,module.isEnabled() ? new Color(213,100,100).getRGB() : Color.darkGray.getRGB());
		        }
		        else if (parent.category.name == "Render") {
		        	DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height,module.isEnabled() ? new Color(158,28,158).getRGB() : Color.darkGray.getRGB());
		        }
		        else if (parent.category.name == "Exploit") {
		        	DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height,module.isEnabled() ? new Color(43,169,102).getRGB() : Color.darkGray.getRGB());
		        }
		        else if (parent.category.name == "World") {
		        	DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height,module.isEnabled() ? Color.orange.getRGB() : Color.darkGray.getRGB());
		        }
		        else if (parent.category.name == "Hud") {
		        	DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height,module.isEnabled() ? new Color(255,35,35).getRGB() : Color.darkGray.getRGB());
		        }
			}
			//DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width + 10, parent.y + offset + parent.height + 10,  Color.gray.getRGB());
			
			if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
				if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
					parent.nuitofont.draw(matrices, module.getName(), parent.x-1 + offsetY, parent.y-6 + offset + offsetY, module.isEnabled() ?  color : -1, false);
				}else {
					parent.nuitofont.draw(matrices, module.getName(), parent.x-1 + offsetY, parent.y - 6+ offset + offsetY, -1, false);
				}
			}else {
					parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x + textOffset, parent.y + offset + textOffset, -1);
			 	
			}
		}
	}
	
}
