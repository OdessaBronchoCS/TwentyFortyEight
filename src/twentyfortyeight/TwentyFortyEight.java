/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twentyfortyeight;

import info.gridworld.grid.BoundedGrid;

/**
 *
 * @author scott.walker
 */
public class TwentyFortyEight {

    static {
        System.setProperty("info.gridworld.gui.selection", "hide");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BoundedGrid<PowerOfTwo> grid = new BoundedGrid<>(4, 4);
        TwentyFortyEightWorld world = new TwentyFortyEightWorld(grid);
        world.show();
        do {            
            world.run();
//            String msg = world.getMessage();
//            world.pause(msg + "\nGame over!");
        } while (true);
    }
}
