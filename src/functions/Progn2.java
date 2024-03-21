package functions;

import ec.EvolutionState;
import ec.Problem;
import ec.app.ant.func.EvalPrint;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class Progn2 extends GPNode implements EvalPrint {
    @Override
    public String toString() {
        return "PROGN3";
    }

    @Override
    public int expectedChildren() {
        return 2;
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        this.children[0].eval(state, thread, input, stack, individual, problem);
        this.children[1].eval(state, thread, input, stack, individual, problem);
    }

    @Override
    public void evalPrint(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem, int[][] map2) {
        ((EvalPrint)this.children[0]).evalPrint(state, thread, input, stack, individual, problem, map2);
        ((EvalPrint)this.children[1]).evalPrint(state, thread, input, stack, individual, problem, map2);
    }
}
