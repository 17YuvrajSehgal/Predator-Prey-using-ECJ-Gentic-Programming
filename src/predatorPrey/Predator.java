package predatorPrey;

import java.awt.*;

public class Predator {
    public Point location;
    public Orientation orientation = Orientation.RIGHT;
    private final int[][] ground;
    private final int BOARD_ROWS;
    private final int BOARD_COLUMNS;

    public Predator(Point location, int[][] ground, final int BOARD_ROWS, final int BOARD_COLUMNS) {
        this.location = location;
        this.ground = ground;
        this.BOARD_ROWS=BOARD_ROWS;
        this.BOARD_COLUMNS=BOARD_COLUMNS;
    }

    public void moveUp(){
        location.y--;
        if(location.y < 0)
            location.y = BOARD_ROWS-1;
    }
    public void moveDown(){
        location.y++;
        if(location.y >= BOARD_ROWS)
            location.y=0;
    }

    public void moveLeft(){
        location.x--;
        if(location.x < 0)
            location.x = BOARD_COLUMNS-1;
    }

    public void moveRight(){
        location.x++;
        if(location.x >= BOARD_COLUMNS)
            location.x = 0;
    }

    public boolean isPreyUp(){
        return ground[location.x][(location.y)% BOARD_COLUMNS] == -1;
    }

    public boolean isPreyLeft(){
        return ground[(location.x-1+BOARD_ROWS)%BOARD_ROWS][location.y] == -1;
    }

    public boolean isPreyDown(){
        return ground[location.x ][(location.y+1)%BOARD_COLUMNS] == -1;
    }

    public boolean isPreyRight(){
        return ground[(location.x+1)%BOARD_ROWS][location.y] == -1;
    }


}
