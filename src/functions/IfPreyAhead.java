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
        Predator predator = predatorPrey.predator;
        switch (predator.orientation) {
            case UP:
                if (predator.isFoodUp()) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case LEFT:
                if (predator.isFoodLeft()) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case DOWN:
                if (predator.isFoodDown()) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case RIGHT:
                if (predator.isFoodRight()) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            default:
                evolutionState.output.fatal("Got invalid orientation request in IfPreyAhead-> (" + predatorPrey.predator.orientation + ")");
        }
    }

    /**
     * TODO: copy above code
     * @param evolutionState
     * @param thread
     * @param gpData
     * @param adfStack
     * @param gpIndividual
     * @param problem
     * @param ints
     */

    @Override
    public void evalPrint(EvolutionState evolutionState, int thread, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem, int[][] ints) {
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        Predator predator = predatorPrey.predator;
        switch (predatorPrey.predator.orientation) {
            case UP:
                if (predatorPrey.ground[predator.point.x - 1][predator.point.y] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case LEFT:
                if (predatorPrey.ground[predator.point.x][predator.point.y - 1] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case DOWN:
                if (predatorPrey.ground[predator.point.x + 1][predator.point.y] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case RIGHT:
                if (predatorPrey.ground[predator.point.x][predator.point.y + 1] == -1) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            default:
                evolutionState.output.fatal("Got invalid orientation request in IfPreyAhead-> (" + predator.orientation + ")");
        }
    }
}
