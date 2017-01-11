package noobPlayer;
import battlecode.common.*;

public strictfp class lumberjackBot {
	static RobotController rc;
	
	public static void run(RobotController rc) throws GameActionException {
		
		lumberjackBot.rc = rc;
		System.out.println("I BE LUMBERJACK!");
		
		while (true) {
			try {
				
				Clock.yield();
				
			} catch (Exception e) {
                System.out.println("Lumberjack Exception");
                e.printStackTrace();
            }
		}
	}
}