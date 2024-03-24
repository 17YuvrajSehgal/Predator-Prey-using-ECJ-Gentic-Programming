package predatorPrey;

import ec.EvolutionState;
import ec.Individual;
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
import java.util.Arrays;

public class PredatorPrey extends GPProblem implements SimpleProblemForm {
    public static final String P_DATA = "data";
    public int MOVES = 0;
    public Predator predator;
    public Preys preys;
    public int TOTAL_PREY_KILLED = 0;
    public final int BOARD_ROWS = 32;
    public final int BOARD_COLUMNS = 32;
    public final int[][] ground = new int[BOARD_ROWS][BOARD_COLUMNS];
    public int MAX_MOVES;
    public int TOTAL_PREY = 0;
    public int MAX_PREYS = 89;
//    public int[] preyX; //initial x coordinates of prey
//    public int[] preyY; //initial y coordinates of prey

    @Override
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);
        this.MAX_MOVES = state.parameters.getInt(base.push("MAX_MOVES"), null, 1);

        //read the maximum moves allowed from the parameter file, and it should be a number greater than 0
        if (this.MAX_MOVES <= 0)
            state.output.error("MAX_MOVES must be greater than 0.");

        //read the predator.trl file to get the defined trail of predators and preys
        readGrid(state, base);

    }

    private void readGrid(final EvolutionState state, final Parameter base) {
        //read the name of the file where we store the trail data from the parameter file under the field data.
        InputStream inputStream = state.parameters.getResource(base.push("file"), null);
        this.predator = new Predator(new Point(0, 0), this.ground, this.BOARD_ROWS, this.BOARD_COLUMNS);
        this.preys = new Preys(MAX_PREYS);
        if (inputStream != null) {
            LineNumberReader lineNumberReader = null;
            try {
                lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream));

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
                            this.TOTAL_PREY++;
                        } else if (data.charAt(j) == '.') {
                            this.ground[i][j] = 2;
                        } else
                            state.output.error("Bad character '" + data.charAt(j) + "' on line number " + lineNumberReader.getLineNumber() + " of the predator trail file.");
                    }
                }
                //getPreyLocations();
            } catch (IOException e) {   //in case error occurred while reading file
                state.output.fatal("The predator.trl file could not be read due to an IOException:\n" + e);
            } finally {  //close the input reader if not null
                try {
                    if (lineNumberReader != null) {
                        lineNumberReader.close();
                    }
                } catch (IOException ignored) {
                }
            }
            state.output.exitIfErrors();


        }
        //if there was an issue while reading the predator.trl file, throw error.
        else {
            state.output.fatal("Error encountered while loading file or resource", base.push("file"), null);
        }
//        printArray();
//        System.out.println("x coordinates of preys:" + Arrays.toString(this.preyX));
//        System.out.println("x coordinates of preys:" + Arrays.toString(this.preyY));

    }

//    private void getPreyLocations() {
//        this.preyX = new int[this.TOTAL_PREY];
//        this.preyY = new int[this.TOTAL_PREY];
//        int counter = 0;
//
//        for (int i = 0; i < this.ground.length; i++) {
//            for (int j = 0; j < this.ground[0].length; j++) {
//                if (ground[i][j] == -1) {
//                    this.preyX[counter] = i;
//                    this.preyY[counter] = j;
//                    counter++;
//                }
//            }
//        }
//    }

    public void printArray() {
        for (int i = 0; i < this.BOARD_ROWS; i++) {
            for (int j = 0; j < this.BOARD_COLUMNS; j++) {
                System.out.print(ground[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void evaluate(EvolutionState evolutionState, Individual individual, int threadNum, int i1) {
        if (!individual.evaluated) {
            this.TOTAL_PREY_KILLED = 0;
            this.predator.location.x = 0;
            this.predator.location.y = 0;
            this.predator.orientation = Orientation.RIGHT;
            this.MOVES = 0;
        }

        while (this.MAX_MOVES > this.MOVES && this.TOTAL_PREY > this.TOTAL_PREY_KILLED) {
            ((GPIndividual) individual).trees[0].child.eval(evolutionState, threadNum, this.input, this.stack, (GPIndividual) individual, this);
        }

        KozaFitness fitness = (KozaFitness) individual.fitness;
        fitness.setStandardizedFitness(evolutionState, this.TOTAL_PREY - this.TOTAL_PREY_KILLED);
        fitness.hits = this.TOTAL_PREY_KILLED;
        individual.evaluated = true;

        //restore the preys to the initial positions
        preys.restorePreyLocations();


    }

//    private void restorePreys() {
//        for (int i = 0; i < TOTAL_PREY; i++) {
//            this.ground[this.preyX[i]][this.preyY[i]] = -1;
//        }
//    }
}
