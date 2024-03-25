/**
 * Represents the prey entity in the predator-prey simulation.
 */
package predatorPrey;

import java.awt.*;
import java.util.Random;

public class Prey {
    public Point location;
    public int[][] ground;
    public Orientation orientation;
    private final int BOARD_ROWS;
    private final int BOARD_COLUMNS;
    public boolean isAlive;

    /**
     * Constructs a prey object with the specified location and ground layout.
     * @param location The initial location of the prey.
     * @param ground The layout of the ground where the prey moves.
     * @param BOARD_ROWS The number of rows in the ground layout.
     * @param BOARD_COLUMNS The number of columns in the ground layout.
     */
    public Prey(Point location, int[][] ground,final int BOARD_ROWS, final int BOARD_COLUMNS) {
        this.location = location;
        this.BOARD_ROWS=BOARD_ROWS;
        this.BOARD_COLUMNS=BOARD_COLUMNS;
        this.orientation = Orientation.RIGHT;
        this.ground = ground;
        this.isAlive=true;
    }

    /**
     * Moves the prey upwards on the grid.
     */
    public void moveUp(){
        ground[this.location.x][location.y]=1;
        location.y--;
        if(location.y < 0)
            location.y = BOARD_COLUMNS-1;
        ground[this.location.x][location.y]=-1;
    }

    /**
     * Moves the prey downwards on the grid.
     */
    public void moveDown(){
        ground[this.location.x][location.y]=1;
        location.y++;
        if(location.y >= BOARD_ROWS)
            location.y=0;
        ground[this.location.x][location.y]=-1;
    }

    /**
     * Moves the prey to the left on the grid.
     */
    public void moveLeft(){
        ground[this.location.x][location.y]=1;
        location.x--;
        if(location.x < 0)
            location.x = BOARD_COLUMNS-1;
        ground[this.location.x][location.y]=-1;
    }

    /**
     * Moves the prey to the right on the grid.
     */
    public void moveRight(){
        ground[this.location.x][location.y]=1;
        location.x++;
        if(location.x >= BOARD_COLUMNS)
            location.x = 0;
        ground[this.location.x][location.y]=-1;
    }

    /**
     * Moves the prey randomly in one of four directions.
     * If the prey is dead, no movement occurs.
     */
    public void moveRandom(){
        if(!this.isAlive){
            return;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(4);
        switch (randomIndex){
            case 1:
                this.moveUp();
                break;
            case 2:
                this.moveLeft();
                break;
            case 3:
                this.moveDown();
                break;
            default: this.moveRight();

        }
    }
}
