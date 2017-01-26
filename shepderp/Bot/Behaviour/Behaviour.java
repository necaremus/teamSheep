package shepderp.Bot.Behaviour;

import battlecode.common.*;
import shepderp.Bot.Bot;
import shepderp.Constants;

import java.util.Map;

/**
 * Created by necaremus on 17.01.17.
 */
public abstract class Behaviour {

    public Bot bot;
    public RobotController rc;
    public int rotateInterval = 1;

    public abstract void execute() throws GameActionException;

    //region build robot functions

    public Direction getPreferredBuildingDirection() throws GameActionException {
        //TODO
        return rc.getLocation().directionTo(bot.enemyAvgStartLocation);
    }

    private boolean canBuild(RobotType type) throws GameActionException {
        if (type.bulletCost > rc.getTeamBullets()) return false;
        if (!(rc.getType() != RobotType.ARCHON || rc.getType() != RobotType.GARDENER)) return false;

        return rc.hasRobotBuildRequirements(type);
    }

    // build rotate left/right
    public boolean build(RobotType type) throws GameActionException {
        System.out.println("DEBUG: Bot.build 1");
        return build(type, getPreferredBuildingDirection(), 180, rotateInterval);
    }
    public boolean build(RobotType type, Direction dir) throws GameActionException {
        System.out.println("DEBUG: Bot.build 2");
        return build(type, dir, 180, rotateInterval);
    }
    public boolean build(RobotType type, Direction dir, int maxRotateDegrees) throws GameActionException {
        System.out.println("DEBUG Bot.build 3");
        return build(type, dir, maxRotateDegrees, rotateInterval);
    }
    public boolean build(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
        System.out.println("DEBUG: Bot.build 4");
        return canBuild(type) && tryBuild(type, dir, maxRotateDegrees, rotateInterval);
    }
    private boolean tryBuild(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
        System.out.println("DEBUG: Bot.tryBuild");
        if (rc.canBuildRobot(type, dir)) { rc.buildRobot(type, dir); return true; }
        return tryRotateBuild(type, dir, maxRotateDegrees, rotateInterval);
    }
    private boolean tryRotateBuild(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
        System.out.println("DEBUG: Bot.tryRotateBuild");
        if (maxRotateDegrees > 180) maxRotateDegrees = 180;
        for (int i = rotateInterval; i <= maxRotateDegrees; i += rotateInterval) {
            if (rc.canBuildRobot(type, dir.rotateLeftDegrees(i))) { rc.buildRobot(type, dir.rotateLeftDegrees(i)); return true; }
            if (rc.canBuildRobot(type, dir.rotateRightDegrees(i))) { rc.buildRobot(type, dir.rotateRightDegrees(i)); return true; }
        }
        return false;
    }

    // build rotate left
    public boolean buildLeft(RobotType type) throws GameActionException {
        System.out.println("DEBUG: Bot.buildLeft 1");
        return buildLeft(type, getPreferredBuildingDirection(), 270);
    }
    public boolean buildLeft(RobotType type, Direction dir) throws GameActionException {
        System.out.println("DEBUG: Bot.buildLeft 2");
        return buildLeft(type, dir, 270, rotateInterval);
    }
    public boolean buildLeft(RobotType type, Direction dir, int maxRotateDegrees) throws GameActionException {
        System.out.println("DEBUG: Bot.buildLeft 3");
        return buildLeft(type, dir, maxRotateDegrees, rotateInterval);
    }
    public boolean buildLeft(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
        System.out.println("DEBUG: Bot.buildLeft 4");
        return canBuild(type) && tryRotateLeftBuild(type, dir, maxRotateDegrees, rotateInterval);
    }
    private boolean tryRotateLeftBuild(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
        System.out.println("DEBUG: Bot.tryRotateLeftBuild");
        if (rc.canBuildRobot(type, dir)) {
            rc.buildRobot(type, dir);
            return true;
        }
        if (maxRotateDegrees > 270) maxRotateDegrees = 270;
        for (int i = rotateInterval; i <= maxRotateDegrees; i += rotateInterval) {
            if (rc.canBuildRobot(type, dir.rotateLeftDegrees(i))) {
                rc.buildRobot(type, dir.rotateLeftDegrees(i));
                return true;
            }
        }
        return false;
    }

