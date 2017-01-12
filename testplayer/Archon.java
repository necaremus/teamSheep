package testplayer;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import testplayer.Behaviour.ArchonFarmingLeaderBehaviour;
import testplayer.Globals;

public class Archon {

	RobotController archon;
	public Archon(RobotController rc) { archon = rc; }

	public void run() throws GameActionException {
		assignArchonBehaviour();
	}

	public void assignArchonBehaviour() throws GameActionException {
		if(archon.readBroadcast(Globals.ARCHON_CHAN_FARM) == 0) {
			archon.broadcast(Globals.ARCHON_CHAN_FARM, 1);
			(new ArchonFarmingLeaderBehaviour(this.archon)).run();
		} else archon.disintegrate(); // TODO: non-farming archons behaviour
	}

}
