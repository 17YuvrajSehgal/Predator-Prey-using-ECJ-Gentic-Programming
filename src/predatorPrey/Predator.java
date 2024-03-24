package predatorPrey;

import java.awt.*;

public class Predator {
    public Point point;
    public Orientation orientation = Orientation.RIGHT;
    private int[][] ground;
    private final int BOARD_ROWS;
    private final int BOARD_COLUMNS;

    public Predator(Point point, int[][] ground, final int BOARD_ROWS, final int BOARD_COLUMNS) {
        this.point = point;
        this.ground = ground;
        this.BOARD_ROWS=BOARD_ROWS;
        this.BOARD_COLUMNS=BOARD_COLUMNS;
    }

    public void moveUp(){
        point.y--;
        if(point.y < 0)
            point.y = BOARD_ROWS-1;
    }
    public void moveDown(){
        point.y++;
        if(point.y >= BOARD_ROWS)
            point.y=0;
    }

    public void moveLeft(){
        point.x--;
        if(point.x < 0)
            point.x = BOARD_COLUMNS-1;
    }

    public void moveRight(){
        point.x++;
        if(point.x >= BOARD_COLUMNS)
            point.x = 0;
    }

    public boolean isFoodUp(){
        return ground[point.x][(point.y)% BOARD_COLUMNS] == -1;
    }

    public boolean isFoodLeft(){
        return ground[(point.x-1+BOARD_ROWS)%BOARD_ROWS][point.y] == -1;
    }

    public boolean isFoodDown(){
        return ground[point.x ][(point.y+1)%BOARD_COLUMNS] == -1;
    }

    public boolean isFoodRight(){
        return ground[(point.x+1)%BOARD_ROWS][point.y] == -1;
    }


}