    // build rotate right
    public boolean buildRight(RobotType type) throws GameActionException {
        System.out.println("DEBUG: Bot.buildRight 1");
        return buildRight(type, getPreferredBuildingDirection(), 270, rotateInterval);
    }
    public boolean buildRight(RobotType type, Direction dir) throws GameActionException {
        System.out.println("DEBUG: Bot.buildRight 2");
        return buildRight(type, dir, 270, rotateInterval);
    }
    public boolean buildRight(RobotType type, Direction dir, int maxRotateDegrees) throws GameActionException {
        System.out.println("DEBUG: Bot.buildRight 3");
        return buildRight(type, dir, maxRotateDegrees, rotateInterval);
    }
    public boolean buildRight(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
        System.out.println("DEBUG: Bot.buildRight 4");
        return canBuild(type) && tryRotateRightBuild(type, dir, maxRotateDegrees, rotateInterval);
    }
    private boolean tryRotateRightBuild(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
        System.out.println("DEBUG: Bot.tryRotateLeftBuild");
        if (rc.canBuildRobot(type, dir)) {
            rc.buildRobot(type, dir);
            return true;
        }
        if (maxRotateDegrees > 270) maxRotateDegrees = 270;
        for (int i = rotateInterval; i <= maxRotateDegrees; i += rotateInterval) {
            if (rc.canBuildRobot(type, dir.rotateRightDegrees(i))) {
                rc.buildRobot(type, dir.rotateRightDegrees(i));
                return true;
            }
        }
        return false;
    }
    //endregion

    //region move functions

    //region move to MapLocation
    public boolean moveTo(MapLocation ml) throws GameActionException {
        return tryMoveTo(ml)
                || tryRotateMove(rc.getLocation().directionTo(ml), rc.getType().strideRadius, 90);
    }
    public boolean moveTo(MapLocation ml, float dist) throws GameActionException {
        return moveTo(ml.subtract(ml.directionTo(rc.getLocation()), dist));
    }
    private boolean tryMoveTo(MapLocation ml) throws GameActionException {
        if (!rc.hasMoved() && rc.canMove(ml)) { rc.move(ml); return true; }
        return false;
    }
    //endregion

    //region move to Direction
    public boolean moveTo(Direction dir) throws GameActionException {
        return tryMoveTo(dir, rc.getType().strideRadius);
    }
    public boolean moveTo(Direction dir, float dist) throws GameActionException {
        return tryMoveTo(dir, dist);
    }
    private boolean tryMoveTo(Direction dir, float dist) throws GameActionException {
        if (!rc.hasMoved() && rc.canMove(dir, dist)) { rc.move(dir, dist); return true; }
        return false;
    }
    //endregion

    //region rotate move to
    public boolean rotateMoveTo(Direction dir) throws GameActionException {
        return tryRotateMove(dir, rc.getType().strideRadius, 180);
    }
    public boolean rotateMoveTo(Direction dir, float dist) throws GameActionException {
        return tryRotateMove(dir, dist, 180);
    }
    public boolean rotateMoveTo(Direction dir, float dist, int maxRotateDegrees) throws GameActionException {
        return tryRotateMove(dir, dist, maxRotateDegrees);
    }
    private boolean tryRotateMove(Direction dir, float dist, int maxRotateDegrees) throws GameActionException {
        if (maxRotateDegrees > 180) maxRotateDegrees = 180;
        for (int i = rotateInterval; i <= maxRotateDegrees; i += rotateInterval) {

            // rotate left
            if (tryMoveTo(dir.rotateLeftDegrees(i), dist)) {
                //bot.turnedLeft = true;
                //bot.turnedRight = false;
                return true;
            }

            // rotate right
            if (tryMoveTo(dir.rotateRightDegrees(i), dist)) {
                //bot.turnedLeft = false;
                //bot.turnedRight = true;
                return true;
            }
        }

        bot.isStuck = true;
        return false;
    }

