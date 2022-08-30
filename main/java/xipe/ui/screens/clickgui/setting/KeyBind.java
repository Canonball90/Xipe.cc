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

public class KeyBind extends Component { 

	private KeybindSetting binding = (KeybindSetting)setting;
	public boolean isBinding = false;
	public int color;
	
	public KeyBind(Setting setting, ModuleButton parent, int offset) {
		
		super(setting, parent, offset);
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
    	

		//System.out.println(binding.getKey());
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.getMode().equalsIgnoreCase("Original")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
				if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x - 2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width + 2, parent.parent.y + parent.offset + offset + parent.parent.height, rainbow(300));
				}else if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x - 2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width + 2, parent.parent.y + parent.offset + offset + parent.parent.height, color);
				}else
				DrawableHelper.fill(matrices, parent.parent.x - 2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width + 2, parent.parent.y + parent.offset + offset + parent.parent.height, Color.pink.getRGB());
			}
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.darkGray.getRGB());
		}
		
		int offsetY = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
		
		if (isBinding == false) {
			mc.textRenderer.drawWithShadow(matrices, "Keybind: " + binding.getKey(), parent.parent.x + offsetY, parent.parent.y + parent.offset + offset + offsetY, -1);
			} else if (isBinding == true) {
				mc.textRenderer.drawWithShadow(matrices, "Binding...", parent.parent.x + offsetY, parent.parent.y + parent.offset + offset + offsetY, -1);
			}
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
    }
}