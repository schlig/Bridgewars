package bridgewars.settings.enums;

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
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(DoubleHealth state) {
		DoubleHealth.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static DoubleHealth getState() {
		return currentState;
	}
}
