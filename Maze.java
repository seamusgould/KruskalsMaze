import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Maze {
    int rows;
    int columns;
    Square[][] maze;

    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.maze = new Square[rows][columns];
    }

    public String toString() {
        String s = "";
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < columns; i++) {
                if (maze[j][i].edges.contains("N") || j == 0) {
                    s += "+---";
                } else {
                    s += "+   ";
                }
            }

            s += "+\n";

            for (int i = 0; i < columns; i++) {
                if (maze[j][i].edges.contains("W") || i == 0) {
                    s += "|   ";
                } else {
                    s += "    ";
                }
            }
            s += "|\n";
        }

        for (int i = 0; i < columns; i++){
            s += "+---";
        }
        s = s + "+";
        s = s.replaceFirst(Pattern.quote("|"), " ");
        StringBuilder maze= new StringBuilder();
        maze.append(s);
        maze = maze.reverse();
        s = String.valueOf(maze);
        s = s.replaceFirst(Pattern.quote("|"), " ");
        StringBuilder maze1= new StringBuilder();
        maze1.append(s);
        maze1 = maze1.reverse();
        return String.valueOf(maze1);
    }

    /*
    * Initializes all squares of the array so that they all have fields for edges and identifiers.
    */
    public void initializeArray() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                maze[i][j] = new Square(i, j, (rows*i) + j);
            }
        }
    }

    /*
     * Makes an ArrayList of Arraylist that will represent all possible paths.  A wall will be removed after each
     * iteration, and this will be done until the conclusion of the maze generation.  A random square and edge will be
     * removed such that the square and its edge exist and do not form a cycle.  After this, I will update my set to
     * consider whether the conditions are satisfied.  The sets will be concatenated if there are unions after every
     * barrier removed.
     */

    public void arrangePath() {
        ArrayList<ArrayList<Square>> sets = new ArrayList<ArrayList<Square>>();
        while (notDone(sets)) {
            Square square = getRandomSquare();
            String edge = square.getEdge();
            while ((square.edges.size() == 0) || (cyclical(square, edge, sets)) || (edge.equals("bogus"))) {
                    square = getRandomSquare();
                    edge = square.getEdge();
                }
            square.edges.remove(edge);
            sets = updateSets(sets, square, edge);
            sets = concatSets(sets);
        }
    }


    /*
     * Checks to see if the maze is cyclical.  If it is, then a boolean will return true, and the edge cannot be used.
     */
    private boolean cyclical(Square square, String edge, ArrayList<ArrayList<Square>> sets) {
        /*Check if it is cyclical.*/
        boolean cyclical = false;
        Square val_uno = square;
        Square val_dos;
        if (edge.equals("W")){
            val_dos = maze[square.row][square.column - 1];
        }
        else {
            val_dos = maze[square.row - 1][square.column];
            }

        for(ArrayList<Square> list: sets) {
            if (list.contains(val_uno) && list.contains(val_dos)){
                cyclical = true;
            }
        }
        return cyclical;
    }

    private ArrayList<ArrayList<Square>> concatSets(ArrayList<ArrayList<Square>> sets) {
        for (int i = 0; i < sets.size(); i++) {
            ArrayList<Square> set_uno = sets.get(i);
            for (int j = i + 1; j < sets.size(); j++){
                ArrayList<Square> set_dos = sets.get(j);

                ArrayList<Square> set_tres = (ArrayList<Square>) set_uno.stream()
                        .filter(set_dos :: contains)
                        .collect(Collectors.toList());

                if (set_tres.size() != 0){
                    ArrayList<Square> union = set_uno;

                    union.addAll(set_dos);

                    union = (ArrayList<Square>) set_uno.stream()
                            .distinct()
                            .collect(Collectors.toList());

                    sets.remove(sets.get(j));
                    sets.remove(sets.get(i));
                    sets.add(union);
                    concatSets(sets);
                }
            }
        }
        return sets;
    }

    public ArrayList<ArrayList<Square>> updateSets(ArrayList<ArrayList<Square>> sets, Square square, String edge) {
        ArrayList<Square> newarray= new ArrayList<Square>();
        if (edge.equals("N")){
            newarray.add(square);
            newarray.add(maze[square.row - 1][square.column]);
        }
        else {
            newarray.add(square);
            newarray.add(maze[square.row][square.column - 1]);
        }
        sets.add(newarray);
        return sets;
    }

    private Square getRandomSquare() {
        Random rand = new Random();
        int rand_row = rand.nextInt(rows);
        int rand_col = rand.nextInt(columns);
        return maze[rand_row][rand_col];
    }

    public boolean notDone(ArrayList<ArrayList<Square>> lists){
        for (ArrayList<Square> list: lists){
            if (list.contains(maze[0][0]) && list.contains(maze[rows - 1][columns - 1])){
                return false;
            }
        }
        return true;
    }
}

