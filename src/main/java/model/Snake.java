package model;

import controller.Direction;

import java.util.LinkedList;
import java.util.List;

public class Snake  {

    private LinkedList<Section> body;
    private LinkedList<Section> removable;
    private int borderX;
    private int BorderY;

    public int getBorderX() {
        return borderX;
    }

    public void setBorderX(int borderX) {
        this.borderX = borderX;
    }

    public int getBorderY() {
        return BorderY;
    }

    public void setBorderY(int borderY) {
        BorderY = borderY;
    }

    public void addCell() {
        Section c = new Section();
        c.setDirection(body.peekLast().getDirection());
        c.setType(SectionType.SNAKE);
        int x = body.peekLast().getX();
        int y = body.peekLast().getY();
        switch (body.peekLast().getDirection()) {
            case UP: {
                y++;
                break;
            }
            case DOWN: {
                y--;
                break;
            }
            case LEFT: {
                x++;
                break;
            }
            case RIGHT: {
                x--;
                break;
            }
        }
        c.setX(x);
        c.setY(y);
        body.addLast(c);

    }


    public Snake(Section section) {
        body = new LinkedList<>();
        body.addFirst(section);
        removable = new LinkedList<>();
    }

    public void setRemovable() {
        try {
            Section section = (Section) body.peekLast().clone();
            removable.addFirst(section);
            if (removable.size() > 3) {
                removable.removeLast();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void stepToNewPosition() {
        setRemovable();
        for (int i = body.size() - 1; i > 0; i--) {
            body.get(i).setDirection(body.get(i - 1).getDirection());
        }
    }


    public boolean eat(Section food) {
        return food.getY() == body.peekFirst().getY() && food.getX() == body.peekFirst().getX();
    }

    public void moveHead(Direction d) {
        stepToNewPosition();
        body.peekFirst().setDirection(d);
    }


    public List<Section> getRemovable() {
        return removable;
    }

    public boolean isBitItself(){
        for(int i = 1; i < body.size(); i++ ){
            if(body.get(0).getX() == body.get(i).getX() && body.get(0).getY() == body.get(i).getY()){
                return true;
            }
        }
        return false;
    }

    public boolean kopp(){
        return body.peekFirst().getX() < 0 || body.peekFirst().getX() > borderX || body.peekFirst().getY() < 0 || body.peekFirst().getY() > getBorderY();

    }

    public List<Section> getBody() {
        return body;
    }
}