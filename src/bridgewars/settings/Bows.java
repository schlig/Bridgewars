package bridgewars.settings;

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
	
	public static void setState(Bows state) {
		Bows.currentState = state;
	}
	
	public static boolean isState(Bows state) {
		return Bows.currentState == state;
	}
	
	public static Bows getState() {
		return currentState;
	}
}
