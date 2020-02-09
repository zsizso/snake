package view;

import accounting.AccountManager;
import accounting.Gamer;
import controller.Direction;
import controller.SnakeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;


public class Game extends JPanel {
    private static final int TIMER_DELAY = 150;
    private static final String SNAKE = "Snake ";
    private static final String USAGE =  "Usage: param1 boardX (min 20, max 80), param2 boardY (min 20, max 80), param3 gamer Name";
    private static final String NEWPEAK = "  NEW PEAK!";
    private static final String LIVES = "  Lives:  ";
    private static final String NOTENOUGHTPARAM = "Parameter number is not enought";
    private static final String SCORE = "   Score: ";
    private static final String GAMEOVER ="GAME OVER";
    private static final String TENBEST = "Ten best gamers are";
    private static final String HIGHESTSCORE = " Highest score: ";
    private static final String BADNUMBERFORMAT = "Bad number format";
    private static final String BADDIMENSION = "Bad dimension";
    private static final String NAME = "Name: ";
    private static final int BOARDMIN = 20;
    private static final int BOARDMAX = 80;

    private JLabel label = new JLabel();
    private JPanel centerPanel;
    private SnakeController snakeController;
    private static JFrame frame = new JFrame(SNAKE);
    private Gamer gamer;
    private AccountManager accountManager;

    public Game(SnakeController snakeController, Gamer gamer,AccountManager accountManager) {
        this.accountManager = accountManager;
        accountManager.load();
        this.gamer = accountManager.getGamer(gamer);
        this.snakeController = snakeController;
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        new Timer(TIMER_DELAY, new TimerListener()).start();
        setFocusable(true);
        requestFocusInWindow();
        this.snakeController.init();
        setPreferredSize(new Dimension(snakeController.getBoardX()* SnakeController.DIMENSION, snakeController.getBoardY() * SnakeController.DIMENSION));
        addKeyListener(new MyKeyListener());
    }

    private class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_DOWN: {
                    snakeController.newHeadPosition(Direction.DOWN);
                    break;
                }
                case KeyEvent.VK_UP: {
                    snakeController.newHeadPosition(Direction.UP);
                    break;
                }
                case KeyEvent.VK_LEFT: {
                    snakeController.newHeadPosition(Direction.LEFT);
                    break;
                }
                case KeyEvent.VK_RIGHT: {
                    snakeController.newHeadPosition(Direction.RIGHT);
                    break;
                }
            }

        }

    }

    private void refreshCaption(Frame f){
        String hs = "";
        gamer.setScore(snakeController.getFoodCount());
        if(gamer.getScore() > gamer.getHighScore()){
            hs = NEWPEAK;
        }
         f.setTitle(SNAKE + gamer.getName() + LIVES + snakeController.getLives() + SCORE + gamer.getScore() + hs );
    }

    private void checkGamerScores(){
        if(gamer.getScore() > gamer.getHighScore()){
            gamer.setHighScore(gamer.getScore());
        }
        gamer.setScore(0);
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshCaption(frame);
            snakeController.moveSnake(centerPanel.getGraphics());
            if(snakeController.getLives() == 0){
                checkGamerScores();
                accountManager.save();
                System.out.println(GAMEOVER);
                System.out.println(TENBEST);
                for(Gamer g : accountManager.sorted()){
                    System.out.println(NAME + g.getName() + HIGHESTSCORE + g.getHighScore());
                }
                System.exit(0);
            }
        }
    }

    private static void createGame(int width,int height,Gamer gamer) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Game(new SnakeController(width,height),gamer,new AccountManager()));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static int convertDimension(String dim) throws IllegalArgumentException{
        int value = 0;
        try {
            value = Integer.parseInt(dim);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException(BADNUMBERFORMAT,e);
        }
       if(value < BOARDMIN || value > BOARDMAX){
           throw new IllegalArgumentException(BADDIMENSION);
       }
       return value;
    }


    public static void main(String[] args) {
       try{
           Gamer g = new Gamer();
           g.setName(args[2]);
       if(args.length < 3 || args[2].length() == 0){
           throw new IllegalArgumentException(NOTENOUGHTPARAM);
       }
       int width = convertDimension(args[0]);
       int height = convertDimension(args[1]);
            SwingUtilities.invokeLater(() -> createGame(width,height,g));
       } catch (IllegalArgumentException e){
           System.out.println(USAGE);
       }
    }
}



