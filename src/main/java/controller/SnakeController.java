package controller;

import model.Section;
import model.SectionType;
import model.Snake;

import java.awt.*;
import java.util.Random;


public class SnakeController {
    public static final int DIMENSION = 10;
    private static final int RANDOMRANGE = 10;
    private static final int RANDOMRANGE2 = 5;
    private Snake theSnake;
    private Section food;
    private int boardX;
    private int boardY;
    private Direction lastDirection;
    private int lives = 5;
    private int foodCount = 0;

    public int getLives() {
        return lives;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public Section getFood() {
        return food;
    }

    public SnakeController(int boardX, int boardY) {
        this.boardX = boardX;
        this.boardY = boardY;
    }

    public int getBoardX() {
        return boardX;
    }

    public void setBoardX(int boardX) {
        this.boardX = boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void setBoardY(int boardY) {
        this.boardY = boardY;
    }

    public Section generateRandomCell(final SectionType type) {
        Section theSection = new Section();
        Random n = new Random();
        theSection.setX(n.nextInt(boardX - RANDOMRANGE) + RANDOMRANGE2);
        theSection.setY(n.nextInt(boardY - RANDOMRANGE) + RANDOMRANGE2);
        theSection.setType(type);
        theSection.setDirection(Direction.DOWN);
        return theSection;
    }

    public void init() {
        food = generateRandomCell(SectionType.FOOD);
        theSnake = new Snake(generateRandomCell(SectionType.SNAKE));
        theSnake.setBorderX(boardX);
        theSnake.setBorderY(boardY);
        if (theSnake.getBody().get(0).getX() < boardX % 2) {
            setLastDirection(Direction.DOWN);
        } else {
            setLastDirection(Direction.UP);
        }
    }

    public void newHeadPosition(Direction d) {
        lastDirection = d;
        theSnake.moveHead(d);
    }

    public void drawCell(Graphics g, Section section, Color c) {
        g.setColor(c);
        g.fillRect(section.getX() * DIMENSION, section.getY() * DIMENSION, DIMENSION, DIMENSION);
    }

    public void setLastDirection(Direction d) {
        this.lastDirection = d;
    }

    public void drawSnake(Graphics g) {
        theSnake.getRemovable().stream().forEach((t) -> drawCell(g, t, Color.LIGHT_GRAY));
        theSnake.getBody().stream().forEach((t) -> drawCell(g, t, Color.BLACK));
    }

    public void moveSnake(Graphics g) {
        drawSnake(g);
        theSnake.moveHead(this.lastDirection);
        drawCell(g, food, Color.RED);
        checkKopp(g, theSnake.kopp());
        checkEat(g, theSnake.eat(food));
        checkBitItself(g, theSnake.isBitItself());
    }

    private void checkBitItself(Graphics g, boolean bitItself) {
        if (bitItself) {
            --lives;
            resetGame(g);
        }
    }

    private void checkEat(Graphics g, boolean eat) {
        if (eat) {
            ++foodCount;
            drawCell(g, food, Color.BLACK);
            theSnake.addCell();
            food = generateRandomCell(SectionType.FOOD);
        }
    }

    private void checkKopp(Graphics g, boolean kopp) {
        checkBitItself(g, kopp);
    }


    public void resetGame(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.boardX * DIMENSION, this.getBoardY() * DIMENSION);
        init();
    }

}


