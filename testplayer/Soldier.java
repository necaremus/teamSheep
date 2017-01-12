package testplayer;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Soldier {

	RobotController soldier;

	public Soldier(RobotController rc) {
		soldier = rc;
	}

	public void run() throws GameActionException {
		while(true) {
			Clock.yield();
		}
	}

}
