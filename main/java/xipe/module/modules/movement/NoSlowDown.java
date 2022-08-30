package xipe.module.modules.movement;

import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class NoSlowDown extends Mod{
	
	public BooleanSetting items = new BooleanSetting("Items", true);
	BooleanSetting inventory = new BooleanSetting("Inventory", true);
	BooleanSetting shift = new BooleanSetting("Shift", true);
	
	public static NoSlowDown INSTANCE = new NoSlowDown();

	public NoSlowDown() {
		super("NoSlow", "Say no to slow", Category.MOVEMENT);
	}
	
	 @Override
	    public void onTick() {
	        if (inventory.isEnabled() && mc.currentScreen != null && !(mc.currentScreen instanceof ChatScreen) && !(mc.currentScreen instanceof SignEditScreen) && !(mc.currentScreen instanceof BookScreen)) {
	            for (KeyBinding k : new KeyBinding[]{mc.options.forwardKey, mc.options.backKey,
	                    mc.options.leftKey, mc.options.rightKey, mc.options.jumpKey, mc.options.sprintKey}) {
	                k.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(),
	                        InputUtil.fromTranslationKey(k.getBoundKeyTranslationKey()).getCode()));
	            }
	            if (shift.isEnabled()) mc.options.sneakKey.setPressed(InputUtil.isKeyPressed(mc.getWindow().getHandle(),
	                    InputUtil.fromTranslationKey(mc.options.sneakKey.getBoundKeyTranslationKey()).getCode()));
	        }
	    }

}
