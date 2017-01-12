package testplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import testplayer.Behaviour.LumberjackFarmingBehaviour;

public class Lumberjack {

	RobotController lumberjack;

	public Lumberjack(RobotController rc) {
		lumberjack = rc;
	}


	public void run() throws GameActionException {
		assignLumberBehaviour();
	}

	public void assignLumberBehaviour() throws GameActionException {
		int behaviour = lumberjack.readBroadcast(Globals.LUMBER_ASSIGNMENT_CHAN);
		lumberjack.broadcast(Globals.LUMBER_ASSIGNMENT_CHAN, Globals.EMPTY);
		switch(behaviour) {
			case Globals.FARMING:		(new LumberjackFarmingBehaviour(this.lumberjack)).run(); 	break;
		}
	}

}
