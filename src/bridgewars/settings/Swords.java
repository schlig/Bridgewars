package bridgewars.settings;

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
	
	public static void setState(Swords state) {
		Swords.currentState = state;
	}
	
	public static boolean isState(Swords state) {
		return Swords.currentState == state;
	}
	
	public static Swords getState() {
		return currentState;
	}
}
