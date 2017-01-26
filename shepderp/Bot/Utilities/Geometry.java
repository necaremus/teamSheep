package shepderp.Bot.Utilities;

import battlecode.common.*;
import shepderp.Bot.Utilities.HelperClasses.Body;

/**
 * Created by necaremus on 17.01.17.
 *
 */
public class Geometry {

    /*
    * returns if given body is in specified rectangle
    *
    * @ param:
    * 		BodyInfo body: the body to be checked against the rectangle
    * 		Direction dir: the direction the rectangle is aligned with
    * 		MapLocation M: middle point of outer-most edge of rectangle
    * 		float h: half the rectangles height
    */
    public static boolean isInRectangle(Body body, Direction dir, MapLocation myMl, float myR) {
        float alpha = Math.abs(myMl.directionTo(body.ml).radiansBetween(dir));
        if(alpha >= Math.PI/2) return false;
        float d = myMl.distanceTo(body.ml);
        float x = (float)(d * Math.sin(alpha));
        return x - myR <= body.r;
    }

    /**
     * determines the closest tangent dir the rc can move around the body
     * given dir, if the body is not in the way
     *
     * @param body body to check
     * @param dir direction to check against
     * @param myMl rc locations
     * @param myR rc body radius
     * @return tangent dir or given dir
     */
    public static Direction getTangentDir(Body body, Direction dir, MapLocation myMl, float myR) {
        float alpha = myMl.directionTo(body.ml).radiansBetween(dir);
        if(Math.abs(alpha) >= Math.PI/2) return dir;
        float d = myMl.distanceTo(body.ml);
        float x = (float)(d * Math.sin(Math.abs(alpha)));
        if (x - myR >= body.r) return dir;
        float beta = (float)Math.atan((body.r + myR) / d);
        return alpha > 0 ? dir.rotateLeftRads(beta) : dir.rotateRightRads(beta);
    }

    //public static Direction[] getTangentRads(Body body1, Body body2) {

    //}

    /**
     * determines the tangent rads
     *
     * @param myBody the body of the bot
     * @param checkBody the body to check against
     * @return both rads that are tangent
     */
    public static float[] getTangentRads(Body myBody, Body checkBody) throws GameActionException {
        float rad = myBody.ml.directionTo(checkBody.ml).radians;
        float alpha = (float)Math.asin(/* h */ (myBody.r + checkBody.r) /
                                       /* d */ (myBody.ml.distanceTo(checkBody.ml)));
        return new float[] { Util.piRadToTauRad(rad + alpha), Util.piRadToTauRad(rad - alpha) };
    }
}
