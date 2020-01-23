package guru.springframework.domain;

/**
 * Created by jt on 6/13/17.
 */
public enum Difficulty {

    EASY("Easy"),
    MODERATE("Moderate"),
    KIND_OF_HARD("Kind of hard"),
    HARD("Hard"),
    IMPOSSIBLE("Impossible...don't even bother");

    public String prettyName;

    Difficulty(String prettyName){
        this.prettyName = prettyName;
    }



}
