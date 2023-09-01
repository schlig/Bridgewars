package bridgewars.settings;

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
	
	public static void setState(NaturalItemSpawning state) {
		NaturalItemSpawning.currentState = state;
	}
	
	public static boolean isState(NaturalItemSpawning state) {
		return NaturalItemSpawning.currentState == state;
	}
	
	public static NaturalItemSpawning getState() {
		return currentState;
	}
}
