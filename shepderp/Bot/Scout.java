package shepderp.Bot;

import battlecode.common.GameActionException;
import battlecode.common.TreeInfo;
import shepderp.Bot.Behaviour.Behaviour;
import shepderp.Bot.Behaviour.ScoutBehaviour.BulletFarmingScoutBehaviour;
import shepderp.Bot.Behaviour.ScoutBehaviour.IdleScoutBehaviour;

import java.util.ArrayList;

/**
 * Created by necaremus on 17.01.17.
 */
public class Scout extends Bot {
    public ArrayList<TreeInfo> bulletTrees = new ArrayList<>();
    public Behaviour getBehaviour() throws GameActionException {
        if(behaviour == null) return new BulletFarmingScoutBehaviour(this);
        return new BulletFarmingScoutBehaviour(this);
    }
}
