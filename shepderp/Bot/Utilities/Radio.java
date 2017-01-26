package shepderp.Bot.Utilities;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import shepderp.Constants;

/**
 * Created by necaremus on 19.01.17.
 */
public class Radio {
    RobotController rc;
    public Radio(RobotController rc) { this.rc = rc; }
    //region write broadcast
    public void broadcastMapSizeX(int x) throws GameActionException {
        int broadcast = rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY);
        int sym = broadcast % 10;
        int y = (broadcast/10) % 1000;
        broadcastMapSizeSymmetry(x, y, sym);
    }
    public void broadcastMapSizeY(int y) throws GameActionException {
        int broadcast = rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY);
        int sym = broadcast % 10;
        int x = broadcast / 10000;
        broadcastMapSizeSymmetry(x, y, sym);
    }
    public void broadcastMapSymmetry(int sym) throws GameActionException {
        int broadcast = rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY);
        int x = broadcast / 10000;
        int y = (broadcast/10) % 1000;
        broadcastMapSizeSymmetry(x, y, sym);
    }
    public void broadcastMapSizeSymmetry(int x, int y, int sym) throws GameActionException {
        int broadcast = (x*10000) + (y*10) + sym;
        rc.broadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY, broadcast);
    }
    public void broadcastPredictedCenterOfMap(MapLocation center) throws GameActionException {
        rc.broadcast(Constants.CHANNEL_CENTER_LOCATION_X, Float.floatToIntBits(center.x));
        rc.broadcast(Constants.CHANNEL_CENTER_LOCATION_Y, Float.floatToIntBits(center.y));
    }
    public void broadcastEnemyAvgStartLocation(MapLocation ml) throws GameActionException {
        rc.broadcast(Constants.CHANNEL_INITIAL_ENEMY_LOCATION_X, Float.floatToIntBits(ml.x));
        rc.broadcast(Constants.CHANNEL_INITIAL_ENEMY_LOCATION_Y, Float.floatToIntBits(ml.y));
    }
    //endregion

    //region read broadcast
    public int[] getMapSize() throws GameActionException {
        int broadcast = rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY);
        int x = broadcast / 10000;
        int y = (broadcast/10) % 1000;
        return new int[] {x, y};
    }
    public Constants.MapSymmetry getMapSymmetry() throws GameActionException {
        return Util.intToMapSymmetry(rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY) % 10);
    }
    public MapLocation getPredictedCenterOfMap() throws GameActionException {
        return new MapLocation(Float.intBitsToFloat(rc.readBroadcast(Constants.CHANNEL_CENTER_LOCATION_X)),
                Float.intBitsToFloat(rc.readBroadcast(Constants.CHANNEL_CENTER_LOCATION_Y)));
    }
    public MapLocation getEnemyAvgStartLocation() throws GameActionException {
        return new MapLocation(Float.intBitsToFloat(rc.readBroadcast(Constants.CHANNEL_INITIAL_ENEMY_LOCATION_X)),
                Float.intBitsToFloat(rc.readBroadcast(Constants.CHANNEL_INITIAL_ENEMY_LOCATION_Y)));
    }
    //region
}
