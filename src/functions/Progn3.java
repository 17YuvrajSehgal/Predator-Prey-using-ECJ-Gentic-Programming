/**
 * Represents a node in a genetic programming tree that executes its three children sequentially.
 */
package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.app.ant.func.EvalPrint;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class Progn3 extends GPNode implements EvalPrint {

    /**
     * Returns a string representation of the node.
     * @return The string representation of the node.
     */
    @Override
    public String toString() {
        return "PROGN3";
    }

    /**
     * Specifies the number of children expected for this node.
     * @return The expected number of children (3 in this case).
     */
    @Override
    public int expectedChildren() {
        return 3;
    }

    /**
     * Evaluates the node's logic by sequentially evaluating its three children.
     * @param state The current evolution state.
     * @param thread The thread number.
     * @param input The GP data.
     * @param stack The ADF stack.
     * @param individual The GP individual.
     * @param problem The problem instance.
     */
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        this.children[0].eval(state, thread, input, stack, individual, problem);
        this.children[1].eval(state, thread, input, stack, individual, problem);
        this.children[2].eval(state, thread, input, stack, individual, problem);
    }

    /**
     * Evaluates the node's logic and prints debug information.
     * Executes the logic of the first child, then the second child, and finally the third child.
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
        ((EvalPrint)this.children[0]).evalPrint(state, thread, input, stack, individual, problem, map2);
        ((EvalPrint)this.children[1]).evalPrint(state, thread, input, stack, individual, problem, map2);
        ((EvalPrint)this.children[2]).evalPrint(state, thread, input, stack, individual, problem, map2);
    }
}
