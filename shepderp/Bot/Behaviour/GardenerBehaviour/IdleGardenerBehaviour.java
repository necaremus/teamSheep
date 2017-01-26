package shepderp.Bot.Behaviour.GardenerBehaviour;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import shepderp.Bot.Behaviour.ArchonBehaviour.ArchonBehaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public class IdleGardenerBehaviour extends GardenerBehaviour {
    public void execute() throws GameActionException {
        Clock.yield();
    }
}
