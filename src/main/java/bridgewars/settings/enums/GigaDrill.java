package bridgewars.settings.enums;

public enum GigaDrill {
	ENABLED(true), DISABLED(false);
	
	private static GigaDrill currentState;
	private boolean isEnabled;
	
	GigaDrill(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(GigaDrill state) {
		GigaDrill.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static GigaDrill getState() {
		return currentState;
	}
}
