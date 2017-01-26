package shepderp;

/**
 * Created by necaremus on 17.01.17.
 */
public class Constants {

    public enum MapSymmetry {
        NONE_DETERMINED,
        VERTICAL, HORIZONTAL, ROTATIONAL, VERTICAL_OR_ROTATIONAL, HORIZONTAL_OR_ROTATIONAL
    }
    public static float TAU = (float)(Math.PI * 2);
    public static int CHANNEL_MAPSIZE_SYMMETRY = 0;
    public static int CHANNEL_CENTER_LOCATION_X = 1;
    public static int CHANNEL_CENTER_LOCATION_Y = 2;
    public static int CHANNEL_INITIAL_ENEMY_LOCATION_X = 3;
    public static int CHANNEL_INITIAL_ENEMY_LOCATION_Y = 4;
    public static int CHANNEL_ROBOT_COUNT = 5;
    public static int CHANNEL_ORDER = 10;
}
