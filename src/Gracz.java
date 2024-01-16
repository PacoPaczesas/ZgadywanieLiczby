import java.io.Serializable;

public class Gracz implements Serializable {
    private static final long serialVersionUID = 1L;

    String nickname;
    int score = 0;
    boolean winner = false;
    boolean isAI = false;
    int myNumber;
    int max;
    int min;
}
