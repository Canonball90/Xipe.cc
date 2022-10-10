package xipe.module.modules.movement;

import xipe.module.Mod;

public class AirJump extends Mod {

    public AirJump() {
        super("AirJump", "Allows you to jump mid air", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        nullCheck();
        if (mc.options.jumpKey.isPressed()) {
            mc.player.setOnGround(true);
            mc.player.fallDistance = 0f;
        }
    }
}
