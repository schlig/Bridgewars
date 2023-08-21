package bridgewars.settings;

public enum KillstreakRewards {
	ENABLED(true), DISABLED(false);
	
	private static KillstreakRewards currentState;
	private boolean isEnabled;
	
	KillstreakRewards(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void setState(KillstreakRewards state) {
		KillstreakRewards.currentState = state;
	}
	
	public static boolean isState(KillstreakRewards state) {
		return KillstreakRewards.currentState == state;
	}
	
	public static KillstreakRewards getState() {
		return currentState;
	}
}
