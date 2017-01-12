package testplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import testplayer.Behaviour.GardenerFarmingBehaviour;

public class Gardener {

	RobotController gardener;



	public Gardener(RobotController rc) {
		gardener = rc;
	}



	public void run() throws GameActionException {
		assignGardenerBehaviour();
	}


	public void assignGardenerBehaviour() throws GameActionException {
		int behaviour = gardener.readBroadcast(Globals.GARDENER_ASSIGNMENT_CHAN);
		gardener.broadcast(Globals.GARDENER_ASSIGNMENT_CHAN, Globals.EMPTY);
		switch(behaviour) {
			case Globals.FARMING:		(new GardenerFarmingBehaviour(this.gardener)).run(); 	break;
		}
	}

}
