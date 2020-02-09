package model;

import controller.Direction;

public class Section implements Cloneable {
   private int x;
   private int y;
   private SectionType type;
   private Direction direction;

    public Object clone()throws CloneNotSupportedException{
        return (Section)super.clone();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case DOWN: {
                y ++;
                break;
            }
            case UP: {
                y --;
                break;
            }
            case LEFT: {
                x --;
                break;
            }
            case RIGHT: {
                x ++;
                break;
            }

        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }


}
