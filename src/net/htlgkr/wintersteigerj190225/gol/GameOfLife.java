package net.htlgkr.wintersteigerj190225.gol;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameOfLife {
    private final int HEIGHT;
    private final int WIDTH;

    private static final Color COLOR_ALIVE = Color.black;
    private static final Color COLOR_DEAD = Color.white;
    private static final int MAX_LOOPS = 900;
    private SimpleGraphicsLibrary window;
    private int startingCellsCount;
    private boolean[][] field;

    public GameOfLife(SimpleGraphicsLibrary window, int startingCellsCount) {
        this.window = window;
        HEIGHT = window.getHeight();
        WIDTH = window.getWidth();
        this.startingCellsCount = startingCellsCount;
        field = new boolean[this.window.getWidth()][this.window.getHeight()];
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

    private void setCells() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j]){
                    window.setPixel(i,j, COLOR_ALIVE);
                }else {
                    window.setPixel(i,j, COLOR_DEAD);
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
        int totalPixels = window.getWidth() * window.getHeight();
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
        boolean upperBound = j-1 < HEIGHT && j-1 > 0;
        boolean rightBound = i+1 < WIDTH;
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