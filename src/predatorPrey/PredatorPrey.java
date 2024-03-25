package predatorPrey;

import ec.EvolutionState;
import ec.Individual;
import ec.app.ant.Ant;
import ec.app.ant.func.EvalPrint;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PredatorPrey extends GPProblem implements SimpleProblemForm {
    public static final String P_DATA = "data";
    public int MOVES = 0;
    public Predator predator;
    public Preys preys;
    public int TOTAL_PREY_KILLED = 0;
    public int BOARD_ROWS;
    public int BOARD_COLUMNS;
    public int[][] ground;
    public int MAX_MOVES;
    public int MAX_PREYS;
    public int PMOD;
    private List<Point> initialLocations;

    @Override
    public Object clone() {
        PredatorPrey myobj = (PredatorPrey) (super.clone());
        myobj.ground = new int[this.ground.length][];

        for (int x = 0; x < this.ground.length; ++x) {
            myobj.ground[x] = (int[]) ((int[]) this.ground[x].clone());
        }

        return myobj;
    }


    @Override
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);
        if (!(input instanceof GPData))
            state.output.fatal("GPData class must subclass from " + GPData.class, base.push(P_DATA), null);

        this.MAX_MOVES = state.parameters.getInt(base.push("MAX_MOVES"), null, 1);
        this.BOARD_ROWS = state.parameters.getInt(base.push("BOARD_ROWS"), null, 1);
        this.BOARD_COLUMNS = state.parameters.getInt(base.push("BOARD_COLUMNS"), null, 1);
        this.MAX_PREYS = state.parameters.getInt(base.push("MAX_PREYS"), null, 1);

        this.ground = new int[BOARD_ROWS][BOARD_COLUMNS];
        this.initialLocations = new ArrayList<>(MAX_PREYS);


        //read the maximum moves allowed from the parameter file, and it should be a number greater than 0
        if (this.MAX_MOVES <= 0)
            state.output.error("MAX_MOVES must be greater than 0.");

        //read the predator.trl file to get the defined trail of predators and preys
        readGrid(state, base);

    }

    @Override
    public void evaluate(EvolutionState evolutionState, Individual individual, int threadNum, int i1) {
        if (!individual.evaluated) {
            this.TOTAL_PREY_KILLED = 0;
            this.predator.location.x = 0;
            this.predator.location.y = 0;
            this.predator.orientation = Orientation.RIGHT;
            this.MOVES = 0;

            while (this.MAX_MOVES > this.MOVES && this.MAX_PREYS > this.TOTAL_PREY_KILLED) {
                ((GPIndividual) individual).trees[0].child.eval(evolutionState, threadNum, this.input, this.stack, (GPIndividual) individual, this);
            }

            KozaFitness fitness = (KozaFitness) individual.fitness;
            fitness.setStandardizedFitness(evolutionState, this.MAX_PREYS - this.TOTAL_PREY_KILLED);
            fitness.hits = this.TOTAL_PREY_KILLED;
            individual.evaluated = true;
        }

        //System.out.println("before:");
        //printArray();
        restorePreyLocations();
        //printArray();
    }

    @Override
    public void describe(EvolutionState state, Individual individual, int subpopulation, int threadnum, int log) {
        state.output.println("\n\nBest Individual's Map\n\n", log);
        this.TOTAL_PREY_KILLED = 0;
        this.predator.location.x = 0;
        this.predator.location.y = 0;
        this.predator.orientation = Orientation.RIGHT;
        this.MOVES = 0;
        this.PMOD = 97;
        int[][] ground2 = new int[this.ground.length][];

        for(int j=0;j<this.ground.length;j++){
            ground2[j]=(this.ground[j].clone());
        }
        ground2[predator.location.x][predator.location.y]=this.PMOD++;
        this.MOVES++;

        while (this.MAX_MOVES > this.MOVES && this.MAX_PREYS > this.TOTAL_PREY_KILLED) {
            ((EvalPrint)(((GPIndividual)individual).trees[0].child)).evalPrint(state, threadnum,
                    this.input, this.stack, (GPIndividual)individual, this, ground2);

        }
        restorePreyLocations();//TODO

        for(int i=0;i<ground2.length;i++){
            for (int j=0;j<ground2[0].length;j++){
                switch (ground2[i][j]){
                    case -1:
                        state.output.print("#", log);
                        break;
                    case 1:
                        state.output.print(".", log);
                        break;
                    default:
                        state.output.print("" + (char)ground2[i][j], log);

                }
            }
            state.output.println("", log);
        }


    }

    private void restorePreyLocations() {
        for (int[] row : ground) {
            Arrays.fill(row, 1);
        }
        for (Point point : initialLocations) {
            ground[point.x][point.y] = -1;
        }
        preys.aliveAll();
    }

    public void printArray() {
        for (int i = 0; i < this.BOARD_ROWS; i++) {
            for (int j = 0; j < this.BOARD_COLUMNS; j++) {
                System.out.print(ground[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void readGrid(final EvolutionState state, final Parameter base) {
        //read the name of the file where we store the trail data from the parameter file under the field data.
        InputStream inputStream = state.parameters.getResource(base.push("file"), null);
        this.predator = new Predator(new Point(0, 0), this.ground, this.BOARD_ROWS, this.BOARD_COLUMNS);
        this.preys = new Preys(MAX_PREYS, ground);
        if (inputStream != null) {
            try (LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream))) {

                for (int i = 0; i < this.BOARD_ROWS; i++) {
                    String data = lineNumberReader.readLine();

                    //if we do not find the expected number of elements then we will stop the program
                    if (data == null) {
                        state.output.warning("The predator trail file ended prematurely");
                        break;
                    }

                    for (int j = 0; j < data.length(); j++) {
                        if (data.charAt(j) == ' ')
                            this.ground[i][j] = 1;
                        else if (data.charAt(j) == '#') {
                            this.ground[i][j] = -1;
                            preys.addPrey(new Prey(new Point(i, j), ground, BOARD_ROWS, BOARD_COLUMNS));
                            initialLocations.add(new Point(i, j));
                        } else
                            state.output.error("Bad character '" + data.charAt(j) + "' on line number " + lineNumberReader.getLineNumber() + " of the predator trail file.");
                    }
                }
            } catch (IOException e) {   //in case error occurred while reading file
                state.output.fatal("The predator.trl file could not be read due to an IOException:\n" + e);
            }  //close the input reader if not null
            state.output.exitIfErrors();
        }
        //if there was an issue while reading the predator.trl file, throw error.
        else {
            state.output.fatal("Error encountered while loading file or resource", base.push("file"), null);
        }
        System.out.println("initial Array:");
        printArray();

    }


}
