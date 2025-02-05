package bridgewars.settings.enums;

public enum Piggyback {
	ENABLED(true), DISABLED(false);
	
	private static Piggyback currentState;
	private boolean isEnabled;
	
	Piggyback(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(Piggyback state) {
		Piggyback.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static Piggyback getState() {
		return currentState;
	}
}
