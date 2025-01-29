package bridgewars.settings.enums;

public enum HidePlayers {
	ENABLED(true), DISABLED(false);
	
	private static HidePlayers currentState;
	private boolean isEnabled;
	
	HidePlayers(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(HidePlayers state) {
		HidePlayers.currentState = state;
	}
	
	public static HidePlayers getState() {
		return currentState;
	}
}
