package xipe.event.events;

import net.minecraft.client.util.math.MatrixStack;
import xipe.event.Event;

/**
 * @author cattyngmd
 */

public class RenderEvent extends Event {

    private final MatrixStack stack;

    public RenderEvent(MatrixStack stack) {
        this.stack = stack;
    }

    public MatrixStack getMatrixStack() {
        return stack;
    }

}
