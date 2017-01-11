package noobPlayer;
import battlecode.common.*;

public strictfp class soldierBot {
	static RobotController rc;
	
	public static void run(RobotController rc) throws GameActionException {
		
		soldierBot.rc = rc;
		System.out.println("I BE SOLDIER!");
		
		while (true) {
			try {
				
				Clock.yield();
				
			} catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
		}
	}
}