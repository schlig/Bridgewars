package bridgewars.settings.enums;

public enum IndestructibleMap {
	ENABLED(true), DISABLED(false);
	
	private static IndestructibleMap currentState;
	private boolean isEnabled;
	
	IndestructibleMap(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(IndestructibleMap state) {
		IndestructibleMap.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static IndestructibleMap getState() {
		return currentState;
	}
}
