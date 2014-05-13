/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twentyfortyeight;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.KeyStroke;

/**
 *
 * @author scott.walker
 */
public class TwentyFortyEightWorld extends World<PowerOfTwo> {

    private Semaphore s = new Semaphore(0);
    ArrayList<PowerOfTwo> tileSet = new ArrayList<>();
    ArrayList<Location> locSet = new ArrayList<>();

    public TwentyFortyEightWorld(BoundedGrid<PowerOfTwo> grid) {
        super(grid);
    }
    
    private Location getRandomEmptyLocation(Grid<PowerOfTwo> grid) {
        ArrayList<Location> occ = grid.getOccupiedLocations();
        int row;
        int col;
        Location target;
        do {
            row = (int) (Math.random() * grid.getNumRows());
            col = (int) (Math.random() * grid.getNumCols());
            target = new Location(row, col);
        } while (occ.contains(target));
        return target;
    }

    @Override
    public boolean keyPressed(String desc, Location loc) {
        KeyStroke ks = KeyStroke.getKeyStroke(desc);
        switch (ks.getKeyCode()) {
            case KeyEvent.VK_UP:
                pushUp();
                break;
            case KeyEvent.VK_LEFT:
                pushLeft();
                break;
            case KeyEvent.VK_RIGHT:
                pushRight();
                break;
            case KeyEvent.VK_DOWN:
                pushDown();
                break;
            default:
                return true;
        }
        s.release();
        return true;
    }

    private void pushUp() {
        for (int c = 0; c < getGrid().getNumCols(); c++) {
            for (int r = 0; r < getGrid().getNumRows(); r++) {
                locSet.add(new Location(r, c));
            }
            shiftTiles();
        }
    }

    private void pushLeft() {
        for (int r = 0; r < getGrid().getNumRows(); r++) {
            for (int c = 0; c < getGrid().getNumCols(); c++) {
                locSet.add(new Location(r, c));
            }
            shiftTiles();
        }
    }

    private void pushRight() {
        for (int r = 0; r < getGrid().getNumRows(); r++) {
            for (int c = getGrid().getNumCols() - 1; c >= 0; c--) {
                locSet.add(new Location(r, c));
            }
            shiftTiles();
        }
    }

    private void pushDown() {
        for (int c = 0; c < getGrid().getNumCols(); c++) {
            for (int r = getGrid().getNumRows() - 1; r >= 0; r--) {
                locSet.add(new Location(r, c));
            }
            shiftTiles();
        }
    }

    public void run() {
        do {
            Location loc = this.getRandomEmptyLocation(getGrid());
            int pwr = (int) (Math.random() + 1.3);
            this.add(loc, new PowerOfTwo(pwr));
            pause();
        } while (true);
    }

    private void pause() {
        try {
            s.acquire();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void collapse() {
        for (int r = 0; r < tileSet.size(); r++) {
            if (r + 1 < tileSet.size()
                    && tileSet.get(r).getPower() == tileSet.get(r + 1).getPower()) {
                tileSet.get(r).increase();
                tileSet.remove(r + 1);
            }
        }
    }

    private void loadTiles() {
        for (Location loc : locSet) {
            PowerOfTwo pt = getGrid().remove(loc);
            if (pt != null) {
                tileSet.add(pt);
            }
        }
    }

    private void replaceTiles() {
        for (int i = 0; i < tileSet.size(); i++) {
            getGrid().put(locSet.get(i), tileSet.get(i));
        }
    }

    private void shiftTiles() {
        loadTiles();
        collapse();
        replaceTiles();
        locSet.clear();
        tileSet.clear();
    }
}
