package shepderp;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import shepderp.Bot.*;

/**
 * Created by necaremus on 17.01.17.
 *
 * main class
 */
public class RobotPlayer {

    /*
     * here starts every bot.
     * run code dependent on RobotType
     */
    public static void run(RobotController rc) throws GameActionException {
        switch(rc.getType()) {
            case ARCHON:		(new Archon()).run(rc);  	break;
            case GARDENER:		(new Gardener()).run(rc);  	break;
            case SCOUT:			(new Scout()).run(rc);  	break;
            case SOLDIER:		(new Soldier()).run(rc);  	break;
            case TANK:			(new Tank()).run(rc);  		break;
            case LUMBERJACK:	(new Lumberjack()).run(rc); break;
        }
    }
}
