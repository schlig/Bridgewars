package bridgewars.settings.enums;

public enum RevealWinner {
	ENABLED(true), DISABLED(false);
	
	private static RevealWinner currentState;
	private boolean isEnabled;
	
	RevealWinner(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(RevealWinner state) {
		RevealWinner.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static RevealWinner getState() {
		return currentState;
	}
}
