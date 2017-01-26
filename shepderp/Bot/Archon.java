package shepderp.Bot;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import shepderp.Bot.Behaviour.ArchonBehaviour.*;
import shepderp.Bot.Behaviour.Behaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public class Archon extends Bot {
    public int gardenersHired = 0;

    public Behaviour getBehaviour() throws GameActionException {
        return new InitialArchonBehaviour(this);
        /*
        if(behaviour == null && rc.getRoundNum() <= 1) return new InitialArchonBehaviour(this);
        if (gardenersHired == 1) return new SearchingArchonBehaviour(this);
        if (mapSize != radio.getMapSize()) return new SearchingArchonBehaviour(this);
        //if(behaviour instanceof InitialArchonBehaviour) return behaviour;
        return new IdleArchonBehaviour(this); //*/
    }

    public boolean isClosestToMapEdgeX() throws GameActionException {
        MapLocation[] mls = rc.getInitialArchonLocations(rc.getTeam());
        if (mls.length == 1) return true;
        float myDist = new MapLocation(rc.getLocation().x, predictedCenterOfMap.y).distanceTo(predictedCenterOfMap);
        for (MapLocation ml : mls) {
            if (new MapLocation(ml.x, predictedCenterOfMap.y).distanceTo(predictedCenterOfMap) > myDist) return false;
        }
        return true;
    }
    public boolean isClosestToMapEdgeY() throws GameActionException {
        MapLocation[] mls = rc.getInitialArchonLocations(rc.getTeam());
        if (mls.length == 1) return true;
        float myDist = new MapLocation(predictedCenterOfMap.x, rc.getLocation().y).distanceTo(predictedCenterOfMap);
        for (MapLocation ml : mls) {
            if (new MapLocation(predictedCenterOfMap.x, ml.y).distanceTo(predictedCenterOfMap) > myDist) return false;
        }
        return true;
    }
    public boolean isClosestToMapEdge() throws GameActionException {
        MapLocation[] mls = rc.getInitialArchonLocations(rc.getTeam());
        if (mls.length == 1) return true;
        float myDist = rc.getLocation().distanceTo(predictedCenterOfMap);
        for (MapLocation ml : mls) {
            if (ml.distanceTo(predictedCenterOfMap) > myDist) return false;
        }
        return true;
    }
}
