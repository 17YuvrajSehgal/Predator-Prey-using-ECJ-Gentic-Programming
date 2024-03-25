/**
 * Represents a genetic programming node for moving the predator in a Predator-Prey simulation.
 * This node evaluates the orientation of the predator and moves it accordingly.
 */
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

    /**
     * Returns a string representation of the Move node.
     *
     * @return The string "move".
     */
    @Override
    public String toString() {
        return "move";
    }

    /**
     * Returns the expected number of children nodes for the Move node, which is 0.
     *
     * @return The expected number of children nodes, which is 0.
     */
    @Override
    public int expectedChildren() {
        return 0;
    }

    /**
     * Evaluates the Move node, determining the orientation of the predator and moving it accordingly.
     * Updates various parameters of the Predator-Prey simulation.
     *
     * @param evolutionState The current evolution state.
     * @param i              The num of threads
     * @param gpData         The GPData object.
     * @param adfStack       The ADF stack.
     * @param gpIndividual   The GPIndividual being evaluated.
     * @param problem        The current problem being solved.
     */
    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        // Cast the problem to PredatorPrey
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        // Get the predator from the PredatorPrey instance
        Predator predator = predatorPrey.predator;

        // Move the predator based on its orientation
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

        // Increment the number of moves made by the predator
        predatorPrey.MOVES++;

        // Check if the predator has caught a prey
        if (predatorPrey.ground[predator.location.x][predator.location.y] == -1 && predatorPrey.MOVES < predatorPrey.MAX_MOVES) {
            predatorPrey.preys.killPreyAt(predator.location.x, predator.location.y);
            predatorPrey.TOTAL_PREY_KILLED++;
            predatorPrey.ground[predator.location.x][predator.location.y] = 1;//TODO
        }

        // Move all the alive preys randomly
        predatorPrey.preys.moveAllAlivePreysRandomly();
    }

    /**
     * Evaluates the Move node similar to eval() method but also performs additional actions for printing.
     *
     * @param evolutionState The current evolution state.
     * @param i              The current evaluation subpopulation.
     * @param gpData         The GPData object.
     * @param adfStack       The ADF stack.
     * @param gpIndividual   The GPIndividual being evaluated.
     * @param problem        The current problem being solved.
     * @param ground2        A 2D array representing the ground of the simulation.
     */
    @Override
    public void evalPrint(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem, int[][] ground2) {
        // Cast the problem to PredatorPrey
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        // Get the predator from the PredatorPrey instance
        Predator predator = predatorPrey.predator;

        // Move the predator based on its orientation
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

        // Increment the number of moves made by the predator
        predatorPrey.MOVES++;

        // Check if the predator has caught a prey
        if (predatorPrey.ground[predator.location.x][predator.location.y] == -1 && predatorPrey.MOVES < predatorPrey.MAX_MOVES) {
            predatorPrey.preys.killPreyAt(predator.location.x, predator.location.y);
            predatorPrey.TOTAL_PREY_KILLED++;
            predatorPrey.ground[predator.location.x][predator.location.y] = 1;//TODO
        }

        // Move all the alive preys randomly
        predatorPrey.preys.moveAllAlivePreysRandomly();

        if(predatorPrey.MOVES<predatorPrey.MAX_MOVES){
            if(++predatorPrey.PMOD >122)
                predatorPrey.PMOD=97;
            ground2[predator.location.x][predator.location.y]=predatorPrey.PMOD;
        }
    }
}
