package shepderp.Bot;

import battlecode.common.GameActionException;
import shepderp.Bot.Behaviour.Behaviour;
import shepderp.Bot.Behaviour.GardenerBehaviour.IdleGardenerBehaviour;
import shepderp.Bot.Behaviour.GardenerBehaviour.InitialGardenerBehaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public class Gardener extends Bot {
    public Behaviour getBehaviour() throws GameActionException {
        if(behaviour == null) return new InitialGardenerBehaviour(this);
        if(behaviour instanceof IdleGardenerBehaviour) return behaviour;
        return new InitialGardenerBehaviour(this);
    }
}
