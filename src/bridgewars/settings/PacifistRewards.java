package bridgewars.settings;

public enum PacifistRewards {
	ENABLED(true), DISABLED(false);
	
	private static PacifistRewards currentState;
	private boolean isEnabled;
	
	PacifistRewards(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void setState(PacifistRewards state) {
		PacifistRewards.currentState = state;
	}
	
	public static boolean isState(PacifistRewards state) {
		return PacifistRewards.currentState == state;
	}
	
	public static PacifistRewards getState() {
		return currentState;
	}
}
