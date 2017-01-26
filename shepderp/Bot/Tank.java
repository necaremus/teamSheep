package shepderp.Bot;

import battlecode.common.GameActionException;
import shepderp.Bot.Behaviour.Behaviour;
import shepderp.Bot.Behaviour.TankBehaviour.IdleTankBehaviour;

/**
 * Created by necaremus on 17.01.17.
 */
public class Tank extends Bot {
    public Behaviour getBehaviour() throws GameActionException {
        if(behaviour == null) return new IdleTankBehaviour();
        if(behaviour instanceof IdleTankBehaviour) return behaviour;
        return new IdleTankBehaviour();
    }
}
