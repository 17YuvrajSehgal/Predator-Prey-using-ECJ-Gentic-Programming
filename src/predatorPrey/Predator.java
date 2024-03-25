/**
 * Represents a predator in a predator-prey simulation.
 */
package predatorPrey;

import java.awt.*;

public class Predator {
    public Point location; // The location of the predator on the grid.
    public Orientation orientation = Orientation.RIGHT; // The orientation of the predator.
    private final int[][] ground; // The ground grid.
    private final int BOARD_ROWS; // The number of rows in the grid.
    private final int BOARD_COLUMNS; // The number of columns in the grid.

    /**
     * Constructor for the Predator class.
     * @param location The initial location of the predator.
     * @param ground The ground grid.
     * @param BOARD_ROWS The number of rows in the grid.
     * @param BOARD_COLUMNS The number of columns in the grid.
     */
    public Predator(Point location, int[][] ground, final int BOARD_ROWS, final int BOARD_COLUMNS) {
        this.location = location;
        this.ground = ground;
        this.BOARD_ROWS=BOARD_ROWS;
        this.BOARD_COLUMNS=BOARD_COLUMNS;
    }

    /**
     * Moves the predator up.
     * If the predator reaches the boundary, it wraps around to the opposite side.
     */
    public void moveUp(){
        location.y--;
        if(location.y < 0)
            location.y = BOARD_COLUMNS-1;
    }

    /**
     * Moves the predator down.
     * If the predator reaches the boundary, it wraps around to the opposite side.
     */
    public void moveDown(){
        location.y++;
        if(location.y >= BOARD_COLUMNS)
            location.y=0;
    }

    /**
     * Moves the predator left.
     * If the predator reaches the boundary, it wraps around to the opposite side.
     */
    public void moveLeft(){
        location.x--;
        if(location.x < 0)
            location.x = BOARD_ROWS-1;
    }

    /**
     * Moves the predator right.
     * If the predator reaches the boundary, it wraps around to the opposite side.
     */
    public void moveRight(){
        location.x++;
        if(location.x >= BOARD_COLUMNS)
            location.x = 0;
    }

    /**
     * Checks if there is prey in the cell above the predator.
     * @return True if there is prey above the predator, false otherwise.
     */
    public boolean isPreyUp(){
        return ground[location.x][(location.y-1+BOARD_COLUMNS)% BOARD_COLUMNS] == -1;
    }

    /**
     * Checks if there is prey in the cell to the left of the predator.
     * @return True if there is prey to the left of the predator, false otherwise.
     */
    public boolean isPreyLeft(){
        return ground[(location.x-1+BOARD_ROWS)%BOARD_ROWS][location.y] == -1;
    }

    /**
     * Checks if there is prey in the cell below the predator.
     * @return True if there is prey below the predator, false otherwise.
     */
    public boolean isPreyDown(){
        return ground[location.x][(location.y+1)%BOARD_COLUMNS] == -1;
    }

    /**
     * Checks if there is prey in the cell to the right of the predator.
     * @return True if there is prey to the right of the predator, false otherwise.
     */
    public boolean isPreyRight(){
        return ground[(location.x+1)%BOARD_ROWS][location.y] == -1;
    }
}
