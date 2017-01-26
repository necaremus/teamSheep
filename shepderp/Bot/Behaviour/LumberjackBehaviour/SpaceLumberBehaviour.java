package shepderp.Bot.Behaviour.LumberjackBehaviour;

import battlecode.common.GameActionException;
import battlecode.common.Team;
import battlecode.common.TreeInfo;
import shepderp.Bot.Lumberjack;
import shepderp.Bot.Utilities.Geometry;
import shepderp.Bot.Utilities.HelperClasses.Body;

/**
 * Created by necaremus on 17.01.17.
 */
public class SpaceLumberBehaviour extends LumberjackBehaviour {
    public Lumberjack lumber;
    private Body myBodyToChop;

    public SpaceLumberBehaviour(Lumberjack lumber) { this.lumber = lumber; }
    public void execute() throws GameActionException {
        try {
            if (getLocalTrees()) {
                if (!lumber.bBodies.isEmpty()) {
                    myBodyToChop = lumber.bBodies.get(lumber.bBodies.size() - 1);
                    checkMyChopTree();
                    moveTo(myBodyToChop.ml, myBodyToChop.r + rc.getType().bodyRadius);
                } else {
                    myBodyToChop = lumber.nBodies.get(lumber.nBodies.size() - 1);
                    moveTo(myBodyToChop.ml, myBodyToChop.r + rc.getType().bodyRadius);
                }
                strikeOrChopTrees();
            } else moveTo(lumber.enemyAvgStartLocation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * adds the local neutral bodies to lumbers ArrayList
     *
     * @return if bodies have been found
     * @throws GameActionException
     */
    public boolean getLocalTrees() throws GameActionException {
        TreeInfo[] trees = rc.senseNearbyTrees(rc.getType().sensorRadius, Team.NEUTRAL);
        if (trees.length == 0) return false;
        lumber.bBodies.clear();
        lumber.nBodies.clear();
        for (int i = trees.length; --i >= 0;) {
            if (trees[i].getContainedRobot() != null) lumber.bBodies.add(new Body(trees[i].location, trees[i].radius));
            else lumber.nBodies.add(new Body(trees[i].location, trees[i].radius));
        }
        return true;
    }

    /**
     * checks if our goal tree can be reached in a straight line
     *
     * @throws GameActionException
     */
    private void checkMyChopTree() throws GameActionException {
        float dist = rc.getLocation().distanceTo(myBodyToChop.ml);
        for (int i = lumber.nBodies.size(); --i >= 0;){

            // break if we reach bodies further away than our goal tree
            if (dist < lumber.nBodies.get(i).ml.distanceTo(rc.getLocation())) break;

            // check if the tree is in front of our goal tree
            if (Geometry.isInRectangle(lumber.nBodies.get(i), rc.getLocation().directionTo(myBodyToChop.ml), rc.getLocation(), rc.getType().bodyRadius)) {
                myBodyToChop = lumber.nBodies.get(i);
                break;
            }
        }
    }

    /**
     * checks for nearby friendly bots in strike radius
     *
     * @return false, if a nearby friendly bot or tree is in strike radius
     * @throws GameActionException
     */
    public boolean checkStrike() throws GameActionException {
        return rc.canStrike() && !(rc.senseNearbyRobots(2, rc.getTeam()).length > 0)
                && !(rc.senseNearbyTrees(2, rc.getTeam()).length > 0);
    }

    /**
     * checks if it should chop or strike bodies
     *
     * @return true if 3 or more bodies can be hit with a strike, else false
     * @throws GameActionException
     */
    public boolean strikeOrChopTrees() throws GameActionException {
        TreeInfo[] trees = rc.senseNearbyTrees(2, Team.NEUTRAL);
        if (trees.length <= 0) return false;
        if (trees.length >= 3) {
            for (int i = trees.length; --i >= 0;)
                if (trees[i].getContainedRobot() != null && trees[i].health <= 4 && rc.canChop(trees[i].ID)) { rc.chop(trees[i].ID); return true; }
            if (checkStrike()) { rc.strike(); return true; }
        }
        if (rc.canChop(trees[0].ID)) { rc.chop(trees[0].ID); return true; }
        return false;
    }

}
