package shepderp.Bot.Behaviour.ArchonBehaviour;

import battlecode.common.*;
import shepderp.Bot.Archon;
import shepderp.Bot.Utilities.Geometry;
import shepderp.Bot.Utilities.HelperClasses.Body;
import shepderp.Bot.Utilities.Util;
import shepderp.Constants;

import java.util.ArrayList;

/**
 * Created by necaremus on 17.01.17.
 */
public class InitialArchonBehaviour extends ArchonBehaviour {
    public InitialArchonBehaviour(Archon archon) { this.archon = archon; }
    public Archon archon;
    private boolean init = true;

    public ArrayList<float[]> tauRads = new ArrayList<>();
    public void execute() throws GameActionException {
        //*
        if (init) {
            if (!archon.getLocations() && determineLocations()) broadcastLocations();
            if (archon.initialArchonCount == 1 || archon.isClosestToMapEdgeX()) {
                int x = (int) determineMapEdgeX();
                archon.radio.broadcastMapSizeX(x);
                archon.mapSize[0] = x;
            }
            if (archon.initialArchonCount == 1 || archon.isClosestToMapEdgeY()) {
                int y = (int) determineMapEdgeY();
                archon.radio.broadcastMapSizeY(y);
                archon.mapSize[1] = y;
                //TODO broadcast
            }
            init = !init;
        }//*/
        testStuff();
        /*
        rc.setIndicatorLine(rc.getLocation(), archon.predictedCenterOfMap, 0, 0, 0);
        //debug_setIndicatorLinesPredictedMap();
        if (shouldHireGardener() && build(RobotType.GARDENER)) archon.gardenersHired++;


        //archon.debug_PrintStuff();
        System.out.println("Bytecodes used: " + Clock.getBytecodeNum());
        //*/
    }
    private void testStuff() throws GameActionException {
        Body myBody = new Body(rc.getLocation(), rc.getType().bodyRadius);
        TreeInfo[] trees = rc.senseNearbyTrees();
        for (int i = trees.length; --i >= 0;)
            tauRads.add(Geometry.getTangentRads(myBody, new Body(trees[i].location, trees[i].radius)));
        sortRads();
        //rc.move(new Direction(archon.myMoveRad));
        //rc.resign();
    }
    private void sortRads() throws GameActionException {
        int j;
        for (int i = tauRads.size(); --i >= 0;) {
            j = sortRads(tauRads.get(i));
            if(j >= 0) {
                if (i>j) {
                    tauRads.remove(i);
                    tauRads.remove(j);
                } else {
                    tauRads.remove(j);
                    tauRads.remove(i);
                }
                sortRads();
                break;
            }
        }
    }

    private int sortRads(float[] rads) throws GameActionException {
        for (int i = tauRads.size(); --i >= 0;) {
            if ((rads[0] != tauRads.get(i)[0] && rads[1] != tauRads.get(i)[1])
                && (inRads(rads[0], tauRads.get(i)) || inRads(rads[1], tauRads.get(i)))) {
                tauRads.add(mergeRads(rads, tauRads.get(i)));
                return i;
            }
        }
        return -1;
    }

    private float[] mergeRads(float[] rad1, float[] rad2) throws GameActionException {
        float lRad = inRads(rad1[0], rad2) ? rad2[0] : rad1[0];
        float rRad = inRads(rad1[1], rad2) ? rad2[1] : rad1[1];
        return new float[] { lRad, rRad };
    }

    private boolean inRads(float rad, float[] rads) throws GameActionException {
        return rads[0] > rads[1]
                ? (rad <= rads[0] && rad >= rads[1])
                : ((rad <= rads[0] && rad >= 0)
                    || (rad >= rads[1] && rad <= Constants.TAU));
    }

    private void debug_drawRads() throws GameActionException {
        for (int i = tauRads.size(); --i >= 0;) {
            rc.setIndicatorLine(rc.getLocation(), rc.getLocation().add(new Direction(tauRads.get(i)[0]), 10), 255, 255, 255);
            rc.setIndicatorLine(rc.getLocation(), rc.getLocation().add(new Direction(tauRads.get(i)[1]), 10), 0, 255, 0);
        }
        Clock.yield();
    }
    /*
    private void testStuff() throws GameActionException {
        TreeInfo[] trees = rc.senseNearbyTrees();
        archon.bodies.clear();
        for (int i = trees.length; --i >= 0;) archon.bodies.add(new Body(trees[i].location, trees[i].radius));
        archon.moveDir = Direction.getEast();
        Direction tmpDir = archon.moveDir;
        for (int i = archon.bodies.size(); --i >= 0;) {
            tmpDir = Geometry.getTangentDir(archon.bodies.get(i), tmpDir, rc.getLocation(), rc.getType().bodyRadius);
        }
    }//*/
    /*private boolean shouldHireGardener() throws GameActionException {
        //TODO
        if (rc.getRoundNum() < 10 && rc.getTeamBullets() >= GameConstants.BULLETS_INITIAL_AMOUNT) return true;
        //return archon.gardenersHired > 1;
        return false;
    }//*/
    private boolean determineLocations() throws GameActionException {
        MapLocation[] myLocations = rc.getInitialArchonLocations(rc.getTeam());
        MapLocation[] enemyLocations = rc.getInitialArchonLocations(rc.getTeam().opponent());
        MapLocation myAvgStartLocation;
        if (myLocations.length > 1) {
            myAvgStartLocation = Util.averageLocation(myLocations);
            archon.enemyAvgStartLocation = Util.averageLocation(enemyLocations);
        } else {
            myAvgStartLocation = myLocations[0];
            archon.enemyAvgStartLocation = enemyLocations[0];
        }
        archon.predictedCenterOfMap = new MapLocation((myAvgStartLocation.x + archon.enemyAvgStartLocation.x) / 2,
                (myAvgStartLocation.y + archon.enemyAvgStartLocation.y) / 2);

        archon.mapSymmetry = Util.getSymmetry(myLocations, enemyLocations);
        archon.initialArchonCount = myLocations.length;
        return archon.mapSymmetry != Constants.MapSymmetry.NONE_DETERMINED;
    }
    private void broadcastLocations() throws GameActionException {
        System.out.println("DEBUG: broadcasting locations");
        int broadcast = rc.readBroadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY);
        broadcast = ((broadcast / 10) * 10) + Util.mapSymmetryToInt(archon.mapSymmetry);
        rc.broadcast(Constants.CHANNEL_MAPSIZE_SYMMETRY, broadcast);
        archon.radio.broadcastPredictedCenterOfMap(archon.predictedCenterOfMap);
        archon.radio.broadcastEnemyAvgStartLocation(archon.enemyAvgStartLocation);
    }

}
