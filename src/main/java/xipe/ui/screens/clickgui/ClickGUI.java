package xipe.ui.screens.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xipe.Client;
import xipe.module.Mod;
import xipe.module.Mod.Category;
import xipe.module.ModuleManager;
import xipe.module.modules.Render.Gui;
import xipe.module.modules.movement.Flight;
import xipe.ui.screens.utils.Particle;
import xipe.ui.screens.utils.TextBox;
import xipe.utils.world.TimerUtil;

public class ClickGUI extends Screen {
	
	public static final ClickGUI INSTANCE = new ClickGUI();
	ArrayList<Particle> particles = new ArrayList();
	
	private List<Frame> frames;
	public static TextBox searchBox;
	private Mod modules;
	
	private ClickGUI() {
		super(Text.literal("Click GUI"));
		
		frames = new ArrayList();
		 ClickGUI.searchBox = new TextBox(0, 0, 100, 15, "#FFFFFF");
		           //20
		int offset = 30;
		for(Category category : Category.values()) {
			frames.add(new Frame(category, offset, 40, 140, 20));
			offset += 150;
		}
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		DrawableHelper.fill(matrices, -1000, -1000, 1000, 1000, new Color(255,255,255,35).getRGB());
		 if (this.pTimer.hasTimeElapsed(5L, true)) {
	            particles.add(new Particle());
	        }
		ClickGUI.searchBox.setX(100);
        ClickGUI.searchBox.setY(0);
        ClickGUI.searchBox.setWidth(75);
        ClickGUI.searchBox.render(matrices, mouseX, mouseY, delta);
        
        for(Particle particle : this.particles) {
            particle.render(matrices);
        }

		for(Frame frame : frames) {
			frame.render(matrices, mouseX, mouseY, delta);
			frame.updatePosition(mouseX, mouseY);
		}
		
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		ClickGUI.searchBox.mouseClicked(mouseX, mouseY, button);
		for(Frame frame : frames) {
			frame.mouseClicked(mouseX, mouseY, button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for(Frame frame : frames) {
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
			for (Frame frame : frames) {                      //5
				if (amount > 0) frame.setY((int) (frame.getY() + ModuleManager.INSTANCE.getModule(Gui.class).scrollSpeed.getValue()));
				else if (amount < 0) frame.setY((int) (frame.getY() - ModuleManager.INSTANCE.getModule(Gui.class).scrollSpeed.getValue()));
			}
			return super.mouseScrolled(mouseX, mouseY, amount);
		}
	 
	 public void categoryIcons() {
		 
	 }
	 
	 @Override
	 public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		  ClickGUI.searchBox.keyPressed(keyCode, scanCode, modifiers);
	        if (keyCode != 259 || ClickGUI.searchBox.getText().length() != 0 || ClickGUI.searchBox.isFocused()) {
	        	
	        }
	        searchBox.keyPressed(keyCode, scanCode, modifiers);
	        searchBox.setSuggestion(searchBox.getText().equals("") ? "Search..." : "");
	     for (Frame frame : frames) {
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
