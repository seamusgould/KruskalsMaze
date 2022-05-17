import java.util.ArrayList;
import java.util.Random;

public class Square {
    ArrayList<String> edges = new ArrayList<String>();
    int row = 1;
    int column = 1;
    int identifier;

    Square(int row, int column, int identifier){
        if (column != 0){
            edges.add("W");
        }
        if (row != 0){
            edges.add("N");
        }
        this.row = row;
        this.column = column;
        this.identifier = identifier;
    }

    public String getEdge() {
        if (edges.size() == 0){
            return "bogus";
        }
        Random random = new Random();
        int rand = random.nextInt(edges.size());
        String edge = edges.get(rand);
        return edge;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && object instanceof Square)
        {
            isEqual = (this.identifier == ((Square) object).identifier);
        }
        return isEqual;
    }
}
