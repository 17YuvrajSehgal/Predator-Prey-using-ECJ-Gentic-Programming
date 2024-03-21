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

public class Right extends GPNode implements EvalPrint {

    @Override
    public String toString() {
        return "right";
    }

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        Predator predator = predatorPrey.predator;

        switch (predator.orientation){
            case UP:
                predator.orientation=Orientation.RIGHT;
                break;
            case LEFT:
                predator.orientation=Orientation.UP;
                break;
            case DOWN:
                predator.orientation=Orientation.LEFT;
                break;
            case RIGHT:
                predator.orientation=Orientation.DOWN;
                break;
            default:
                evolutionState.output.fatal("Got invalid orientation request in Right-> (" + predatorPrey.predator.orientation + ")");
        }
        predatorPrey.MOVES++;
    }

    @Override
    public void evalPrint(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem, int[][] map2) {
        this.eval(state, thread, input, stack, individual, problem);
    }
}
