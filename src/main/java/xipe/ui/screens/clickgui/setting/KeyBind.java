package xipe.ui.screens.clickgui.setting;


import java.awt.Color;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.module.settings.KeybindSetting;
import xipe.module.settings.Setting;
import xipe.ui.screens.clickgui.ModuleButton;
import xipe.utils.font.FontRenderer;

public class KeyBind extends Component { 

	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	//public boolean isBinding = false;
		public int color;
		private KeybindSetting binding;
	    public boolean isBinding;
	    
	    public KeyBind(final Setting setting, final ModuleButton parent, final int offset) {
	        super(setting, parent, offset);
	        this.binding = (KeybindSetting)this.setting;
	        this.isBinding = false;
	    }

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (isHovered(mouseX, mouseY) && button == 0) {
			binding.toggle();
			isBinding = true;
		}
		super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public void keyPressed(int key) {
		if (isBinding == true) {
			parent.module.setKey(key);
			binding.setKey(key);
			isBinding = false;
		}
		if ((binding.getKey() == 256)) {
			parent.module.setKey(0);
			binding.setKey(0);
			isBinding = false;
		}
		if ((binding.getKey() == 259)) {
			parent.module.setKey(0);
			binding.setKey(0);
			isBinding = false;
		}
		super.keyPressed(key);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		this.color = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 255).getRGB();
		int offsetY = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);

		//System.out.println(binding.getKey());
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.getMode().equalsIgnoreCase("Original")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
				if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x - 1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, rainbow(300));
				}else if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x - 1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, color);
				}else
					if (parent.parent.category.name == "Movement") {
						DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(61,29,158).getRGB());
						}else if (parent.parent.category.name == "Combat") {
				        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(213,100,100).getRGB());
				        }else if (parent.parent.category.name == "Render") {
				        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(158,28,158).getRGB());
				        }else if (parent.parent.category.name == "Exploit") {
				        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(43,169,102).getRGB());
				        }else if (parent.parent.category.name == "World") {
				        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, Color.orange.getRGB());
				        }else if (parent.parent.category.name == "Hud") {
				        	DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(255,35,35).getRGB());
				        }
				}
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(52,52,52).getRGB());
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.getMode().equalsIgnoreCase("Win98")) {
			DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset - 20, parent.parent.x + parent.parent.width +1, parent.parent.y + parent.offset + offset + parent.parent.height, Color.lightGray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
			mc.textRenderer.drawWithShadow(matrices,parent.module.getName(), parent.parent.x + offsetY, parent.parent.y + parent.offset + offset + offsetY - 20, -1);
		}else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Sigma Old")) {
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0,0,0,190).getRGB());
		}
		
		
		if (isBinding == false) {
			 if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
				 nuitofont.draw(matrices, "Keybind: " + binding.getKey(), parent.parent.x + offsetY, parent.parent.y + parent.offset + offset + offsetY-7, -1, false);
			 }else {
				 mc.textRenderer.drawWithShadow(matrices, "Keybind: " + binding.getKey(), parent.parent.x + offsetY, parent.parent.y + parent.offset + offset + offsetY, -1);
			 }
			 } else if (isBinding == true) {
				mc.textRenderer.drawWithShadow(matrices, "Binding...", parent.parent.x + offsetY, parent.parent.y + parent.offset + offset + offsetY, -1);
			}
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}