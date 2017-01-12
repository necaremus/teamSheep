package testplayer.Behaviour;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import battlecode.common.Team;
import battlecode.common.TreeInfo;
import testplayer.Globals;

/*
 * Turn #0: Hire Gardener for farming
 * Turn #10: Hire Gardener for farming
 * Then: Wait
 */

public class ArchonFarmingLeaderBehaviour {

	RobotController archon;
	final static int MAX_AMOUNT_GARDENER = 1;
	int amountGardener = 0;



	public ArchonFarmingLeaderBehaviour(RobotController rc) {
		archon = rc;
	}



	public void run() throws GameActionException {
		while(true) {
			hire();
			Clock.yield();
		}
	}



	/* hires a new gardener */
	public void hire() throws GameActionException {
		if(amountGardener < MAX_AMOUNT_GARDENER) {
			Direction hireDirection = getHireDirection();
			if(hireInDirection(hireDirection)) {
				amountGardener++;
				archon.broadcast(Globals.GARDENER_ASSIGNMENT_CHAN, Globals.FARMING);
			}
		}
	}



	/* hires a gardener in the given direction, iff has enough bullets, and has no cooldown
	 * turns the direction by 1 degrees left until can built*/
	public boolean hireInDirection(Direction hireDirection) throws GameActionException {
		if(archon.getTeamBullets() < RobotType.GARDENER.bulletCost || !archon.isBuildReady()) return false;

		for(int i = 0; i <= 360; i++) {
			if(archon.canHireGardener(hireDirection.rotateLeftDegrees(i))) {
				archon.hireGardener(hireDirection.rotateLeftDegrees(i));
				return true;
			}
		}

		return false;
	}


	/* returns a direction towards the best nearby neutral tree,
	 * or towards the enemy's average starting location, if no good neutral tree can be seen */
	public Direction getHireDirection() throws GameActionException {
		TreeInfo neutralTree = getBestNearbyNeutralTree();
		if(neutralTree == null)return archon.getLocation().directionTo(getAverageEnemyStartingLocation());
		else return archon.getLocation().directionTo(neutralTree.location);
	}



	/* returns the neutral tree in sensor radius with most the most bullets */
	public TreeInfo getBestNearbyNeutralTree() throws GameActionException {
		TreeInfo[] nearbyNeutralTrees = archon.senseNearbyTrees(archon.getType().sensorRadius, Team.NEUTRAL);
		TreeInfo maxBulletTree = null;
		int maxContainedBullets = -1;
		for(TreeInfo tree : nearbyNeutralTrees) {
			if(tree.containedBullets > maxContainedBullets) {
				maxContainedBullets = tree.containedBullets;
				maxBulletTree = tree;
			}
		}
		return maxBulletTree;
	}



	/* calculates and returns the average starting location of the enemy's archons, using getInitialArchonLocations */
	public MapLocation getAverageEnemyStartingLocation() throws GameActionException {
		MapLocation[] enemyStartingLocations = archon.getInitialArchonLocations(archon.getTeam().opponent());
		float averageLocationX = 0, averageLocationY = 0;
		for(MapLocation location : enemyStartingLocations) {
			averageLocationX += location.x;
			averageLocationY += location.y;
		}
		averageLocationX /= enemyStartingLocations.length;
		averageLocationY /= enemyStartingLocations.length;
		return new MapLocation(averageLocationX, averageLocationY);
	}


}
















