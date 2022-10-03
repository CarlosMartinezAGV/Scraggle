package dictionary;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
        
public class Dictionary //Dictionary class
{
    private static final String WORDS_FILE = "words.txt"; //file name to read
    private final Trie trie;
    
    public Dictionary()//Dictionary method
    {
        Scanner inputFile;
        String word;
        
        try //try,catch
        {
             this.trie = new Trie();
             URL url = getClass().getResource(WORDS_FILE);
             File file = new File(url.toURI());//helps for mac instead of File(WORDS_FILE)
             
             inputFile = new Scanner(file);
             
             if(inputFile == null)
                throw new IOException("Invalid URL specified");
             
             while(inputFile.hasNext())//till there are no more words in the txt file
             {
                 word = inputFile.next();
                 word = word.trim().toLowerCase();
                 trie.insert(word);
             }
             
             System.out.println("Loaded all words into the trie");
        }
        catch(IOException | URISyntaxException e)
        {
            System.out.println("Error while loading words into the try");
            throw new RuntimeException(e);
        }
    }
    
    public int search(String word) //searches the trie for the word
    {
        return this.trie.search(word);
    }
    
    public boolean prefix(String word) //searches the trie for the prefix
    {
        return this.trie.prefix(word);
    }
}
