package userInterface;

import game.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

public class ScraggleUi 
{
    private  JFrame jframe;   
    private  JMenuBar jMenuBar;  
    private  JMenu jmenu; 
    private  JMenuItem exit;   
    private  JMenuItem newGame;    
    private  JPanel jpanel;   
    private  JButton[][] jbutton2d;   
    private  JPanel dicePanel;    
    private  JScrollPane scrollPane;
    private  JTextArea rightText;
    private  JLabel currentWordLabel;   
    private  JButton submitButton;  
    private  JPanel rightPanel;   
    private  JLabel scoreLabel;   
    private  JButton shakeDice;  
    private  JLabel timerLabel;   
    private  Game game;
    
    private int playerScore = 0;
    private int minutes = 3;
    private int seconds = 00;
    private Timer timer;
    private ArrayList<String> foundWords;
    private ResetGameListener resetGameListener;
    
    private final int GRID = 4;
    
    private final static int MAX_INDEX = 3;
    private final static int MIN_INDEX = 0;
            
    public ScraggleUi(Game game)
    {
        this.game = game;
        initObjects();
        initComponents();
    }
    
    private void initObjects()
    {
        resetGameListener = new ResetGameListener();
        foundWords = new ArrayList();
    }
    
    private void initComponents()
    {
        jframe = new JFrame("Scraggle");
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(new Dimension(700, 600));          
  
        BorderLayout borderLayout = new BorderLayout();
        jframe.setLayout(borderLayout);
        
        //jmenubar
        jMenuBar = new JMenuBar();
        jmenu = new JMenu("Scraggle");
        jmenu.setMnemonic(KeyEvent.VK_A);
        
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(resetGameListener);
        exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());
        
        jframe.setJMenuBar(jMenuBar);
        
        jmenu.add(newGame);
        jmenu.add(exit);
        
        jMenuBar.add(jmenu);
        
        //A JPanel should be created to hold the current word being created by the user
        jpanel = new JPanel(new FlowLayout());
        
        jpanel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        jpanel.setPreferredSize(new Dimension(300,85));
        
        currentWordLabel = new JLabel();
        currentWordLabel.setPreferredSize(new Dimension(250,50));
        currentWordLabel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        
        submitButton = new JButton("Submit Word");
        submitButton.setPreferredSize(new Dimension(200,50));
        submitButton.addActionListener(new SubmitListener());
        
        scoreLabel = new JLabel();
        scoreLabel.setPreferredSize(new Dimension(100,50));
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
         
        jpanel.add(currentWordLabel);
        jpanel.add(submitButton);
        jpanel.add(scoreLabel);
        
        //dice panel
        setupScragglePanel();
        
        //right panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3,1));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        
        rightText = new JTextArea();
        rightText.setPreferredSize(new Dimension(100,25));
        
        scrollPane = new JScrollPane(rightText);
        scrollPane.setPreferredSize(new Dimension(225,50)); 
        scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        rightPanel.add(scrollPane);
        
        timerLabel = new JLabel(MAX_INDEX + ":" + MIN_INDEX + MIN_INDEX, SwingConstants.CENTER);
        timerLabel.setBorder(BorderFactory.createTitledBorder("Time Left"));
        timerLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        
        rightPanel.add(timerLabel);
        
        //SHAKE DICE button
        
        shakeDice = new JButton("Shake Dice");
        shakeDice.addActionListener(resetGameListener);
        rightPanel.add(shakeDice);
    
        //timer
        setupTimer();
       
