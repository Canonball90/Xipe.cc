package xipe.module.modules.Render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import xipe.module.Mod;
import xipe.utils.Utils;
import xipe.utils.render.RenderUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Trail extends Mod {
    List<Vec3d> positions = new ArrayList<>();
    public Trail() {
        super("Trail", "Draws a trail behind you", Category.RENDER);
    }

    @Override
    public void onTick() {
        positions.add(Utils.getInterpolatedEntityPosition(mc.player));
        while(positions.size() > 1000) positions.remove(0);
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {
        Vec3d before = null;
        List<Vec3d> bk = new ArrayList<>(positions);
        float progressOffset = (System.currentTimeMillis()%1000)/1000f;
        for (int i = 0; i < bk.size(); i++) {
            float progress = i/(float)bk.size();
//            progress = 0;
            progress += progressOffset;
            progress %= 1;
            Vec3d vec3d = bk.get(i);
            if (vec3d == null) continue;
            if (before == null) before = vec3d;
            RenderUtils.renderLine(before,vec3d, Color.getHSBColor(progress,0.6f,1f),matrices);
            before = vec3d;
        }

    }
}

