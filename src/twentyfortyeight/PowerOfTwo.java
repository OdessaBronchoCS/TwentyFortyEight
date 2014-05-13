/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twentyfortyeight;

import java.awt.Color;

/**
 *
 * @author scott.walker
 */
public class PowerOfTwo {

    private int power;
    private Color color;

    public PowerOfTwo(int n) {
        power = n;
        setColor();
    }

    @Override
    public String toString() {
        return String.valueOf((int) Math.pow(2, power));
    }

    public Color getColor() {
        return color;
    }

    public int getPower() {
        return power;
    }

    public void increase() {
        power++;
        setColor();
    }

    private void setColor() {
        int factor = 255 - 10 * power;
        color = new Color(factor, 255, factor);
    }
}
