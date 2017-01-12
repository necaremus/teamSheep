package testplayer;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Tank {

	RobotController tank;
	public Tank(RobotController rc) {
		tank = rc;
	}

	public void run() throws GameActionException {
		while(true) {
			Clock.yield();
		}
	}

}
