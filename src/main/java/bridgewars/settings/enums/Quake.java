package bridgewars.settings.enums;

public enum Quake {
	ENABLED(true), DISABLED(false);
	
	private static Quake currentState;
	private boolean isEnabled;
	
	Quake(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(Quake state) {
		Quake.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static Quake getState() {
		return currentState;
	}
}
