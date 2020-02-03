package StateMachine;

public enum StateEnum {

    INITIAL(0, "INITIAL"),

    SIGN_ON(1, "SIGN_ON"),

    RE_SIGN_ON(2, "RE_SIGN_ON"),

    READY(3, "READY"),

    RE_SCORE_BOARD(4, "RE_SCORE_BOARD"),

    CATEGORY_SELECTION_ANNOUNCEMENT(5, "CATEGORY_SELECTION_ANNOUNCEMENT"),

    SELECTOR(6, "SELECTOR"),

    RE_QUESTION(7, "RE_QUESTION"),

    BUZZ_SCREW(8, "BUZZ_SCREW"),

    RE_BUZZ_SCREW(9, "RE_BUZZ_SCREW"),

    ANSWERER(10, "ANSWERER");

    private int key;

    private String stateStr;

    StateEnum(int key, String stateStr)
    {
        this.key = key;

        this.stateStr = stateStr;
    }

    void printState()
    {
        System.out.println(String.format("current state: %d: %s", this.key, this.stateStr));
    }
}