        //add panels to frames
        jframe.add(rightPanel, BorderLayout.EAST);
        jframe.add(dicePanel, BorderLayout.CENTER);
        jframe.add(jpanel,BorderLayout.SOUTH);
        jframe.setVisible(true);
    }
    
    private void setupScragglePanel()
    {
        dicePanel = new JPanel(new GridLayout(4,4));
        dicePanel.setBorder(BorderFactory.createTitledBorder("Scraggle Board"));
        
        jbutton2d = new JButton[GRID][GRID];
        
        for(int row = 0; row < GRID; row++)
        {
            for(int col = 0; col < GRID; col++)
            {
                URL imgPath = getClass().getResource(game.getGrid()[row][col].getImgPath());
                ImageIcon icon = new ImageIcon(imgPath);
                jbutton2d[row][col] = new JButton(icon);
                jbutton2d[row][col].putClientProperty("row",row);
                jbutton2d[row][col].putClientProperty("col",col);
                jbutton2d[row][col].putClientProperty("letter",game.getGrid()[row][col].getLetter());
                
                TileListener tileListener = new TileListener();
                LetterListener letterListener = new LetterListener();
                
                jbutton2d[row][col].addActionListener(tileListener);
                jbutton2d[row][col].addActionListener(letterListener);
                dicePanel.add(jbutton2d[row][col]);
            }
        }
    }
    
    private void setupTimer()
    {
        timer = new Timer(1000, new TimerListener());
        timer.start();
    }
    
    private void updateTextArea(String data)
    {
        rightText.setText(rightText.getText() + "\n" + data);
        rightText.setCaretPosition(rightText.getDocument().getLength());
    }
    
    private class ExitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            int response = JOptionPane.showConfirmDialog(jframe, "Confirm to exit Scraggle?", "Exit?", JOptionPane.YES_NO_OPTION);
        
            if(response == JOptionPane.YES_OPTION);
            {
                System.exit(0);
            }
        }
    }
    
    private class ResetGameListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            playerScore = 0;
            game.populateGrid();
            
            jframe.remove(dicePanel);
            dicePanel.removeAll();
            setupScragglePanel();
            dicePanel.revalidate();
            dicePanel.repaint();
            
            jframe.add(dicePanel);
            rightText.setText("");
            
            scoreLabel.setText("0");
            currentWordLabel.setText("");
            timerLabel.setText("3:00");
            
            foundWords.removeAll(foundWords);
            timer.stop();
            minutes = 3;
            seconds = 0;
            timer.start();
        }
    }
    
    private class SubmitListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            int wordScore = game.getDictionary().search(currentWordLabel.getText().toLowerCase());
        
            //if word has been used
            if(foundWords.contains(currentWordLabel.getText().toLowerCase()))
            {
                JOptionPane.showMessageDialog(jframe, "Word found already");
            }
            else if(wordScore > 0)
            {
                
                updateTextArea(currentWordLabel.getText());
                foundWords.add(currentWordLabel.getText().toLowerCase());
                playerScore += wordScore;
                scoreLabel.setText(String.valueOf(playerScore));
            }
            else
            {
                JOptionPane.showMessageDialog(jframe, "Not a valid word");
            }
            
            currentWordLabel.setText("");
            
            for(int i = 0; i < GRID; i++)
            {
                for(int j = 0; j < GRID; j++)
                {
                    jbutton2d[i][j].setEnabled(true);
                }
            }
        }
    }
    
    private class TileListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton)ae.getSource();
                
                int row = (int)button.getClientProperty("row");
                int col = (int)button.getClientProperty("col");
                
                for(int i = 0; i < GRID; i++)
                {
                    for(int j = 0; j < GRID; j++)
                    {
                        jbutton2d[i][j].setEnabled(false);
                    }
                }
                
                //enable jbuttons based on logic
                
                if(row == 0 && col == 0)
                {
                    //enable surroundings 
                    // row + 1 and current col
                    jbutton2d[row + 1][col].setEnabled(true);
                    jbutton2d[row + 1][col + 1].setEnabled(true);
                    jbutton2d[row][col + 1].setEnabled(true);
                }
                else if(row == 0 && col == 3)
                {
                    jbutton2d[row][col - 1].setEnabled(true);
                    jbutton2d[row + 1][col].setEnabled(true);
                    jbutton2d[row + 1][col - 1].setEnabled(true);
                }
                
                else if(row == 3 && col == 0)
                {
                    jbutton2d[row - 1][col].setEnabled(true);
                    jbutton2d[row - 1][col + 1].setEnabled(true);
                    jbutton2d[row][col + 1].setEnabled(true);
                }
                
                else if(row == 3 && col == 3)
                {
                    jbutton2d[row - 1][col].setEnabled(true);
                    jbutton2d[row - 1][col - 1].setEnabled(true);
                    jbutton2d[row][col - 1].setEnabled(true);
                    
                }
                else if(row == 0 && col > 0)
                {
                    jbutton2d[row + 1][col].setEnabled(true);
                    jbutton2d[row + 1][col + 1].setEnabled(true);
                    jbutton2d[row + 1][col - 1].setEnabled(true);
                    
                    jbutton2d[row][col - 1].setEnabled(true);
                    jbutton2d[row][col + 1].setEnabled(true);
                }
                
                else if(row > 0 && col == 0)
                {
                    jbutton2d[row - 1][col + 1].setEnabled(true);
                    jbutton2d[row][col + 1].setEnabled(true);
                    jbutton2d[row + 1][col + 1].setEnabled(true);
                    
                    jbutton2d[row - 1][col].setEnabled(true);
                    jbutton2d[row + 1][col].setEnabled(true);
                }
                
                else if(row == 3 && col > 0)
                {
                    jbutton2d[row - 1][col].setEnabled(true);
                    jbutton2d[row - 1][col + 1].setEnabled(true);
                    jbutton2d[row - 1][col - 1].setEnabled(true);
                    
                    jbutton2d[row][col - 1].setEnabled(true);
                    jbutton2d[row][col + 1].setEnabled(true);
                }
                
                else if(row > 0 && col == 3)
                {
                    jbutton2d[row - 1][col - 1].setEnabled(true);
                    jbutton2d[row][col - 1].setEnabled(true);
                    jbutton2d[row + 1][col - 1].setEnabled(true);
                    
                    jbutton2d[row - 1][col].setEnabled(true);
                    jbutton2d[row + 1][col].setEnabled(true);
                }
                
                //row 1, 2
                else if(row > 0 && col > 0)
                {
                    jbutton2d[row - 1][col - 1].setEnabled(true);
                    jbutton2d[row - 1][col].setEnabled(true);
                    jbutton2d[row - 1][col + 1].setEnabled(true);
                    
                    jbutton2d[row + 1][col - 1].setEnabled(true);
                    jbutton2d[row + 1][col].setEnabled(true);
                    jbutton2d[row + 1][col + 1].setEnabled(true);
                    
                    jbutton2d[row][col - 1].setEnabled(true);
                    jbutton2d[row][col + 1].setEnabled(true);
                }
                
                
 
            }
        }
        
    }
    
    private class LetterListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            if(ae.getSource() instanceof JButton)
            {
                JButton tile = (JButton)ae.getSource();
                
                String letter = (String)tile.getClientProperty("letter");
                
                currentWordLabel.setText(currentWordLabel.getText() + letter);
            }
        }
        
    }
    
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(seconds == 0 && minutes == 0)
            {              
                timer.stop();
                JOptionPane.showMessageDialog(jframe, "Time is up! Game over!");
            }
            else
            {
                if(seconds == 0)
                {
                    seconds = 59;
                    minutes--;
                }
                else
                {
                    seconds--;
                }
            }

            if(seconds < 10)
            {
                String strSeconds = "0" + String.valueOf(seconds);
                timerLabel.setText(String.valueOf(minutes) + ":" + strSeconds);
            }
            else
            {
                timerLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }
        }
        
    }
}
