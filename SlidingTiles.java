// Java wrapper dependencies
import java.io.*;
import java.util.*;

// JavaFx dependencies
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;


public class SlidingTiles extends Application {

    /* * * * * * * fields * * * * * * * */

    // current board state
    private int[][] board;

    // random int in [0,3] that represents which row the new element will be added to
    private int randRow;
    // random int in [0,3] that represents which column the new element will be appended to
    private int randCol;

    private int numRows;
    private int numCols;

    /** Constructor */
    public SlidingTiles() {
        // randomly sets one box to a 1
        randRow = (int) (Math.random() * 4);
        randCol = (int) (Math.random() * 4);

        numRows = 4;
        numCols = 4;

        // creates 4x4 board by default
        board = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;
            }
        }

        // randomly assigns a square to be one
        board[randRow][randCol] = 1;

    }

    /**
     * Overrides the start method of Application to create the GUI
     * @param primaryStage  the JavaFX main window
     */
    @Override
    public void start (Stage primaryStage) throws Exception {
    
        // Creates gridpane object
        GridPane gridpane = new GridPane();
        Button[][] buttons = new Button[numRows][numCols];
        
        // resizes buttons and creates buttons
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                buttons[i][j] = new Button();
                gridpane.add(buttons[i][j], j, i);
                buttons[i][j].setMinSize(75,75);
            }
        }
        updateButtons(buttons);

        /* * * * * * Left slide * * * * * */

        for (int i = 1; i < numRows - 1; i++) {
            buttons[i][0].setOnAction(actionEvent -> {
 
                // handle method body
                int shiftIndicator = 0;

                for (int row = 0; row < board.length; row++) {
                    shiftIndicator += shift(board[row]);
                }          
           
                if (shiftIndicator > 0)
                    randOne(board);  // adds a random one to the board

                updateButtons(buttons);
                System.out.println(Arrays.deepToString(board)); // for debugging


            });
        }

       /* * * * * * Right slide * * * * * */

        for (int i = 1; i < numRows - 1; i++) {
             buttons[i][numCols - 1].setOnAction(actionEvent -> {
            
                // handle method body
           
                int shiftIndicator = 0; // indicates if movement took place
            
                for (int row = 0; row < board.length; row++) {
                     // reverses the order of the row elements before sliding
                    reverseArray(board[row]);
                 
                    shiftIndicator += shift(board[row]);
                 
                    // un-reverses (?) the order of the row elements after sliding
                    reverseArray(board[row]);
                }          
            
                if (shiftIndicator > 0)
                    randOne(board);  // adds a random one to the board

            
                updateButtons(buttons);
                System.out.println(Arrays.deepToString(board)); // for debugging

            });
        }

        /* * * * * * Up slide * * * * * * */

        for (int i = 1; i < numCols - 1; i++) {
            buttons[0][i].setOnAction(actionEvent -> {
            
                // handle method body
             
                int shiftIndicator = 0; // indicates if movement happened
            
                // transposes the board so that we 'slide' the elements of the columns up
                transpose(board);
            
                // slides all the elements as far as possible
                for (int row = 0; row < board.length; row++) {
                     shiftIndicator += shift(board[row]);
                }       
            
                // transposes the board again after sliding to revert to original orientation
                transpose(board);
            
                if (shiftIndicator > 0)
                    randOne(board);  // adds a random one to the board

            
                updateButtons(buttons);
                System.out.println(Arrays.deepToString(board)); // for debugging

            });
       
        }

        /* * * * * * Down slide * * * * * */
        
        for (int i = 1; i < numCols - 1; i++) {
            buttons[numRows - 1][i].setOnAction(actionEvent -> {
            
                // handle method body
             
                int shiftIndicator = 0; // indicates if movement happened
            
                // transposes the board so that we 'slide' the elements of the columns up
                transpose(board);
            
                // slides all the elements as far as possible
                for (int row = 0; row < board.length; row++) {
                    // reverses the order of the row elements before sliding
                    reverseArray(board[row]);
                 
                    shiftIndicator += shift(board[row]);
                 
                    // un-reverses (?) the order of the row elements after sliding
                    reverseArray(board[row]);
                }       
            
                // transposes the board again after sliding to revert to original orientation
                transpose(board);
            
                if (shiftIndicator > 0)
                    randOne(board);  // adds a random one to the board

            
                updateButtons(buttons);
                System.out.println(Arrays.deepToString(board)); // for debugging

            });
        }        
       
        
        /* * * * * * NW slide * * * * * * */

        /* * * * * * NE slide * * * * * * */

        /* * * * * * SE slide * * * * * * */

        /* * * * * * SW slide * * * * * * */


        // Creates scene of application
        Scene scene = new Scene(gridpane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("2048 clone");
        
        // shows the scene
        primaryStage.show();    


        // tests the initial creation of the board
        System.out.println(Arrays.deepToString(board));

    }  

    /** 
     * Updates the values of the buttons per the board state 
     *
     * @param buttons  the buttons we wish to update
     */
    public void updateButtons(Button[][] buttons) {

        // Assigns initial values to the starting board
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                // sets square to be blank if the number is 0
                if (board[row][col] == 0) {
                    buttons[row][col].setText(" ");
                }
                else 
                    buttons[row][col].setText(String.valueOf(board[row][col]));
            }
        }
    }
    
    /**
     * Reverses the order of an array
     * 
     * @param array  the array we wish to reverse the order of
     */
    public void reverseArray(int[] array) {
         // temporary value placeholder
         int temp;
         
         int n = array.length;
         
         // swaps first value with last, second with second-to-last, etc
         for (int i = 0; i < n / 2; i++) {
              temp = array[i];
              array[i] = array[n - i - 1];
              array[n - i - 1] = temp;
         }
    }
    
    /**
     * Transposes a 2d array (or matrix, if you will)
     * 
     * @param matrix  the matrix we wish to transpose
     */
    public void transpose(int[][] matrix) {
         // temporary value placeholder
         int temp;
         
         // nested loops traverse along the main diagonal to avoid double swapping elements
         for (int i = 0; i < matrix.length; i++ ) {
              for (int j = 0; j < i; j++) {
                   temp = matrix[i][j];
                   matrix[i][j] = matrix[j][i];
                   matrix[j][i] = temp;
              }
         }
    }
    
  

    /**
     * Shifts the elements of an array in a given direction
     *
     * @param array  the array we wish to shift, where the first element is in the direction we wish to slide
     * @return didShift  indicates whether or not a shift/merge actually took place
     */
    public int shift(int[] array) {
            int travPtr = 0; // pointer that traverses the array
            int didShift = 0; // is zero if no shift occurs; is > 0 otherwise

                for (int col = 1; col < array.length; col++) {
                    travPtr = col;

                    // shifts current element as far to the left as it can
                    while (array[travPtr - 1] == 0 && array[travPtr] != 0) {
                        array[travPtr - 1] = array[travPtr];
                        array[travPtr] = 0;
                        travPtr--;
                            
                        didShift++; // a shift occurred

                        if (travPtr < 1)
                            break;
                    }
                    
                    // merges current element with the element before it if possible
                    if (travPtr > 0 && array[travPtr - 1] == array[travPtr]) {
                        array[travPtr - 1] *= 2;
                        array[travPtr] = 0;

                        didShift++; // a merge occurred
                    }
                }
                
            return didShift;

    }

    /**
     * Randomly assigns a square with a zero entry a one
     * 
     * @param boardState  the board we wish to add a one to
     */
    public void randOne(int[][] boardState) {
    
        // randomly assigns a 0 square a one
        LinkedList<Integer[]> zeroSquares = new LinkedList<Integer[]>(); // keeps track of the buttons with zeros
           
        // iterates through the elements and adds its corresponding button to the zeroButtons array if its value is 0
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (boardState[i][j] == 0)
                    zeroSquares.add(new Integer[]{Integer.valueOf(i), Integer.valueOf(j)});  // stores index of button
            }
        }

        // randomly chooses a square from the list of zero buttons
        int randIndex = (int) (Math.random() * zeroSquares.size()); 

        boardState[(zeroSquares.get(randIndex))[0].intValue()][(zeroSquares.get(randIndex))[1].intValue()] = 1;
                
        zeroSquares.clear();


    }

    /** main method */
    public static void main (String[] args) {
        launch(args);
    }
}
