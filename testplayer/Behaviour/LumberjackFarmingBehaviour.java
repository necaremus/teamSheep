package testplayer.Behaviour;

import java.util.ArrayList;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Team;
import battlecode.common.TreeInfo;

public class LumberjackFarmingBehaviour {

	RobotController lumberjack;
	ArrayList<MapLocation> neutralTreesRobotLocations = new ArrayList<MapLocation>();

	public LumberjackFarmingBehaviour(RobotController rc) {
		lumberjack = rc;
	}

	public void run() throws GameActionException {
		while(true) {
			listenToBroadcast();
			maybeDodgeAttacks();
			scanForNeutralTrees();
			chopNeutralRobots();
			Clock.yield();
		}
	}


	public void listenToBroadcast() throws GameActionException {
		// TODO
	}

	public void maybeDodgeAttacks() throws GameActionException {
		// TODO
	}

	public void scanForNeutralTrees() throws GameActionException {
		// sense nearby neutral robots
		TreeInfo[] nearbyNeutralTrees = lumberjack.senseNearbyTrees(lumberjack.getType().sensorRadius, Team.NEUTRAL);
		for(TreeInfo tree : nearbyNeutralTrees) {
			if(tree.containedRobot != null && !neutralTreesRobotLocations.contains(tree.location)) neutralTreesRobotLocations.add(tree.location);
		}
	}

	public void chopNeutralRobots() throws GameActionException {
		// find nearest location to chop a neutral robot
		MapLocation nearest = null;
		float minDist = Float.MAX_VALUE;
		for(MapLocation location : neutralTreesRobotLocations) {
			if(location.distanceTo(lumberjack.getLocation()) < minDist) {
				minDist = location.distanceTo(lumberjack.getLocation());
				nearest = location;
			}
		}
		if(nearest == null) return;

		// if too far away to chop, move closer
		if(lumberjack.getLocation().distanceTo(nearest) > lumberjack.getType().strideRadius) {
			if(!lumberjack.hasMoved()) {
				if(lumberjack.canMove(nearest)) lumberjack.move(nearest);
				else {
					Direction dir = lumberjack.getLocation().directionTo(nearest);
					for(int i = 5; i <= 90; i+=5) {
						if(lumberjack.canMove(dir.rotateLeftDegrees(i))) { lumberjack.move(dir.rotateLeftDegrees(i)); break; }
						if(lumberjack.canMove(dir.rotateRightDegrees(i))) { lumberjack.move(dir.rotateRightDegrees(i)); break; }
					}
				}
			}
		}

		// if there's any choppable location nearby, chop that if possible
		if(lumberjack.canChop(nearest)) neutralTreesRobotLocations.remove(nearest);
		while(lumberjack.canChop(nearest)) {
			lumberjack.chop(nearest);
			Clock.yield();
		}

	}


}
