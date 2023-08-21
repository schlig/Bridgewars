package bridgewars.settings;

public enum DoubleJump {
	ENABLED(true), DISABLED(false);
	
	private static DoubleJump currentState;
	private boolean isEnabled;
	
	DoubleJump(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void setState(DoubleJump state) {
		DoubleJump.currentState = state;
	}
	
	public static boolean isState(DoubleJump state) {
		return DoubleJump.currentState == state;
	}
	
	public static DoubleJump getState() {
		return currentState;
	}
}
