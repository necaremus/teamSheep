package noobPlayer;
import battlecode.common.*;

public strictfp class RobotPlayer {
    static RobotController rc;

    public static void run(RobotController rc) throws GameActionException {

        RobotPlayer.rc = rc;

        switch (rc.getType()) {
            case ARCHON:
                archonBot.run(rc);
                break;
            case GARDENER:
                gardenerBot.run(rc);
                break;
            case SCOUT:
            	scoutBot.run(rc);
            	break;
            case SOLDIER:
                soldierBot.run(rc);
                break;
            case LUMBERJACK:
                lumberjackBot.run(rc);
            	break;
            case TANK:
            	tankBot.run(rc);
            	break;
        }
	}
}
