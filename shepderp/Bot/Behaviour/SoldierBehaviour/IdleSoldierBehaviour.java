package shepderp.Bot.Behaviour.SoldierBehaviour;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import shepderp.Bot.Behaviour.ArchonBehaviour.ArchonBehaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public class IdleSoldierBehaviour extends SoldierBehaviour {
    public void execute() throws GameActionException {
        Clock.yield();
    }
}
