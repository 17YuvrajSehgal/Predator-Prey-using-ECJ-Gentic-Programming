package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.app.ant.func.EvalPrint;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import predatorPrey.PredatorPrey;

public class IfPreyAhead extends GPNode implements EvalPrint {

    @Override
    public String toString() {
        return "IF-PREY-AHEAD";
    }

    @Override
    public int expectedChildren() {
        return 2;
    }

    @Override
    public void eval(EvolutionState evolutionState, int thread, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        switch (predatorPrey.predator.orientation) {
            case UP:
                if (predatorPrey.ground[predatorPrey.predator.point.x][(predatorPrey.predator.point.y)% predatorPrey.BOARD_COLUMNS] == -1) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case LEFT:
                if (predatorPrey.ground[(predatorPrey.predator.point.x-1+predatorPrey.BOARD_ROWS)%predatorPrey.BOARD_ROWS][predatorPrey.predator.point.y] == -1) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case DOWN:
                if (predatorPrey.ground[predatorPrey.predator.point.x ][(predatorPrey.predator.point.y+1)%predatorPrey.BOARD_COLUMNS] == -1) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case RIGHT:
                if (predatorPrey.ground[(predatorPrey.predator.point.x+1)%predatorPrey.BOARD_ROWS][predatorPrey.predator.point.y] == -1) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            default:
                evolutionState.output.fatal("Got invalid orientation request in IfPreyAhead-> (" + predatorPrey.predator.orientation + ")");
        }
    }

    @Override
    public void evalPrint(EvolutionState evolutionState, int thread, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem, int[][] ints) {
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        switch (predatorPrey.predator.orientation) {
            case UP:
                if (predatorPrey.ground[predatorPrey.predator.point.x - 1][predatorPrey.predator.point.y] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case LEFT:
                if (predatorPrey.ground[predatorPrey.predator.point.x][predatorPrey.predator.point.y - 1] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case DOWN:
                if (predatorPrey.ground[predatorPrey.predator.point.x + 1][predatorPrey.predator.point.y] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case RIGHT:
                if (predatorPrey.ground[predatorPrey.predator.point.x][predatorPrey.predator.point.y + 1] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            default:
                evolutionState.output.fatal("Got invalid orientation request in IfPreyAhead-> (" + predatorPrey.predator.orientation + ")");
        }
    }
}
