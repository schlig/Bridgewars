package bridgewars.settings;

public enum DoubleHealth {
	ENABLED(true), DISABLED(false);
	
	private static DoubleHealth currentState;
	private boolean isEnabled;
	
	DoubleHealth(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void setState(DoubleHealth state) {
		DoubleHealth.currentState = state;
	}
	
	public static boolean isState(DoubleHealth state) {
		return DoubleHealth.currentState == state;
	}
	
	public static DoubleHealth getState() {
		return currentState;
	}
}
