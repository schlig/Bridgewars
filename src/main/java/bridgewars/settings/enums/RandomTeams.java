package bridgewars.settings.enums;

public enum RandomTeams {
	ENABLED(true), DISABLED(false);
	
	private static RandomTeams currentState;
	private boolean isEnabled;
	
	RandomTeams(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(RandomTeams state) {
		RandomTeams.currentState = state;
	}
	
	public static RandomTeams getState() {
		return currentState;
	}
}
