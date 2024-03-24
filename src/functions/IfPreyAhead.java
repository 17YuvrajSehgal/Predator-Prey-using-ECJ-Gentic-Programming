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
                if (predator.isPreyUp()) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case LEFT:
                if (predator.isPreyLeft()) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case DOWN:
                if (predator.isPreyDown()) {
                    this.children[0].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                } else {
                    this.children[1].eval(evolutionState, thread, gpData, adfStack, gpIndividual, problem);
                }
                break;

            case RIGHT:
                if (predator.isPreyRight()) {
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
        Predator predator = predatorPrey.predator;
        switch (predator.orientation) {
            case UP:
                if (predator.isPreyUp()) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case LEFT:
                if (predator.isPreyLeft()) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case DOWN:
                if (predator.isPreyDown()) {
                    ((EvalPrint)this.children[0]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                } else {
                    ((EvalPrint)this.children[1]).evalPrint(evolutionState, thread, gpData, adfStack, gpIndividual, problem, ints);
                }
                break;

            case RIGHT:
                if (predator.isPreyRight()) {
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
