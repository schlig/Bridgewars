package bridgewars.settings;

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
	
	public static void setState(Shears state) {
		Shears.currentState = state;
	}
	
	public static boolean isState(Shears state) {
		return Shears.currentState == state;
	}
	
	public static Shears getState() {
		return currentState;
	}
}
