package shepderp.Bot.Behaviour.LumberjackBehaviour;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import shepderp.Bot.Bot;

/**
 * Created by necaremus on 17.01.17.
 */
public class IdleLumberjackBehaviour extends LumberjackBehaviour {
    public Bot lumber;

    public IdleLumberjackBehaviour(Bot lumber) { this.lumber = lumber; }
    public void execute() throws GameActionException {
        Clock.yield();
    }
}
