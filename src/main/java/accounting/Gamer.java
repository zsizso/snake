package accounting;

import java.io.Serializable;
import java.util.Objects;

public class Gamer implements Serializable {
    private String name;
    private Integer score = 0;
    private Integer highScore = 0;

    public Integer getHighScore() {
        return highScore;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gamer)) return false;
        Gamer gamer = (Gamer) o;
        return getScore() == gamer.getScore() &&
                getName().equals(gamer.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getScore());
    }

    @Override
    public String toString() {
        return "Gamer{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", highScore=" + highScore +
                '}';
    }

}
