package shepderp.Bot.Behaviour.GardenerBehaviour;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotType;
import shepderp.Bot.Behaviour.Behaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public abstract class GardenerBehaviour extends Behaviour {

    public boolean buildTree() throws GameActionException {
        return buildTree(getPreferredBuildingDirection(), 180);
    }
    public boolean buildTree(Direction dir, int maxRotateDegrees) throws GameActionException {
        if (!rc.hasTreeBuildRequirements()) return false;
        return tryBuildTree(dir, maxRotateDegrees);
    }
    private boolean tryBuildTree(Direction dir, int maxRotateDegrees) throws GameActionException {
        if (rc.canPlantTree(dir)) { rc.plantTree(dir); return true; }
        if (maxRotateDegrees > 180) maxRotateDegrees = 180;
        for (int i = rotateInterval; i <= maxRotateDegrees; i += rotateInterval) {
            if (rc.canPlantTree(dir.rotateLeftDegrees(i)))  { rc.plantTree(dir.rotateLeftDegrees(i)); return true; }
            if (rc.canPlantTree(dir.rotateRightDegrees(i))) { rc.plantTree(dir.rotateRightDegrees(i)); return true; }
        }

        return false;
    }
    public boolean checkSpace() throws GameActionException {

        return false;
    }

}
