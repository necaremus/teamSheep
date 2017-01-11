package noobPlayer;
import battlecode.common.*;

public strictfp class gardenerBot {
	static RobotController rc;
	
	public static void run(RobotController rc) throws GameActionException {
		
		gardenerBot.rc = rc;
		System.out.println("I BE GARDENER");
		
		while (true) {
			try {
				
				Clock.yield();
				
			} catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
		}
	}
}