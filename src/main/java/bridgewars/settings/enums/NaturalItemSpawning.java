package bridgewars.settings.enums;

public enum NaturalItemSpawning {
	ENABLED(true), DISABLED(false);
	
	private static NaturalItemSpawning currentState;
	private boolean isEnabled;
	
	NaturalItemSpawning(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	public Boolean isEnabled() {
		return isEnabled;
	}
	
	public static void toggle() {
		setState(getState().isEnabled() ? DISABLED : ENABLED);
	}
	
	public static void setState(NaturalItemSpawning state) {
		NaturalItemSpawning.currentState = state;
	}
	
	public static void setState(boolean state) {
		setState(state ? ENABLED : DISABLED);
	}
	
	public static NaturalItemSpawning getState() {
		return currentState;
	}
}
