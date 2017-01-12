package testplayer;

public class Globals {




	// channels
	public static final int ARCHON_CHAN_FARM = 0;			// archon-only channel to assign farming job at the beginning

	public static final int ALL_CHAN = 9;					// channel for all units

	public static final int GARDENER_CHAN = 10;				// channel gardeners listen to
	public static final int SCOUT_CHAN = 11;				// channel scouts listen to
	public static final int LUMBER_CHAN = 12;				// channel lumberjacks listen to
	public static final int SOLDIER_CHAN = 13;				// channel soldiers listen to
	public static final int TANK_CHAN = 14;					// channel tanks listen to

	public static final int GARDENER_ASSIGNMENT_CHAN = 15;	// channel for assigning behaviour to gardener
	public static final int SCOUT_ASSIGNMENT_CHAN = 16;		// channel for assigning behaviour to scout
	public static final int LUMBER_ASSIGNMENT_CHAN = 17;	// channel for assigning behaviour to lumberjack
	public static final int SOLDIER_ASSIGNMENT_CHAN = 18;	// channel for assigning behaviour to soldiers
	public static final int TANK_ASSIGNMENT_CHAN = 19;		// channel for assigning behaviour to tanks



	// orders
	public static final int FARMING = 1;			// sends receiving unit to farming behaviour


	// other
	public static final int EMPTY = 0;			// no message on channel

}
