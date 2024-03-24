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

    public Prey(Point location, int[][] ground,final int BOARD_ROWS, final int BOARD_COLUMNS) {
        this.location = location;
        this.BOARD_ROWS=BOARD_ROWS;
        this.BOARD_COLUMNS=BOARD_COLUMNS;
        this.orientation = Orientation.RIGHT;
        this.ground = ground;
        this.isAlive=true;
    }

    /**
     * TODO: make sure to cover the case when two predators at same location
     */
    public void moveUp(){
        ground[this.location.x][location.y]=1;
        location.y--;
        if(location.y < 0)
            location.y = BOARD_ROWS-1;
        ground[this.location.x][location.y]=-1;
    }
    public void moveDown(){
        ground[this.location.x][location.y]=1;
        location.y++;
        if(location.y >= BOARD_ROWS)
            location.y=0;
        ground[this.location.x][location.y]=-1;
    }

    public void moveLeft(){
        ground[this.location.x][location.y]=1;
        location.x--;
        if(location.x < 0)
            location.x = BOARD_COLUMNS-1;
        ground[this.location.x][location.y]=-1;
    }

    public void moveRight(){
        ground[this.location.x][location.y]=1;
        location.x++;
        if(location.x >= BOARD_COLUMNS)
            location.x = 0;
        ground[this.location.x][location.y]=-1;
    }

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
