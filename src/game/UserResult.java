
package game;

import java.util.LinkedHashMap;
import java.util.Map;
import model.WordResult;

/**
 *
 * @author carlos martinez
 */
public class UserResult 
{
    private int totalScore; 
    private final Map<String, WordResult> wordToResultMap = new LinkedHashMap<>(); 
    
    public int getTotalScore()//Generate a getter for member variable totalScore
    {
        return totalScore;
    }
    
    public void add(String word, WordResult result)
    {
        this.wordToResultMap.put(word, result);
        this.totalScore += result.getScore();
    }
    
    public WordResult get(String word)
    {
        return this.wordToResultMap.get(word);
    }
    
    public Map<String, WordResult> all ()
    {
        return this.wordToResultMap;
    }
    
    public boolean exist(String word)
    {
        return this.wordToResultMap.containsKey(word);
    }
    
    public int getWordCount()
    {
        return this.wordToResultMap.size();
    }
}
