package bridgewars.settings.enums;

public enum KillstreakRewards {
	ENABLED(true), DISABLED(false);
	
	private static KillstreakRewards currentState;
	private boolean isEnabled;
	
	KillstreakRewards(boolean isEnabled){
		this.isEnabled = isEnabled;
	}

	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(KillstreakRewards state) {
		KillstreakRewards.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static KillstreakRewards getState() {
		return currentState;
	}
}
