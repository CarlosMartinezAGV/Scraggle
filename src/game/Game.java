
package game;

import model.GridUnit;
import model.GridPoint;
import dictionary.Dictionary;
import dictionary.Alphabet;

public class Game 
{
    private final GridUnit[][] grid;
    private static final int ROW = 4;
    private static final int COL = 4;
    private Dictionary dictionary;
    
    public Game(Dictionary dictionary)
    {
        this.grid = new GridUnit[ROW][COL];
        this.populateGrid();
        this.dictionary = dictionary;
    }
    
    public GridUnit[][] getGrid() //getter for grid
    {
        return grid;
    }
    
    public GridUnit getGridUnit(GridPoint point) //getter for gridunit
    {
        
        return grid[point.x][point.y];
    }
    
    public void populateGrid() //fills up grid w/ calls to Alphabet and Gridpoint classes
    {
        int i, j;
        
        for(i = 0; i < ROW; i++)
        {
            for(j = 0; j < COL; j++)
            {
                grid[i][j] = new GridUnit(Alphabet.newRandom(), new GridPoint(i,j));
            }
        }    
    }
    
    public void displayGrid() //displays game
    {
        int i, j;
        
        System.out.print("-------------------------\n");
        
        for(i = 0; i < ROW; i++)
        {
            System.out.print("|");
            for(j = 0; j < COL; j++)
            {
                System.out.print("  " + grid[i][j].getLetter() + "  |");
            }
            System.out.println("\n-------------------------");
        }        
    }
    
    public Dictionary getDictionary()
    {
        return dictionary;
    }
}
