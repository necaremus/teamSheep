package shepderp.Bot.Utilities;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import shepderp.Constants;

/**
 * Created by necaremus on 17.01.17.
 */
public class Util {

    //region symmetry
    public static Constants.MapSymmetry getSymmetry (MapLocation[] myLocs, MapLocation[] enemyLocs) throws GameActionException {
        if (myLocs.length < 1) return Constants.MapSymmetry.NONE_DETERMINED;
        if (checkAllX(myLocs, enemyLocs)) {
            if (myLocs.length == 1) return Constants.MapSymmetry.HORIZONTAL_OR_ROTATIONAL;
            return  Constants.MapSymmetry.HORIZONTAL;
        }
        if (checkAllY(myLocs, enemyLocs)) {
            if (myLocs.length == 1) return Constants.MapSymmetry.VERTICAL_OR_ROTATIONAL;
            return Constants.MapSymmetry.VERTICAL;
        }
        return Constants.MapSymmetry.ROTATIONAL;
    }
    // broadcast helper
    public static int mapSymmetryToInt(Constants.MapSymmetry mS) throws GameActionException {
        switch (mS) {
            case HORIZONTAL: return 1;
            case VERTICAL: return 2;
            case ROTATIONAL: return 3;
            case HORIZONTAL_OR_ROTATIONAL: return 4;
            case VERTICAL_OR_ROTATIONAL: return 5;
            default: return 0;
        }
    }
    public static Constants.MapSymmetry intToMapSymmetry(int i) throws GameActionException {
        switch (i) {
            case 0: return Constants.MapSymmetry.NONE_DETERMINED;
            case 1: return Constants.MapSymmetry.HORIZONTAL;
            case 2: return Constants.MapSymmetry.VERTICAL;
            case 3: return Constants.MapSymmetry.ROTATIONAL;
            case 4: return Constants.MapSymmetry.HORIZONTAL_OR_ROTATIONAL;
            case 5: return Constants.MapSymmetry.VERTICAL_OR_ROTATIONAL;
            default: return Constants.MapSymmetry.NONE_DETERMINED;
        }
    }

    private static boolean checkAllX(MapLocation[] myLocs, MapLocation[] enemyLocs) throws GameActionException {
        for (int i = (myLocs.length - 1); i < myLocs.length; i++) {
            if (myLocs[i].x != enemyLocs[i].x) return false;
        }
        return true;
    }
    private static boolean checkAllY(MapLocation[] myLocs, MapLocation[] enemyLocs) throws GameActionException {
        for (int i = (myLocs.length - 1); i < myLocs.length; i++) {
            if (myLocs[i].y != enemyLocs[i].y) return false;
        }
        return true;
    }
    //endregion

    //region avgLocations
    public static MapLocation averageLocation(MapLocation[] mapLocations) throws GameActionException {
        float avgLocX = 0, avgLocY = 0;
        for (MapLocation mapLocation : mapLocations) {
            avgLocX += mapLocation.x;
            avgLocY += mapLocation.y;
        }
        avgLocX /= mapLocations.length;
        avgLocY /= mapLocations.length;
        return new MapLocation(avgLocX, avgLocY);
    }
    //endregion

    // tau ftw
    public static float piRadToTauRad(float piRad) throws GameActionException {
        return piRad >= 0 ? piRad : Constants.TAU + piRad;
    }
    public static float tauRadToPiRad(float tauRad) throws GameActionException {
        return tauRad <= Math.PI ? tauRad : tauRad - Constants.TAU;
    }
}
