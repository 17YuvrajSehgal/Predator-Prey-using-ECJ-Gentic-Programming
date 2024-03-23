package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.app.ant.func.EvalPrint;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import predatorPrey.Orientation;
import predatorPrey.Predator;
import predatorPrey.PredatorPrey;

public class Move extends GPNode implements EvalPrint{

    @Override
    public String toString() {
        return "move";
    }
    @Override
    public int expectedChildren(){
        return 0;
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        PredatorPrey predatorPrey = (PredatorPrey)problem;
        Predator predator = predatorPrey.predator;
        Orientation currentPredOrie = predator.orientation;
        switch (currentPredOrie){
            case UP:
                predatorPrey.predator.point.y--;
                if(predator.point.y < 0)
                    predator.point.y = predatorPrey.BOARD_ROWS-1;
                break;
            case LEFT:
                predatorPrey.predator.point.x--;
                if(predator.point.x < 0)
                    predator.point.x = predatorPrey.BOARD_COLUMNS-1;
                break;
            case DOWN:
                predatorPrey.predator.point.y++;
                if(predator.point.y >= predatorPrey.BOARD_ROWS)
                    predator.point.y=0;
                break;
            case RIGHT:
                predatorPrey.predator.point.x++;
                if(predator.point.x >= predatorPrey.BOARD_COLUMNS)
                    predator.point.x = 0;
                break;
            default:
        }

        //System.out.println("###################################################################New location: "+predator.point);

        predatorPrey.MOVES++;
        if(predatorPrey.ground[predator.point.x][predator.point.y]==-1 && predatorPrey.MOVES<predatorPrey.MAX_MOVES){
            predatorPrey.TOTAL_PREY_KILLED++;
            predatorPrey.ground[predator.point.x][predator.point.y]=3;

        }
    }

    @Override
    public void evalPrint(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem, int[][] ints) {

    }
}
