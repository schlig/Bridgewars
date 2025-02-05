package bridgewars.settings.enums;

public enum WoolDecay {
	ENABLED(true), DISABLED(false);
	
	private static WoolDecay currentState;
	private boolean isEnabled;
	
	WoolDecay(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(WoolDecay state) {
		WoolDecay.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static WoolDecay getState() {
		return currentState;
	}
}
