package shepderp.Bot.Behaviour.ArchonBehaviour;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import shepderp.Bot.Behaviour.Behaviour;
/**
 * Created by necaremus on 17.01.17.
 */
public abstract class ArchonBehaviour extends Behaviour {

    public boolean shouldHireGardener() throws GameActionException {
        return rc.getRoundNum() <= 10; //TODO
    }

    public void debug_setIndicatorLinesPredictedMap() throws GameActionException {
        float r = rc.getType().sensorRadius;
        float top = bot.predictedCenterOfMap.y + (bot.mapSize[1] > 0 ? (bot.mapSize[1] / 2) : r);
        float bottom = bot.predictedCenterOfMap.y - (bot.mapSize[1] > 0 ? (bot.mapSize[1] / 2) : r);
        float right = bot.predictedCenterOfMap.x + (bot.mapSize[0] > 0 ? (bot.mapSize[0] / 2) : r);
        float left = bot.predictedCenterOfMap.x - (bot.mapSize[0] > 0 ? (bot.mapSize[0] / 2) : r);
        debug_drawPredictedMapSize(top, bottom, right, left);
    }

    private void debug_drawPredictedMapSize(float top, float bottom, float right, float left) throws GameActionException {
        rc.setIndicatorLine(new MapLocation(left, bottom), new MapLocation(right, bottom), 0, 0, 0);
        rc.setIndicatorLine(new MapLocation(left, bottom), new MapLocation(left, top), 0, 0, 0);
        rc.setIndicatorLine(new MapLocation(left, top), new MapLocation(right, top), 0, 0, 0);
        rc.setIndicatorLine(new MapLocation(right, top), new MapLocation(right, bottom), 0, 0, 0);
    }
}
