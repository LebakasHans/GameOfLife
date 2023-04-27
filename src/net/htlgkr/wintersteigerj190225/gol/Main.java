package net.htlgkr.wintersteigerj190225.gol;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        new GameOfLife(1000
                , 1500
                , 2000
                , 20
                , Integer.MAX_VALUE)
                .startSimulation();
    }
}