package shepderp.Bot;

import battlecode.common.GameActionException;
import shepderp.Bot.Behaviour.Behaviour;
import shepderp.Bot.Behaviour.SoldierBehaviour.IdleSoldierBehaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public class Soldier extends Bot {
    public Behaviour getBehaviour() throws GameActionException {
        if(behaviour == null) return new IdleSoldierBehaviour();
        if(behaviour instanceof IdleSoldierBehaviour) return behaviour;
        return new IdleSoldierBehaviour();
    }
}
