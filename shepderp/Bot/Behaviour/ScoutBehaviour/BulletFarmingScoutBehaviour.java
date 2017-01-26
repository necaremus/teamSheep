package shepderp.Bot.Behaviour.ScoutBehaviour;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.Team;
import battlecode.common.TreeInfo;
import shepderp.Bot.Scout;

/**
 * Created by necaremus on 17.01.17.
 */
public class BulletFarmingScoutBehaviour extends ScoutBehaviour {
    Scout scout;
    public BulletFarmingScoutBehaviour(Scout scout) { this.scout = scout; }
    public void execute() throws GameActionException {
        if (searchForBulletTrees()) System.out.println("More bodies found.");
        if (scout.bulletTrees.isEmpty()) {
            moveTo(scout.enemyAvgStartLocation);
            return;
        }
        TreeInfo myTree = getNearestTree();
        if (rc.getLocation().distanceTo(myTree.location.subtract(myTree.location.directionTo(rc.getLocation()), myTree.radius)) > (rc.getType().bodyRadius + 1))
            moveTo(myTree.location, myTree.radius + rc.getType().bodyRadius + 1);
        if (shakeTree(myTree)) scout.bulletTrees.remove(myTree);
    }
    public boolean searchForBulletTrees() throws GameActionException {
        int count = scout.bulletTrees.size();
        TreeInfo[] trees = rc.senseNearbyTrees(rc.getType().sensorRadius, Team.NEUTRAL);
        for (TreeInfo tree: trees) {
            if (tree.getContainedBullets() > 0) scout.bulletTrees.add(tree);
        }
        return scout.bulletTrees.size() > count;
    }
    private TreeInfo getNearestTree() throws GameActionException {
        float minDist = Float.MAX_VALUE;
        TreeInfo nearestTree = null;
        for (TreeInfo tree : scout.bulletTrees) {
            float dist = rc.getLocation().distanceTo(tree.location);
            if (dist < minDist) {
                minDist = dist;
                nearestTree = tree;
            }
        }
        return nearestTree;
    }
}
