package xipe.ui.screens.clickgui.setting;

import java.awt.Color;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.module.settings.BooleanSetting;
import xipe.module.settings.ModeSetting;
import xipe.module.settings.Setting;
import xipe.ui.screens.clickgui.ModuleButton;
import xipe.utils.font.FontRenderer;

public class ModeBox extends Component{

	protected static FontRenderer nuitofont = new FontRenderer("Comfortaa-Regular.ttf", new Identifier("xipe", "fonts"), 20);
	private ModeSetting modeset = (ModeSetting)setting;
	public int color;
	public int color2;
	
	public ModeBox(Setting setting, ModuleButton parent, int offset) {
		super(setting, parent, offset);
		this.modeset = (ModeSetting)setting;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		this.color = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(),255).getRGB();
		this.color2 = new Color(ModuleManager.INSTANCE.getModule(Gui.class).red.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).green.getValueInt(), ModuleManager.INSTANCE.getModule(Gui.class).blue.getValueInt(), 150).getRGB();
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Original")) {
			if(ModuleManager.INSTANCE.getModule(Gui.class).outline.isEnabled()) {
				if(ModuleManager.INSTANCE.getModule(Gui.class).rainbow.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x - 1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height,  rainbow(300));
				}else if(ModuleManager.INSTANCE.getModule(Gui.class).custom.isEnabled()) {
					DrawableHelper.fill(matrices, parent.parent.x - 1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width+ 1, parent.parent.y + parent.offset + offset + parent.parent.height,  color);
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
				DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height,  new Color(52,52,52).getRGB());
		}
		else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Win98")) {
			DrawableHelper.fill(matrices, parent.parent.x -2, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +2, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x -1, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width +1, parent.parent.y + parent.offset + offset + parent.parent.height, Color.lightGray.getRGB());
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, Color.gray.getRGB());
		}
		else if(ModuleManager.INSTANCE.getModule(Gui.class).theme.isMode("Sigma Old")) {
			DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0,0,0,190).getRGB());
		}
		int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
		
		if(ModuleManager.INSTANCE.getModule(Gui.class).cFont.isEnabled()) {
			nuitofont.draw(matrices, modeset.getName() + ": " + modeset.getMode(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset - 8, -1, false);
		}else {
			mc.textRenderer.drawWithShadow(matrices, modeset.getName() + ": " + modeset.getMode(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, -1);
		}
		
		//mc.textRenderer.drawWithShadow(matrices, modeset.getName() + ": " + modeset.getMode(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, -1);
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(isHovered(mouseX, mouseY) && button == 0) modeset.cycle();
		super.mouseClicked(mouseX, mouseY, button);
	}

	public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
}
