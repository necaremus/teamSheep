package shepderp.Bot.Behaviour.ArchonBehaviour;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import shepderp.Bot.Archon;
import shepderp.Bot.Bot;

/**
 * Created by necaremus on 17.01.17.
 */
public class IdleArchonBehaviour extends ArchonBehaviour {
    public Bot archon;
    public IdleArchonBehaviour(Archon archon) {
        this.archon = archon;
    }
    public void execute() throws GameActionException {
        rc.setIndicatorLine(rc.getLocation(), archon.predictedCenterOfMap, 0, 0, 0);
        debug_setIndicatorLinesPredictedMap();

    }
}
