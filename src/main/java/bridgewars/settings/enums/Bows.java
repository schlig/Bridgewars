package bridgewars.settings.enums;

public enum Bows {
	ENABLED(true), DISABLED(false);
	
	private static Bows currentState;
	private boolean isEnabled;
	
	Bows(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(Bows state) {
		Bows.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static Bows getState() {
		return currentState;
	}
}
