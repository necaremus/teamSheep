package shepderp.Bot.Behaviour.GardenerBehaviour;

import battlecode.common.*;
import shepderp.Bot.Gardener;

/**
 * Created by necaremus on 17.01.17.
 */
public class InitialGardenerBehaviour extends GardenerBehaviour {
    public Gardener gardener;
    public InitialGardenerBehaviour(Gardener gardener) {
        this.gardener = gardener;
    }

    public void execute() throws GameActionException {
        if (rc.getTeamBullets() >= 100) build(RobotType.LUMBERJACK);
        //debug_drawTens();
        runInCircle();
    }
    private void runInCircle() throws GameActionException {
        Direction dir = rc.getLocation().directionTo(gardener.predictedCenterOfMap).rotateRightDegrees(90);
        MapLocation ml = rc.getLocation();
        for (int i = 1; i <= 10; i++) {
            ml = ml.add(dir.rotateLeftDegrees(i*36), rc.getType().strideRadius);
            moveTo(ml);
            Clock.yield();
        }
    }

    public void debug_drawTens() throws GameActionException {
        Direction dir = rc.getLocation().directionTo(gardener.predictedCenterOfMap).rotateRightDegrees(90);
        MapLocation ml = rc.getLocation();
        for (int i = 1; i <= 10; i++) {
            MapLocation nMl = ml.add(dir.rotateLeftDegrees(i*36), rc.getType().strideRadius);
            rc.setIndicatorLine(ml, nMl, 255, 255, 255);
            rc.setIndicatorDot(ml.add(dir.rotateLeftDegrees(i*36).rotateRightDegrees(90), 2), 0, 0, 0);
            ml = nMl;
        }
    }


}
