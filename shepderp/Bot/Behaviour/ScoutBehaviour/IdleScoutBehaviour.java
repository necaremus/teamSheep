package shepderp.Bot.Behaviour.ScoutBehaviour;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import shepderp.Bot.Behaviour.ArchonBehaviour.ArchonBehaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public class IdleScoutBehaviour extends ScoutBehaviour {
    public void execute() throws GameActionException {
        Clock.yield();
    }
}
