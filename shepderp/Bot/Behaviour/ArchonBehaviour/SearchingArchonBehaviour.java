package shepderp.Bot.Behaviour.ArchonBehaviour;

import battlecode.common.*;
import shepderp.Bot.Archon;

/**
 * Created by necaremus on 17.01.17.
 */
public class SearchingArchonBehaviour extends ArchonBehaviour {
    public SearchingArchonBehaviour(Archon archon) { this.archon = archon; }
    public Archon archon;
    Direction myDir;
    public void execute() throws GameActionException {
        myDir = getMyDir();
        moveTo(rc.getLocation().directionTo(archon.predictedCenterOfMap).opposite());
        //if (shouldHireGardener() && build(RobotType.GARDENER)) archon.gardenersHired++;
        rc.setIndicatorLine(rc.getLocation(), archon.predictedCenterOfMap, 0, 0, 0);
        debug_setIndicatorLinesPredictedMap();

    }

    /*
    @returns the direction where no map size has been determined
     */
    private Direction getMyDir() throws GameActionException {
        int[] buffer = archon.radio.getMapSize();
        // nothing should have been broadcastet, but the archon should have saved the predicted minSize
        if (archon.mapSize[0] != buffer[0]) {
            if (rc.getLocation().x > archon.predictedCenterOfMap.x) return Direction.getEast();
            else return Direction.getWest();
        }
        if (archon.mapSize[1] != buffer[1]) {
            if (rc.getLocation().y > archon.predictedCenterOfMap.y) return Direction.getNorth();
            else return Direction.getSouth();
        }
        return Direction.getNorth(); // dis is random
    }
}
