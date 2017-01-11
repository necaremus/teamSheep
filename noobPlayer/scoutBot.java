package noobPlayer;
import battlecode.common.*;

public strictfp class scoutBot {
	static RobotController rc;
	
	public static void run(RobotController rc) throws GameActionException {
		
		scoutBot.rc = rc;
		System.out.println("I BE SCOUT!");
		
		while (true) {
			try {
				
				Clock.yield();
				
			} catch (Exception e) {
                System.out.println("Scout Exception");
                e.printStackTrace();
            }
		}
	}
}