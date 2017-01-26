package shepderp.Bot;

import battlecode.common.GameActionException;
import shepderp.Bot.Behaviour.Behaviour;
import shepderp.Bot.Behaviour.LumberjackBehaviour.SpaceLumberBehaviour;
import shepderp.Bot.Utilities.HelperClasses.Body;

import java.util.ArrayList;

/**
 * Created by necaremus on 17.01.17.
 */
public class Lumberjack extends Bot {

    public ArrayList<Body> nBodies = new ArrayList<>();
    public ArrayList<Body> bBodies = new ArrayList<>();
    public Behaviour getBehaviour() throws GameActionException {
        if(behaviour == null) return new SpaceLumberBehaviour(this);
        //if(behaviour instanceof IdleLumberjackBehaviour) return behaviour;
        return new SpaceLumberBehaviour(this);
    }
}
