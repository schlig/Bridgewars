package bridgewars.settings;

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
	
	public static void setState(GigaDrill state) {
		GigaDrill.currentState = state;
	}
	
	public static boolean isState(GigaDrill state) {
		return GigaDrill.currentState == state;
	}
	
	public static GigaDrill getState() {
		return currentState;
	}
}
