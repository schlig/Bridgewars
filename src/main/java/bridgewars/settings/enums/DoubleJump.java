package bridgewars.settings.enums;

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
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(DoubleJump state) {
		DoubleJump.currentState = state;
	}
	
	public static DoubleJump getState() {
		return currentState;
	}
}
