package testplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import testplayer.Behaviour.ScoutFarmingBehaviour;

public class Scout {

	RobotController scout;

	public Scout(RobotController rc) {
		scout = rc;
	}

	public void run() throws GameActionException {
		assignScoutBehaviour();
	}

	public void assignScoutBehaviour() throws GameActionException {
		int behaviour = scout.readBroadcast(Globals.SCOUT_ASSIGNMENT_CHAN);
		scout.broadcast(Globals.SCOUT_ASSIGNMENT_CHAN, Globals.EMPTY);
		switch(behaviour) {
			case Globals.FARMING:		(new ScoutFarmingBehaviour(this.scout)).run(); 	break;
		}
	}

}
