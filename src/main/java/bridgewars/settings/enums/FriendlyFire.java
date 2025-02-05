package bridgewars.settings.enums;

public enum FriendlyFire {
	ENABLED(true), DISABLED(false);
	
	private static FriendlyFire currentState;
	private boolean isEnabled;
	
	FriendlyFire(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(FriendlyFire state) {
		FriendlyFire.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static FriendlyFire getState() {
		return currentState;
	}
}
