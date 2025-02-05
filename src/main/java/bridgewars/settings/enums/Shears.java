package bridgewars.settings.enums;

public enum Shears {
	ENABLED(true), DISABLED(false);
	
	private static Shears currentState;
	private boolean isEnabled;
	
	Shears(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(Shears state) {
		Shears.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static Shears getState() {
		return currentState;
	}
}
