package susuPlayer.Bot.Behaviour.Archon;

import battlecode.common.*;
import susuPlayer.Constants;

/**
 * initially, archons should always start by building a gardener,
 * shake nearby trees, and wait for further instructions.
 *
 */

public class InitialArchonBehaviour extends ArchonBehaviour {

	RobotController archon;

	MapLocation myStartLocation, enemyStartLocation, predictedCenterOfMap;
	/*
	 *	returns true if a gardener should be hired.
	 *	initially should hire exactly one gardener.
	 *	should hire another gardener if another one is needed -> TODO
	 */
	public boolean shouldHireGardener() throws GameActionException {
		// return true if no gardener has been hired yet
		if(archon.getRoundNum() <= 1 && archon.getTeamBullets() >= GameConstants.BULLETS_INITIAL_AMOUNT) return true;

		// return true if amount of gardeners is less than a certain threshhold value
		int amountOfGardeners = 1;// TODO : implement get amount of <type> function
		int threshHoldGardeners = 1; // TODO : calculate threshhold for min. amount of gardeners
		return amountOfGardeners < threshHoldGardeners;
	}

	// hires a gardener roughly in the direction of the enemy
	public boolean hireGardener() throws GameActionException {
		Direction preferredDirection = Direction.getEast();// TODO: get preferred building direction
		return hireGardener(preferredDirection);
	}

	public void execute() throws GameActionException {
		if(archon == null) archon = executer.getController();
		if (hello) {
			System.out.println("ME ARCHON!");
			hello = !hello;
		}
		getLocations();
		if(shouldHireGardener()) {
			build(RobotType.GARDENER, myStartLocation.directionTo(enemyStartLocation));
			System.out.println("build Gardener");
		}
		Clock.yield();
	}

	private void getLocations() throws GameActionException {
		MapLocation[] myLocations = archon.getInitialArchonLocations(archon.getTeam());
		MapLocation[] enemyLocations = archon.getInitialArchonLocations(archon.getTeam().opponent());
		if (myLocations.length > 1) {
			myStartLocation = averageLocation(myLocations);
			enemyStartLocation = averageLocation(enemyLocations);
		} else {
			myStartLocation = myLocations[0];
			enemyStartLocation = enemyLocations[0];
		}
		predictedCenterOfMap = new MapLocation((myStartLocation.x + enemyStartLocation.x) / 2, (myStartLocation.y + enemyStartLocation.y) / 2);
		System.out.println("determined locations.");
		System.out.println("enemy avgSart: x: " + enemyStartLocation.x + " y: " + enemyStartLocation.y);
		System.out.println("my avgStart: x: " + myStartLocation.x + " y: " + myStartLocation.y);
		System.out.println("middle point: x: " + predictedCenterOfMap.x + " y: " + predictedCenterOfMap.y);
		executer.mapSymmetry = determineSymmetry(myLocations, enemyLocations);
		printlnSymmetry();
		archon.resign();
	}

    private MapLocation averageLocation(MapLocation[] mapLocations) throws GameActionException {
		float avgLocX = 0, avgLocY = 0;
		for (MapLocation mapLocation : mapLocations) {
			avgLocX += mapLocation.x;
			avgLocY += mapLocation.y;
		}
		avgLocX /= mapLocations.length;
		avgLocY /= mapLocations.length;
		return new MapLocation(avgLocX, avgLocY);
	}



	private Constants.MapSymmetry determineSymmetry (MapLocation[] myLocs, MapLocation[] enemyLocs) throws GameActionException {
	    if (myLocs.length < 1) return Constants.MapSymmetry.NONE_DETERMINED;
	    if (checkAllX(myLocs, enemyLocs)) {
	        if (myLocs.length == 1) return Constants.MapSymmetry.HORIZONTAL_OR_ROTATIONAL;
	        return  Constants.MapSymmetry.HORIZONTAL;
        }
        if (checkAllY(myLocs, enemyLocs)) {
	        if (myLocs.length == 1) return Constants.MapSymmetry.VERTICAL_OR_ROTATIONAL;
	        return Constants.MapSymmetry.VERTICAL;
        }
	    return Constants.MapSymmetry.ROTATIONAL;
    }
    private boolean checkAllX(MapLocation[] myLocs, MapLocation[] enemyLocs) throws GameActionException {
	    for (int i = (myLocs.length - 1); i < myLocs.length; i++) {
            if (myLocs[i].x != enemyLocs[i].x) return false;
        }
        return true;
    }
    private boolean checkAllY(MapLocation[] myLocs, MapLocation[] enemyLocs) throws GameActionException {
        for (int i = (myLocs.length - 1); i < myLocs.length; i++) {
            if (myLocs[i].y != enemyLocs[i].y) return false;
        }
        return true;
    }
    private void printlnSymmetry() throws GameActionException {
	    switch (executer.mapSymmetry) {
            case HORIZONTAL: System.out.println("horizontal"); break;
            case VERTICAL: System.out.println("vertical"); break;
            case ROTATIONAL: System.out.println("rotational"); break;
            case HORIZONTAL_OR_ROTATIONAL: System.out.println("horizontal or rotational"); break;
            case VERTICAL_OR_ROTATIONAL: System.out.println("vertical or rotational"); break;
            default: System.out.println("dafuq");
        }
    }
	/*
	public Constants.MapSymmetry determineSymmetry(MapLocation[] myInitialLocations, MapLocation[] enemyInitialLocations) throws GameActionException {
		System.out.println("determining symmetry");
		System.out.println(myInitialLocations.length);
		boolean bLocs = (myInitialLocations.length > 1);
		// check horizontal or rotational
		if (myInitialLocations[0].x == enemyInitialLocations[0].x) {
		    System.out.println("horizontal or rotational");
            if (!bLocs) return Constants.MapSymmetry.HORIZONTAL_OR_ROTATIONAL;
            else {
                if (myInitialLocations[1].x == enemyInitialLocations[1].x) {
                    System.out.println("horizontal");
                    return Constants.MapSymmetry.HORIZONTAL;
                } else {
                    System.out.println("rotational");
                    return Constants.MapSymmetry.ROTATIONAL;
                }
            }
        }
		// check vertical or rotational
		if (myInitialLocations[0].y == enemyInitialLocations[0].y) {
		    System.out.println("vertical or rotational");
		    if (!bLocs) return Constants.MapSymmetry.VERTICAL_OR_ROTATIONAL;
		    else {
		        if (myInitialLocations[1].y == enemyInitialLocations[1].y) {
		            System.out.println("vertical");
		            return Constants.MapSymmetry.VERTICAL;
                } else {
		            System.out.println("rotational");
		            return Constants.MapSymmetry.ROTATIONAL;
                }
            }
        }
        System.out.println("dafuq");
		return Constants.MapSymmetry.NONE_DETERMINED;

	} //*/

}
