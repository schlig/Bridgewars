package bridgewars.settings;

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
	
	public static void setState(FriendlyFire state) {
		FriendlyFire.currentState = state;
	}
	
	public static boolean isState(FriendlyFire state) {
		return FriendlyFire.currentState == state;
	}
	
	public static FriendlyFire getState() {
		return currentState;
	}
}
