package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.app.ant.func.EvalPrint;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import predatorPrey.Predator;
import predatorPrey.PredatorPrey;

public class Move extends GPNode implements EvalPrint {

    @Override
    public String toString() {
        return "move";
    }

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        Predator predator = predatorPrey.predator;
        switch (predator.orientation) {
            case UP:
                predator.moveUp();
                break;
            case LEFT:
                predator.moveLeft();
                break;
            case DOWN:
                predator.moveDown();
                break;
            case RIGHT:
                predator.moveRight();
                break;
            default:
                evolutionState.output.fatal("Got invalid orientation request in Right-> (" + predator.orientation + ")");
        }

        predatorPrey.MOVES++;
        if (predatorPrey.ground[predator.location.x][predator.location.y] == -1 && predatorPrey.MOVES < predatorPrey.MAX_MOVES) {
            predatorPrey.TOTAL_PREY_KILLED++;
            predatorPrey.ground[predator.location.x][predator.location.y] = 3;
        }
        predatorPrey.preys.moveAllPreysRandomly();
        //predatorPrey.printArray();
    }

    @Override
    public void evalPrint(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem, int[][] ints) {

    }
}
