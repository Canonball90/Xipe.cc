package xipe.ui.screens.csgo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xipe.module.Mod;
import xipe.module.Mod.Category;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.ui.screens.utils.Particle;
import xipe.ui.screens.utils.TextBox;
import xipe.utils.world.TimerUtil;

public class MenuGUI extends Screen {
	
	public static final MenuGUI INSTANCE = new MenuGUI();
	ArrayList<Particle> particles = new ArrayList();
	
	private List<Cframe> frames;
	public static TextBox searchBox;
	private Mod modules;
	
	private MenuGUI() {
		super(Text.literal("Click GUI"));
		
		frames = new ArrayList();
		 MenuGUI.searchBox = new TextBox(0, 0, 100, 15, "#FFFFFF");
		           //20
		int offset = 30;
			//offset += 150;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		DrawableHelper.fill(matrices, -1000, -1000, 1000, 1000, new Color(255,255,255,35).getRGB());
		 if (this.pTimer.hasTimeElapsed(5L, true)) {
	            particles.add(new Particle());
	        }
		MenuGUI.searchBox.setX(100);
        MenuGUI.searchBox.setY(0);
        MenuGUI.searchBox.setWidth(75);
        MenuGUI.searchBox.render(matrices, mouseX, mouseY, delta);
        
        for(Particle particle : this.particles) {
            particle.render(matrices);
        }
        
        DrawableHelper.fill(matrices, 600, 100, 100, 400, new Color(35,35,35,200).getRGB());

		DrawableHelper.fill(matrices, 300, 200, 200, 300, new Color(46, 253, 0, 255).getRGB());

		
		for(Cframe frame : frames) {
			frame.render(matrices, mouseX, mouseY, delta);
			frame.updatePosition(mouseX, mouseY);
		}
		
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		MenuGUI.searchBox.mouseClicked(mouseX, mouseY, button);
		for(Cframe frame : frames) {
			frame.mouseClicked(mouseX, mouseY, button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for(Cframe frame : frames) {
			frame.mouseReleased(mouseX, mouseY, button);
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	TimerUtil pTimer = new TimerUtil();
	
	 @Override
	    public boolean shouldPause() {
	        return false;
	    }
	 private void load() {
	        final Random random = new Random();
	        for (int i = 0; i < 100; ++i) {
	           
	        }
	 }
	 
	 @Override
		public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
			for (Cframe frame : frames) {                      //5
				if (amount > 0) frame.setY((int) (frame.getY() + ModuleManager.INSTANCE.getModule(Gui.class).scrollSpeed.getValue()));
				else if (amount < 0) frame.setY((int) (frame.getY() - ModuleManager.INSTANCE.getModule(Gui.class).scrollSpeed.getValue()));
			}
			return super.mouseScrolled(mouseX, mouseY, amount);
		}
	 
	 public void categoryIcons() {
		 
	 }
	 
	 @Override
	 public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		  MenuGUI.searchBox.keyPressed(keyCode, scanCode, modifiers);
	        if (keyCode != 259 || MenuGUI.searchBox.getText().length() != 0 || MenuGUI.searchBox.isFocused()) {
	        	
	        }
	        searchBox.keyPressed(keyCode, scanCode, modifiers);
	        searchBox.setSuggestion(searchBox.getText().equals("") ? "Search..." : "");
	     for (Cframe frame : frames) {
	         frame.keyPressed(keyCode);
	     }
	     return super.keyPressed(keyCode, scanCode, modifiers);
	 }
	 
	 @Override public boolean charTyped(char c, int i) {
		 searchBox.charTyped(c, i);
		 searchBox.setSuggestion(searchBox.getText().equals("") ? "Search..." : "");
	        return super.charTyped(c, i);
	    }
	    
}
