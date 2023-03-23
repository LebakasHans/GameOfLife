package net.htlgkr.wintersteigerj190225.gol;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        new GameOfLife(new SimpleGraphicsLibrary(
             900, 900, Color.WHITE
        ), 810000).startSimulation();
    }
}