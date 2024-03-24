package predatorPrey;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preys {
    private final List<Prey> preyList;
    private final List<Point> initialLocations;
    int[][] ground;

    public Preys(int totalPreys, int[][] ground){
        this.preyList = new ArrayList<>(totalPreys);
        this.initialLocations = new ArrayList<>(totalPreys);
        this.ground = ground;
    }

    public void addPrey(Prey prey){
        this.preyList.add(prey);
        this.initialLocations.add(prey.location);
    }

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

    public void moveAllAlivePreysRandomly(){
        for (Prey prey : preyList){
            if(prey.isAlive)
                prey.moveRandom();
        }
    }


    public void killPreyAt(int x, int y) {
        for(Prey prey:preyList){
            if(prey.location.x==x && prey.location.y==y){
                prey.isAlive=false;
                return;
            }
        }
    }
}
