package bridgewars.settings.enums;

public enum UnlockedInventory {
	ENABLED(true), DISABLED(false);
	
	private static UnlockedInventory currentState;
	private boolean isEnabled;
	
	UnlockedInventory(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(UnlockedInventory state) {
		UnlockedInventory.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static UnlockedInventory getState() {
		return currentState;
	}
}
