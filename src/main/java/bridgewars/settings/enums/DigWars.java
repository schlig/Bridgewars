package bridgewars.settings.enums;

public enum DigWars {
	ENABLED(true), DISABLED(false);
	
	private static DigWars currentState;
	private boolean isEnabled;
	
	DigWars(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(DigWars state) {
		DigWars.currentState = state;
	}
	
	public static DigWars getState() {
		return currentState;
	}
}
