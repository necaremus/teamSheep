package shepderp.Bot;

import battlecode.common.*;
import shepderp.Bot.Behaviour.Behaviour;
import shepderp.Bot.Utilities.HelperClasses.Body;
import shepderp.Bot.Utilities.Radio;
import shepderp.Bot.Utilities.Util;
import shepderp.Constants;

import java.util.ArrayList;


/**
 * Created by necaremus on 17.01.17.
 *
 * This is the abstract Bot.
 * vars all bots need go here.
 */
public abstract class Bot {

    public abstract Behaviour getBehaviour() throws GameActionException;
    public RobotController rc;
    public static Behaviour behaviour;
    public Radio radio;
    public Constants.MapSymmetry mapSymmetry = Constants.MapSymmetry.NONE_DETERMINED;
    public int initialArchonCount;
    public int[] mapSize = new int[] {0, 0};
    public boolean isStuck, turnedLeft, turnedRight = false;
    //public Direction moveDir;
    public MapLocation enemyAvgStartLocation, predictedCenterOfMap;
    public ArrayList<Body> bodies = new ArrayList<>();
    public float myMoveRad = 0;

    public void run(RobotController rc) throws GameActionException {
        this.rc = rc;
        init();
        while(true) {
            //if (rc.getTeamBullets() >= 10000) rc.donate(10000);
            int round = rc.getRoundNum();
            behaviour = getBehaviour();
            behaviour.bot = this;
            behaviour.rc = rc;
            behaviour.execute();
            if (round == rc.getRoundNum()) { fuckTheServer(); Clock.yield(); }
            else System.out.println("!! too expensive somewhere !!");
        }
    }

    // i like to troll
    private void fuckTheServer() throws GameActionException {
        int limit = rc.getType().bytecodeLimit - 20;
        int bytecodes = Clock.getBytecodeNum();
        System.out.println("Bytecodes used: " + bytecodes);
        //rc.resign(); //TODO CHECK HERE
        while (bytecodes < limit) bytecodes = Clock.getBytecodeNum();
    }

    //region init
    private void init() throws GameActionException {
        debug_sayHello();
        radio = new Radio(rc);
        if (getLocations()) debug_PrintStuff();
    }

    private void debug_sayHello() throws GameActionException {
        switch (rc.getType()) {
            case ARCHON: System.out.println("Me be archon");        break;
            case GARDENER: System.out.println("Me be building");    break;
            case LUMBERJACK: System.out.println("me lumber");       break;
            case SCOUT: System.out.println("me scout");             break;
            case SOLDIER: System.out.println("me fight");           break;
            case TANK: System.out.println("I TANK");                break;
        }
    }

    public boolean getLocations() throws GameActionException {
        if (rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY) % 10 == 0) return false;
        predictedCenterOfMap = radio.getPredictedCenterOfMap();
        enemyAvgStartLocation = radio.getEnemyAvgStartLocation();
        mapSymmetry = radio.getMapSymmetry();
        mapSize = radio.getMapSize();
        return true;
    }

    public void debug_PrintStuff() throws GameActionException {
        System.out.println("DEBUG: got locations");
        System.out.println("MapSymmetry: " + Util.mapSymmetryToInt(mapSymmetry));
        System.out.println("MapSize X*Y: " + mapSize[0] + "*" + mapSize[1]);
        System.out.println("PredictedCenterOfMap: " + predictedCenterOfMap.x + ", " + predictedCenterOfMap.y);
        System.out.println("EnemyAvgStartLocation: " + enemyAvgStartLocation.x + ", " + enemyAvgStartLocation.y);
    }
    //endregion

}
