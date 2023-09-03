package bridgewars.settings;

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
	
	public static void setState(Piggyback state) {
		Piggyback.currentState = state;
	}
	
	public static boolean isState(Piggyback state) {
		return Piggyback.currentState == state;
	}
	
	public static Piggyback getState() {
		return currentState;
	}
}
