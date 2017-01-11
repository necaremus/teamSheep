package noobPlayer;
import battlecode.common.*;

public strictfp class archonBot {
	static RobotController rc;
	
	public static void run(RobotController rc) throws GameActionException {
		
		archonBot.rc = rc;
		System.out.println("I'm an archon!");
		
		while (true) {
			try {
				
				Clock.yield();
				
			} catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
		}
	}
}