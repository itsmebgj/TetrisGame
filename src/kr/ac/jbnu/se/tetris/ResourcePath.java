package kr.ac.jbnu.se.tetris;

public final class ResourcePath {

    private ResourcePath() {} // 생성할 수 없는 객체

    /**
     * 배경음
     */
    public static final String BGM = "bgm/bgm.wav";

    /**
     * 스코어 txt 파일
     */
    public static final String[] MAX_SCORE = {
        "Score/MaxScore1.txt",
        "Score/MaxScore2.txt",
        "Score/MaxScore3.txt",
        "Score/MaxScore4.txt",
        "Score/MaxScore5.txt",
    };

    /**
     * 메인 화면 기본 배경 이미지
     */
    public static final String WALL_PAPER = "image/Background.jpg";

    /**
     * 게임 하단 조작법 설명바 이미지 (AI 모드)
     */
    public static final String KEY_INPUT_MANUAL = "image/control.png";

    /**
     * 게임 설명 이미지
     */
    public static final String TUTORIAL = "image/tutorials.png";

    /**
     * 버튼 이미지
     */
    public static final String BUTTON_2P_MODE = "image/buttons/2P_modes.png";

    public static final String BUTTON_AI_MODE = "image/buttons/AImodes.png";

    public static final String BUTTON_BACK = "image/buttons/back.png";

    public static final String BUTTON_KEY_CHANGE = "image/buttons/keychange.png";

    public static final String BUTTON_MUSIC_ON = "image/buttons/musicOn.png";

    public static final String BUTTON_MUSIC_OFF = "image/buttons/musicOff.png";

    public static final String BUTTON_SETTING = "image/buttons/settings.png";

    public static final String BUTTON_TUTORIAL = "image/buttons/tutorial.png";

    public static final String BUTTON_LOG_IN = "image/buttons/Login.png";

    public static final String BUTTON_SIGN_UP = "image/buttons/SignUp.png";
    
    public static final String[] BUTTON_LEVEL = {
        "image/buttons/level1.png",
        "image/buttons/level2.png",
        "image/buttons/level3.png",
        "image/buttons/level4.png",
        "image/buttons/level5.png",
    };

    /**
     * 블록 이미지
     */
    public static final String NO_SHAPE = "image/blocks/Block0.png";

    public static final String Z_SHAPE = "image/blocks/Block1.png";

    public static final String S_SHAPE = "image/blocks/Block2.png";

    public static final String LINE_SHAPE = "image/blocks/Block3.png";

    public static final String T_SHAPE = "image/blocks/Block4.png";

    public static final String SQUARE_SHAPE = "image/blocks/Block5.png";

    public static final String L_SHAPE = "image/blocks/Block6.png";

    public static final String MIRRORED_L_SHAPE = "image/blocks/Block7.png";

    public static final String BOMB_BLOCK = "image/blocks/BombIcon.png";

    public static final String LOCK_BLOCK = "image/blocks/lockBlock.png";

}
