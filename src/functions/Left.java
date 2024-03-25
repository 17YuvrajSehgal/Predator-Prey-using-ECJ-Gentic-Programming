/**
 * Represents a node in a genetic programming tree that turns the predator left.
 * Inherits from GPNode and implements EvalPrint interface.
 */
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

public class Left extends GPNode implements EvalPrint {

    /**
     * Returns a string representation of the node.
     * @return The string representation of the node ("left").
     */
    @Override
    public String toString() {
        return "left";
    }

    /**
     * Specifies the number of children expected for this node (0 in this case).
     * @return The expected number of children (0 in this case).
     */
    @Override
    public int expectedChildren() {
        return 0;
    }

    /**
     * Evaluates the node's logic.
     * Changes the predator's orientation to the left.
     * Increments the count of moves performed by the predator.
     * @param evolutionState The current evolution state.
     * @param thread The thread number.
     * @param gpData The GP data.
     * @param adfStack The ADF stack.
     * @param gpIndividual The GP individual.
     * @param problem The problem instance.
     */
    @Override
    public void eval(EvolutionState evolutionState, int thread, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        PredatorPrey predatorPrey = (PredatorPrey) problem;
        Predator predator = predatorPrey.predator;

        switch (predator.orientation){
            case UP://if predator is looking up, turn it left
                predator.orientation=Orientation.LEFT;
                break;
            case LEFT://if predator is looking left, turn it down
                predator.orientation=Orientation.DOWN;
                break;
            case DOWN://if predator is looking down, turn it right
                predator.orientation=Orientation.RIGHT;
                break;
            case RIGHT://if predator is looking right, turn it up
                predator.orientation=Orientation.UP;
                break;
            default:
                evolutionState.output.fatal("Got invalid orientation request in Left-> (" + predator.orientation + ")");
        }
        predatorPrey.MOVES++;
    }

    /**
     * Evaluates the node's logic and prints debug information.
     * Calls the eval method to perform the left turn logic.
     * @param state The current evolution state.
     * @param thread The thread number.
     * @param input The GP data.
     * @param stack The ADF stack.
     * @param individual The GP individual.
     * @param problem The problem instance.
     * @param map2 Two-dimensional array holding integer data.
     */
    @Override
    public void evalPrint(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem, int[][] map2) {
        this.eval(state, thread, input, stack, individual, problem);
    }
}
