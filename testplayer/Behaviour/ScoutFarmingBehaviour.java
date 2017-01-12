package testplayer.Behaviour;


import java.util.ArrayList;
import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.Team;
import battlecode.common.TreeInfo;


public class ScoutFarmingBehaviour {

	RobotController scout;
	ArrayList<MapLocation> neutralTreesBulletsLocations = new ArrayList<MapLocation>();

	public ScoutFarmingBehaviour(RobotController rc) {
		scout = rc;
	}

	public void run() throws GameActionException {
		while(true) {
			listenToBroadcast();
			maybeDodgeAttacks();
			relayInformationAboutNearbyHostiles();
			scanForNeutralTrees();
			harvestNeutralTrees();
			Clock.yield();
		}
	}


	public void listenToBroadcast() throws GameActionException {
		// TODO
	}

	public void maybeDodgeAttacks() throws GameActionException {
		// TODO
	}

	public void relayInformationAboutNearbyHostiles() throws GameActionException {
		// TODO
	}

	public void scanForNeutralTrees() throws GameActionException {
		TreeInfo[] nearbyNeutralTrees = scout.senseNearbyTrees(scout.getType().sensorRadius, Team.NEUTRAL);
		for(TreeInfo tree : nearbyNeutralTrees) {
			if(tree.containedBullets > 0 && !neutralTreesBulletsLocations.contains(tree.location)) neutralTreesBulletsLocations.add(tree.location);
			if(tree.containedBullets <= 0 && neutralTreesBulletsLocations.contains(tree.location)) neutralTreesBulletsLocations.remove(tree.location);
		}
	}

	public void harvestNeutralTrees() throws GameActionException {
		// find nearest tree
		MapLocation nearest = null;
		float minDist = Float.MAX_VALUE;
		for(MapLocation treeLocation : neutralTreesBulletsLocations) {
			if(scout.getLocation().distanceTo(treeLocation) < minDist) {
				minDist = scout.getLocation().distanceTo(treeLocation);
				nearest = treeLocation;
			}
		}

		if(nearest != null) {
			scout.setIndicatorLine(scout.getLocation(), nearest, 255, 0, 0);
		}

		// move towards nearest tree
		if(!scout.hasMoved() && nearest != null) {
			if(scout.canMove(nearest)) scout.move(nearest);
			else {
				Direction dir = scout.getLocation().directionTo(nearest);
				for(int i = 1; i <= 45; i++) {
					if(scout.canMove(dir.rotateLeftDegrees(i))) { scout.move(dir.rotateLeftDegrees(i)); break; }
					if(scout.canMove(dir.rotateRightDegrees(i))) { scout.move(dir.rotateRightDegrees(i)); break; }
				}
			}
		}

		// shake nearest tree
		if(nearest != null && scout.canShake(nearest)) scout.shake(nearest);


		if (nearest == null) headlessChicken(); // TODO: if nothing left to harvest, go scouting
	}


	public void headlessChicken() throws GameActionException {
		float random = (float)(Math.random()*360);
		if(scout.canMove(Direction.getEast().rotateLeftDegrees(random))) scout.move(Direction.getEast().rotateLeftDegrees(random));
	}

}









