package bridgewars.settings.enums;

public enum Blocks {
	ENABLED(true), DISABLED(false);
	
	private static Blocks currentState;
	private boolean isEnabled;
	
	Blocks(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(Blocks state) {
		Blocks.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static Blocks getState() {
		return currentState;
	}
}
