package bridgewars.settings.enums;

public enum Swords {
	ENABLED(true), DISABLED(false);
	
	private static Swords currentState;
	private boolean isEnabled;
	
	Swords(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(Swords state) {
		Swords.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static Swords getState() {
		return currentState;
	}
}
