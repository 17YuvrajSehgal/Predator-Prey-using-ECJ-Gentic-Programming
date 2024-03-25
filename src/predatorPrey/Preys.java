/**
 * Represents a collection of preys in a predator-prey simulation.
 */
package predatorPrey;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preys {
    private final List<Prey> preyList; // List to store the preys.
    private final List<Point> initialLocations; // List to store the initial locations of the preys.
    int[][] ground; // The ground grid.

    /**
     * Constructor for the Preys class.
     * @param totalPreys The total number of preys.
     * @param ground The ground grid.
     */
    public Preys(int totalPreys, int[][] ground){
        this.preyList = new ArrayList<>(totalPreys);
        this.initialLocations = new ArrayList<>(totalPreys);
        this.ground = ground;
    }

    /**
     * Adds a prey to the collection.
     * @param prey The prey to add.
     */
    public void addPrey(Prey prey){
        this.preyList.add(prey);
        this.initialLocations.add(prey.location);
    }

    /**
     * Restores the initial locations of all the preys.
     */
    public void restorePreyLocations() {
        for(int[] row:ground){
            Arrays.fill(row,1);
        }
        for (int i = 0; i < preyList.size(); i++) {
            Prey prey = preyList.get(i);
            Point location = prey.location = initialLocations.get(i);
            ground[location.x][location.y]=-1;
        }
    }

    /**
     * Moves all alive preys randomly.
     */
    public void moveAllAlivePreysRandomly(){
        for (Prey prey : preyList){
            if(prey.isAlive)
                prey.moveRandom();
        }
    }

    /**
     * Kills the prey at the specified location.
     * @param x The x-coordinate of the location.
     * @param y The y-coordinate of the location.
     */
    public void killPreyAt(int x, int y) {
        for(Prey prey:preyList){
            if(prey.location.x==x && prey.location.y==y){
                prey.isAlive=false;
                return;
            }
        }
    }
}
