package xipe.module.modules.Render;

import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.module.settings.BooleanSetting;
import xipe.utils.render.QuadColor;
import xipe.utils.render.RenderUtils;
import xipe.utils.world.WorldUtil;

import java.awt.*;

public class Search extends Mod {

    BooleanSetting shulkers = new BooleanSetting("Shulkers", true);
    BooleanSetting portals = new BooleanSetting("Portals", true);

    public Search() {
        super("Search", "Searches for a block", Mod.Category.RENDER);
        addSettings(shulkers, portals);
    }

    Vec3d blockPos;

    @Override
    public void onWorldRender(MatrixStack matrices) {

        for (BlockEntity block : WorldUtil.getBlockEntities()) {
            if(shulkers.isEnabled()) {
                if (block instanceof ShulkerBoxBlockEntity) {
                    if (block instanceof ShulkerBoxBlockEntity) {
                        RenderUtils.drawBoxBoth(new Box(
                                block.getPos().getX()+0.06,
                                block.getPos().getY(),
                                block.getPos().getZ()+0.06,
                                block.getPos().getX()+0.94,
                                block.getPos().getY()+0.88,
                                block.getPos().getZ()+0.94), QuadColor.single(1f, 1f, 1f, 0.3f), 1);
                    }
                }
            }
            if(portals.isEnabled()) {
                if (block instanceof EndPortalBlockEntity) {
                    if (block instanceof ShulkerBoxBlockEntity) {
                        RenderUtils.drawBoxBoth(new Box(
                                block.getPos().getX()+0.06,
                                block.getPos().getY(),
                                block.getPos().getZ()+0.06,
                                block.getPos().getX()+0.94,
                                block.getPos().getY()+0.88,
                                block.getPos().getZ()+0.94), QuadColor.single(1f, 0f, 0f, 0.3f), 1);
                    } }
            }
        }
        super.onWorldRender(matrices);
    }
}

