package predatorPrey;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preys {
    private final List<Prey> preyList; // List to store the preys.
    int[][] ground; // The ground grid.

    /**
     * Constructor for the Preys class.
     * @param totalPreys The total number of preys.
     * @param ground The ground grid.
     */
    public Preys(int totalPreys, int[][] ground){
        this.preyList = new ArrayList<>(totalPreys);
        this.ground = ground;
    }

    /**
     * Adds a prey to the collection.
     * @param prey The prey to add.
     */
    public void addPrey(Prey prey){
        this.preyList.add(prey);
    }

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

    public void aliveAll() {
        for(Prey prey:preyList){
            prey.isAlive=true;
        }
    }
}
