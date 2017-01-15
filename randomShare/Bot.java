package susuPlayer.Bot;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import susuPlayer.Bot.Behaviour.Behaviour;
import susuPlayer.Bot.Utilities.NavigationSystem;
import susuPlayer.Bot.Utilities.Radar;
import susuPlayer.Bot.Utilities.Radio;
import susuPlayer.Constants;

public abstract class Bot {

	public abstract Behaviour getBehaviour() throws GameActionException;
	public static RobotController rc;
	public static Behaviour behaviour;
	public static Radar radar;
	public static Radio radio;
	public static NavigationSystem navi;
	//*
	private static int ROTATE_INTERVAL = 5;
	//*/
	public RobotController getController() { return rc; }

	public Constants.MapSymmetry mapSymmetry = Constants.MapSymmetry.NONE_DETERMINED;

	public void run() throws GameActionException {
		while(true) {
			behaviour = getBehaviour();
			behaviour.executer = this;
			behaviour.execute();
		}
	}
	public boolean build(RobotType type) throws GameActionException {
	    return build(type, Direction.getEast(), 180);
    }
	public boolean build(RobotType type, Direction dir) throws GameActionException {
		return build(type, dir, 180);
	}
	public boolean build(RobotType type,Direction dir, int maxRotateDegrees) throws GameActionException {
		if ((type.bulletCost > rc.getTeamBullets())
				|| rc.getType() != RobotType.ARCHON
				|| rc.getType() != RobotType.GARDENER
				) return false;
		if (!rc.hasRobotBuildRequirements(type)) return false;

		return tryBuild(type, dir, maxRotateDegrees);
	}
	private boolean tryBuild(RobotType type, Direction dir, int maxRotateDegrees) throws GameActionException {
		if (rc.canBuildRobot(type, dir)) { rc.buildRobot(type, dir); return true; }
		return tryRotateBuild(type, dir, maxRotateDegrees, ROTATE_INTERVAL);
	}
	private boolean tryRotateBuild(RobotType type, Direction dir, int maxRotateDegrees, int rotateInterval) throws GameActionException {
		if (maxRotateDegrees > 180) maxRotateDegrees = 180;
	    for (int i = rotateInterval; i <= maxRotateDegrees; i += rotateInterval) {
			if (rc.canBuildRobot(type, dir.rotateLeftDegrees(i))) { rc.buildRobot(type, dir.rotateLeftDegrees(i)); return true; }
			if (rc.canBuildRobot(type, dir.rotateRightDegrees(i))) { rc.buildRobot(type, dir.rotateRightDegrees(i)); return true; }
		}
		return false;
	}

}
