
package scraggle;

import dictionary.Dictionary; //Imports the Dictionary class and package
import game.Game; //Imports the game class and package
import userInterface.ScraggleUi; //Imports the UI class and package
/* @author Carlos Martinez */
public class Scraggle 
{
    public static void main(String[] args) 
    {
       Dictionary dictionary = new Dictionary(); 
       Game game = new Game(dictionary); // Create new game instance
       
       game.displayGrid(); //displays game with random letters
       ScraggleUi scraggleUi = new ScraggleUi(game);
    }
}
