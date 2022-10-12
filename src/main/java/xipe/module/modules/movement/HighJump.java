package xipe.module.modules.movement;

import xipe.module.Mod;

public class HighJump extends Mod {


    public HighJump() {
        super("High Jump", "Jump Higher", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        nullCheck();
        if(mc.options.jumpKey.isPressed()) {
            mc.player.setVelocity(mc.player.getVelocity().x, 1, mc.player.getVelocity().z);
        }

        super.onTick();
    }
}
