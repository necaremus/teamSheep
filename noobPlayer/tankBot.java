package noobPlayer;
import battlecode.common.*;

public strictfp class tankBot {
	static RobotController rc;
	
	public static void run(RobotController rc) throws GameActionException {
		
		tankBot.rc = rc;
		System.out.println("I BE TANK!");
		
		while (true) {
			try {
				
				Clock.yield();
				
			} catch (Exception e) {
                System.out.println("Tank Exception");
                e.printStackTrace();
            }
		}
	}
}