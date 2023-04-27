package net.htlgkr.wintersteigerj190225.gol;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameOfLife {
    private final int HEIGHT;
    private final int WIDTH;
    private final int CELL_SIZE;
    private final int MAX_LOOPS;

    private static final Color COLOR_ALIVE = Color.black;
    private static final Color COLOR_DEAD = Color.white;

    private SimpleGraphicsLibrary window;
    private int startingCellsCount;
    private boolean[][] field;

    public GameOfLife(int height, int width, int startingCellsCount, int cellSize, int maxLoops) {
        CELL_SIZE = cellSize;
        this.startingCellsCount = startingCellsCount;
        adjustField(height, width);
        WIDTH = field.length * CELL_SIZE;
        HEIGHT = field[0].length * CELL_SIZE;
        this.window = new SimpleGraphicsLibrary(WIDTH, HEIGHT, Color.white);
        MAX_LOOPS = maxLoops;
    }

    private void adjustField(int height, int width) {
        int rows = width;
        while (rows % CELL_SIZE != 0){
            rows++;
        }
        rows = rows/ CELL_SIZE;
        int columns = height;
        while (columns % CELL_SIZE != 0){
            columns++;
        }
        columns = columns/ CELL_SIZE;
        field = new boolean[rows][columns];
    }

    public void startSimulation(){
        window.setVisible(true);
        setRandomCells();

        startLoop();
    }

    private void startLoop() {
        for (int i = 0; i < MAX_LOOPS; i++) {
            boolean[][] nextGen = generateNextGeneration();
            if (Arrays.deepEquals(nextGen, field)){
                break;
            }

            field = nextGen;
            setCells();
        }

        System.out.println("Game ended");
    }

    private void setCells(){
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[row].length; col++) {
                int rowStartingPoint = row * CELL_SIZE;
                int columnStartingPoint = col * CELL_SIZE;
                Color color = field[row][col] ? COLOR_ALIVE : COLOR_DEAD;

                for (int i = rowStartingPoint; i < rowStartingPoint + CELL_SIZE; i++) {
                    for (int j = columnStartingPoint; j < columnStartingPoint + CELL_SIZE; j++) {
                        window.setPixel(i,j, color);
                    }
                }
            }
        }
    }

    private void setRandomCells() {
        ArrayList<Integer> randomIndex = generateRandomIndexes();
        for (int i = 0; i < startingCellsCount; i++) {
            int[] coordinates = indexToCoordinate(randomIndex.get(i));
            int x = coordinates[0];
            int y = coordinates[1];
            field[x][y] = true;
        }
        setCells();
    }

    private ArrayList<Integer> generateRandomIndexes() {
        ArrayList<Integer> randomIndex = new ArrayList<>();
        int totalPixels = field.length * field[0].length;
        for(int i = 0; i < totalPixels; i++) {
            randomIndex.add(i);
        }
        Collections.shuffle(randomIndex);
        return randomIndex;
    }

    private int[] indexToCoordinate(int index) {
        int length = field[0].length;
        int row = index / length;
        int col = index % length;
        return new int[]{row, col};
    }

    private boolean[][] generateNextGeneration(){
        boolean[][] nextGeneration = new boolean[field.length][field[0].length];
        for (int i = 0; i < field.length-1; i++) {
            for (int j = 0; j < field[i].length-1; j++) {
                int neighbourCount = getNeighbourCount(i, j);
                if (field[i][j]){
                    if (neighbourCount > 3 || neighbourCount < 2){
                        nextGeneration[i][j] = false;
                    }else{
                        nextGeneration[i][j] = true;
                    }
                }else{
                    if (neighbourCount == 3){
                        nextGeneration[i][j] = true;
                    }
                }
            }
        }
        return nextGeneration;
    }

    private int getNeighbourCount(int i, int j) {
        int neighbourCount = 0;
        boolean upperBound = j-1 < field.length && j-1 > 0;
        boolean rightBound = i+1 < field[0].length;
        boolean lowerBound = j+1 >= 0;
        boolean leftBound = i-1 >= 0;

        //check above left
        if (upperBound && leftBound){
            neighbourCount += field[i-1][j-1] ? 1:0;
        }

        //check above
        if (upperBound){
            neighbourCount += field[i][j-1] ? 1:0;
        }

        //check above right
        if (upperBound && rightBound){
            neighbourCount += field[i+1][j-1] ? 1:0;
        }

        //check right
        if (rightBound){
            neighbourCount += field[i+1][j] ? 1:0;
        }

        //check lower right
        if (lowerBound && rightBound){
            neighbourCount += field[i+1][j+1] ? 1:0;
        }

        //check lower
        if (lowerBound){
            neighbourCount += field[i][j+1] ? 1:0;
        }

        //check lower left
        if (lowerBound && leftBound){
            neighbourCount += field[i-1][j+1] ? 1:0;
        }

        //check left
        if (lowerBound && leftBound){
            neighbourCount += field[i-1][j] ? 1:0;
        }

        return neighbourCount;
    }
}