    public void moveToTarget(MapLocation ml, float dist) throws GameActionException {
        while (rc.getLocation().distanceTo(ml) > dist) {
            moveTo(ml, dist);
            Clock.yield();
        }
    }
    //endregion
    /*
    public boolean moveTo(MapLocation ml) throws  GameActionException {
        return tryMoveTo(ml);
    }
    public boolean moveTo(MapLocation ml, float dist) throws GameActionException {
        return moveTo(ml.subtract(ml.directionTo(rc.getLocation()), dist));
    }
    private boolean tryMoveTo(MapLocation ml) throws GameActionException {
        if (!rc.hasMoved() && rc.canMove(ml)) { rc.move(ml); return true; }
        return false;
    }
    private boolean tryMoveTo(Direction dir) throws GameActionException {
        return tryMoveTo(dir, rc.getType().strideRadius);
    }
    private boolean tryMoveTo(Direction dir, float dist) throws GameActionException {
        if (!rc.hasMoved() && rc.canMove(dir, dist)) { rc.move(dir, dist); return true; }
        return false;
    }
    public boolean rotateMoveTo(MapLocation ml) throws GameActionException{
        return rotateMoveTo(ml, 360);
    }
    public boolean rotateMoveTo(MapLocation ml, int maxRotateDegree) throws GameActionException {
        return tryRotateMoveTo(ml, maxRotateDegree);
    }
    public boolean rotateMoveTo(MapLocation ml, float dist, int maxRotateDegree) throws GameActionException {
        return tryMoveTo(ml.subtract(ml.directionTo(rc.getLocation()), dist))
                || tryRotateMoveTo(ml.subtract(ml.directionTo(rc.getLocation()), dist), maxRotateDegree);
    }
    //TODO
    private boolean tryRotateMoveTo(MapLocation ml, int maxRotateDegree) throws GameActionException {
        //TODO save last location so we don't wiggle back and forth
        for (int i = rotateInterval; i <= maxRotateDegree; i += rotateInterval) {
            if (rc.canMove(rc.getLocation().directionTo(ml).rotateRightDegrees(i))) {
                rc.move(rc.getLocation().directionTo(ml).rotateRightDegrees(i));
                return true;
            }
            if (rc.canMove(rc.getLocation().directionTo(ml).rotateLeftDegrees(i))) {
                rc.move(rc.getLocation().directionTo(ml).rotateLeftDegrees(i));
                return true;
            }
        }
        return false;
    }

    public boolean forceMoveTo(MapLocation ml) throws GameActionException {
        return forceMoveTo(ml, 0);
    }
    public boolean forceMoveTo(MapLocation ml, float dist) throws GameActionException {
        while (rc.getLocation().distanceTo(ml) > dist) {
            if (rc.hasMoved()) Clock.yield();
            if (!rotateMoveTo(ml)) return false;
        }
        return true;
    }
    public boolean moveTo(Direction dir) throws GameActionException {
        return moveTo(dir, rc.getType().strideRadius);
    }
    public boolean moveTo(Direction dir, float dist) throws GameActionException {
        return tryMove(dir, dist) || tryRotateMove(dir, dist, 180);
    }
    private boolean tryMove(Direction dir, float dist) throws GameActionException {
        if (!rc.hasMoved() && rc.canMove(dir, dist)) { rc.move(dir, dist); return true; }
        return false;
    }
    public boolean rotateMoveTo(Direction dir, float dist, int maxRotateDegrees) throws GameActionException {
        return tryRotateMove(dir, dist, maxRotateDegrees);
    }
    private boolean tryRotateMove(Direction dir, float dist, int maxRotateDegrees) throws GameActionException {
        if (maxRotateDegrees > 180) maxRotateDegrees = 180;
        for (int i = rotateInterval; i >= maxRotateDegrees; i += rotateInterval) {

            // rotate left
            if (!bot.turnedRight && tryMove(dir.rotateLeftDegrees(i), dist)) {
                bot.turnedLeft = true;
                bot.turnedRight = false;
                return true;
            }

            // rotate right
            if (!bot.turnedLeft && tryMove(dir.rotateRightDegrees(i), dist)) {
                bot.turnedLeft = false;
                bot.turnedRight = true;
                return true;
            }
        }

        bot.isStuck = true;
        return false;
    }
    //*/
    //endregion
    public boolean shakeTree(TreeInfo tree) throws GameActionException {
        if (rc.canShake(tree.location)) { rc.shake(tree.location); return true; }
        return false;
    }


    protected float determineMapEdgeX() throws GameActionException {
        float y = rc.getLocation().y;
        float minX = rc.getLocation().x;
        float maxX = (minX > bot.predictedCenterOfMap.x) ? minX + rc.getType().sensorRadius : minX - rc.getType().sensorRadius;
        if (maxX < 0) return bot.predictedCenterOfMap.x * 2;
        if (rc.onTheMap(new MapLocation(maxX, y))) return -maxX;
        float x = (minX + maxX) / 2;
        for (int i = 0; i <= 8; i++) {
            if (rc.onTheMap(new MapLocation(x, y))) {
                minX = x;
                x = (x + maxX) / 2;
            } else {
                maxX = x;
                x = (x + minX) / 2;
            }
        }
        x -= bot.predictedCenterOfMap.x;
        return (x > 0) ? x * 2 : -(x * 2);
    }
    protected float determineMapEdgeY() throws GameActionException {
        float x = rc.getLocation().x;
        float minY = rc.getLocation().y;
        float maxY = (minY > bot.predictedCenterOfMap.y) ? minY + rc.getType().sensorRadius : minY - rc.getType().sensorRadius;
        if (maxY < 0) return bot.predictedCenterOfMap.y * 2;
        if (rc.onTheMap(new MapLocation(x, maxY))) return -maxY;
        float y = (minY + maxY) / 2;
        //if (y < 0) return 0;
        for (int i = 0; i <= 8; i++) {
            if (rc.onTheMap(new MapLocation(x, y))) {
                minY = y;
                y = (y + maxY) / 2;
            } else {
                maxY = y;
                y = (y + minY) / 2;
            }
        }
        y -= bot.predictedCenterOfMap.y;
        return (y > 0) ? y * 2 : -(y * 2);
    }
    /*
    protected boolean getLocations() throws GameActionException {
        if (rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY) % 10 == 0) return false;
        bot.predictedCenterOfMap = bot.radio.getPredictedCenterOfMap();
        bot.enemyAvgStartLocation = bot.radio.getEnemyAvgStartLocation();
        bot.mapSymmetry = bot.radio.getMapSymmetry();
        bot.mapSize = bot.radio.getMapSize();
        return true;
    }//*/

}
