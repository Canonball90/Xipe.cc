package xipe.ui.screens.clickgui;


import java.util.Random;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class Snow
{
    private int _x;
    private int _y;
    private int _fallingSpeed;
    private int _size;

    public Snow(final int x, final int y, final int fallingSpeed, final int size) {
        this._x = x;
        this._y = y;
        this._fallingSpeed = fallingSpeed;
        this._size = size;
    }


    public int getX() {
        return this._x;
    }

    public void setX(final int x) {
        this._x = x;
    }

    public int getY() {
        return this._y;
    }

    public void setY(final int _y) {
        this._y = _y;
    }

    public void Update(MatrixStack matrices) {
    	DrawableHelper.fill(matrices,this.getX(), this.getY(), (this.getX() + this._size), (this.getY() + this._size),  -1714829883);
        this.setY(this.getY() + this._fallingSpeed);
        if (this.getY() > MinecraftClient.getInstance().getWindow().getScaledHeight() + 10 || this.getY() < -10) {
            this.setY(-10);
            final Random rand = new Random();
            this._fallingSpeed = rand.nextInt(5) + 1;
            this._size = rand.nextInt(4) + 1;
        }
    }
}

