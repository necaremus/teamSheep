package testplayer.Behaviour;


import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.TreeInfo;
import testplayer.Globals;



public class GardenerFarmingBehaviour {

    RobotController gardener;
    MapLocation averageEnemyStartingLocation;
    MapLocation[] enemyStartingPoints;

    public GardenerFarmingBehaviour(RobotController rc) {
        gardener = rc;

        // calculate average enemy starting location
        MapLocation[] enemyStartingPoints = gardener.getInitialArchonLocations(gardener.getTeam().opponent());
        this.enemyStartingPoints = enemyStartingPoints;
        float averageX = 0, averageY = 0;
        for(MapLocation loc : enemyStartingPoints) {
        	averageX += loc.x; averageY += loc.y;
        }
        averageX /= enemyStartingPoints.length;
        averageY /= enemyStartingPoints.length;
        averageEnemyStartingLocation = new MapLocation(averageX, averageY);
    }


    public void run() throws GameActionException {
        buildFirstUnits();	// forcefully builds a scout, and maybe a lumberjack if one is needed to harvest
        findNicePlaceToFarm();
        broadcastGTFO();
        while(true) {
        	tendToTheGardens();
            Clock.yield();
        }
    }


    public void tendToTheGardens() throws GameActionException {
    	plantTree();
    	waterTree();
    }

    public void plantTree() throws GameActionException {
    	if(!gardener.hasTreeBuildRequirements()) return;
    	Direction dir = gardener.getLocation().directionTo(averageEnemyStartingLocation);
    	int i;
    	for(i = 0; i < 360; i += 2) {
    		if(gardener.canPlantTree(dir.rotateLeftDegrees(i))) { gardener.plantTree(dir.rotateLeftDegrees(i)); return; }
    		if(gardener.canPlantTree(dir.rotateRightDegrees(i))) { gardener.plantTree(dir.rotateRightDegrees(i)); return; }
    	}
    }


    public void broadcastGTFO() throws GameActionException {
    	// TODO
    }


    public void broadcastDonePlanting() throws GameActionException {
    	// TODO
    }


    public void waterTree() throws GameActionException {
    	if(!gardener.canWater()) return;
    	TreeInfo[] adjacentTrees = gardener.senseNearbyTrees(2.2f, gardener.getTeam());
    	TreeInfo minHealthTree = null;
    	float minHealth = Float.MAX_VALUE;
    	for(TreeInfo tree : adjacentTrees) {
    		if(gardener.canWater(tree.location)) {
    			if(tree.health < minHealth) {
    				minHealthTree = tree;
    				minHealth = tree.health;
    			}
    		}
    	}
    	if(minHealthTree != null) gardener.water(minHealthTree.location);

    	if(adjacentTrees.length >= 5) broadcastDonePlanting();
    }

    public void findNicePlaceToFarm() throws GameActionException {
    	Direction dir = averageEnemyStartingLocation.directionTo(gardener.getLocation());
    	while(gardener.onTheMap(gardener.getLocation().add(dir, 3))) {
    		moveAwayFromEnemyCenter();
    	}


    	MapLocation eastLocation = gardener.getLocation().add(Direction.getEast(), 3);
    	MapLocation northLocation = gardener.getLocation().add(Direction.getNorth(), 3);
    	MapLocation southLocation = gardener.getLocation().add(Direction.getSouth(), 3);
    	MapLocation westLocation = gardener.getLocation().add(Direction.getWest(), 3);
    	if(gardener.hasMoved()) Clock.yield();

    	if(!gardener.onTheMap(eastLocation)) moveIsh(Direction.getWest());
    	else if(!gardener.onTheMap(northLocation)) moveIsh(Direction.getSouth());
    	else if(!gardener.onTheMap(southLocation)) moveIsh(Direction.getNorth());
    	else if(!gardener.onTheMap(westLocation)) moveIsh(Direction.getEast());

    }

    public void moveIsh(Direction dir) throws GameActionException {
    	if(gardener.hasMoved()) Clock.yield();
    	for(int i = 0; i <= 90; i+=5) {
    		if(gardener.canMove(dir.rotateLeftDegrees(i))) { gardener.move(dir.rotateLeftDegrees(i)); break; }
    		if(gardener.canMove(dir.rotateRightDegrees(i))) { gardener.move(dir.rotateRightDegrees(i)); break; }
    	}
    }

    public void moveAwayFromEnemyCenter() throws GameActionException {
    	if(gardener.hasMoved()) Clock.yield();
    	Direction dir = averageEnemyStartingLocation.directionTo(gardener.getLocation());
    	for(int i = 0; i <= 90; i+=5) {
    		if(gardener.canMove(dir.rotateLeftDegrees(i))) { gardener.move(dir.rotateLeftDegrees(i)); break; }
    		if(gardener.canMove(dir.rotateRightDegrees(i))) { gardener.move(dir.rotateRightDegrees(i)); break; }
    	}
    }

    public void signalRobotsToMoveAway() throws GameActionException {
    	// TODO
    }

    public void buildFirstUnits() throws GameActionException {

        // check trees for neutral robots
        TreeInfo[] nearbyNeutralTrees = gardener.senseNearbyTrees(gardener.getType().sensorRadius, Team.NEUTRAL);
        TreeInfo nearbyTreeWithRobots = null;
        for(TreeInfo tree : nearbyNeutralTrees) if(tree.containedRobot != null) { nearbyTreeWithRobots = tree; break; }

        // check allied robots for lumberjacks
        RobotInfo[] nearbyAlliedRobots = gardener.senseNearbyRobots(gardener.getType().sensorRadius, gardener.getTeam());
        RobotInfo lumberjack = null;
        for(RobotInfo robot : nearbyAlliedRobots) if(robot.getType() == RobotType.LUMBERJACK) { lumberjack = robot; break; }

        // force-build a lumberjack if none found, and a neutral robot is to be harvested
        if(nearbyTreeWithRobots != null && lumberjack == null) {
            forceBuild(RobotType.LUMBERJACK, gardener.getLocation().directionTo(nearbyTreeWithRobots.location));
            gardener.broadcast(Globals.LUMBER_ASSIGNMENT_CHAN, Globals.FARMING);
        }

        // force-build a scout
        forceBuild(RobotType.SCOUT, Direction.getEast());
        gardener.broadcast(Globals.SCOUT_ASSIGNMENT_CHAN, Globals.FARMING);
    }

    public void forceBuild(RobotType type, Direction dir) throws GameActionException {
        while(!build(type, dir));
    }

    public boolean build(RobotType type, Direction dir) throws GameActionException {
        for(int i = 0; i < 360; i++) {
            if(gardener.canBuildRobot(type, dir.rotateLeftDegrees(i))){
                gardener.buildRobot(type, dir.rotateLeftDegrees(i));
                return true;
            }
        }
        return false;
    }


}
