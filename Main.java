import java.io.*;

import static java.awt.SystemColor.text;
import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        /*Identify the number of rows*/
        int rows = 8;
        /*Identify the number of columns*/
        int columns = 8;
        /*Creates a maze that is not solvable, i.e. it is made of squares and no openings.*/
        Maze maze = new Maze(rows, columns);
        /*Initializes a 2d array of blocks that consists of rows and columns.*/
        maze.initializeArray();
        /* Makes a path so that the maze is solvable using Kruskal's Algorithm.*/
        maze.arrangePath();
        out.println(maze);
    }
}