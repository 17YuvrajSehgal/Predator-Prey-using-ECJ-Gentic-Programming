import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class PredatorPrey extends GPProblem implements SimpleProblemForm {
    public static final String P_DATA = "data";
    private final int BOARD_ROWS = 32;
    private final int BOARD_COLUMNS = 32;
    private final int[][] ground = new int[BOARD_ROWS][BOARD_COLUMNS];
    int HITS=0;
    int MAX_MOVES;
    int TOTAL_PREY=0;
    private int[] preyX;
    private int[] preyY;

    @Override
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);
        this.MAX_MOVES = state.parameters.getInt(base.push("MAX_MOVES"),null,1);

        //read the maximum moves allowed from the parameter file, and it should be a number greater than 0
        if(this.MAX_MOVES <=0)
            state.output.error("MAX_MOVES must be greater than 0.");

        //read the predator.trl file to get the defined trail of predators and preys
        readGrid(state,base);

    }

    private void readGrid(final EvolutionState state, final Parameter base) {
        //read the name of the file where we store the trail data from the parameter file under the field data.
        InputStream inputStream = state.parameters.getResource(base.push("file"), null);
        //start reading file
        if (inputStream != null){
            LineNumberReader lineNumberReader = null;
            int i=0,j=0;
            try {
                lineNumberReader = new LineNumberReader(new InputStreamReader(inputStream));

                for(i=0;i<this.BOARD_COLUMNS;i++){
                    String data = lineNumberReader.readLine();

                    //if we do not find the expected number of elements then we will stop the program
                    if(data == null) {
                        state.output.warning("The predator trail file ended prematurely");
                        break;
                    }



                    for(j=0;j<data.length();j++){
                        if(data.charAt(j)==' ')
                            this.ground[j][i]=1;
                        else if (data.charAt(j)=='#') {
                            this.ground[j][i] = -1;
                            this.TOTAL_PREY++;
                        }
                        else if(data.charAt(j)=='.'){
                            this.ground[j][i]=2;
                        }
                        else
                            state.output.error("Bad character '" + data.charAt(j) + "' on line number " + lineNumberReader.getLineNumber() + " of the predator trail file.");
                    }

                    //fill the remaining columsn
                    for(int k = j; k < this.BOARD_ROWS; k++) {
                        this.ground[k][i] = 1;
                    }
                }
                for (; i<BOARD_COLUMNS; i++){
                    for(i=0;i<this.BOARD_ROWS;i++){
                        this.ground[i][j]=1;
                    }
                }
            } catch (IOException e) {   //in case error occurred while reading file
                state.output.fatal("The predator.trl file could not be read due to an IOException:\n" + e);
            }finally {  //close the input reader if not null
                try {
                    if (lineNumberReader != null) {
                        lineNumberReader.close();
                    }
                } catch (IOException ignored) {
                }
            }

            state.output.exitIfErrors();
            this.preyX = new int[this.TOTAL_PREY];
            this.preyY = new int[this.TOTAL_PREY];
            int tmpf = 0;

            for(i = 0; i < this.ground.length; ++i) {
                for(i = 0; i < this.ground[0].length; ++i) {
                    if (this.ground[i][i] == -1) {
                        this.preyX[tmpf] = i;
                        this.preyY[tmpf] = i;
                        ++tmpf;
                    }
                }
            }
        }
        //if there was an issue while reading the predator.trl file, throw error.
        else {
            state.output.fatal("Error encountered while loading file or resource", base.push("file"), null);
        }
        printArray();
    }

        public void printArray() {
        for (int i = 0; i < this.BOARD_ROWS; i++) {
            for (int j = 0; j < this.BOARD_COLUMNS; j++) {
                System.out.print(ground[i][j] + "\t");
            }
            System.out.println();
        }
    }

    @Override
    public void evaluate(EvolutionState evolutionState, Individual individual, int i, int i1) {

    }
}
