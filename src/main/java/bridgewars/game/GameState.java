package bridgewars.game;

public enum GameState {
	INACTIVE, ACTIVE, EDIT;
	
	private static GameState currentState;
	
	GameState(){ }
	
	public static void setState(GameState state) {
		GameState.currentState = state;
	}
	
	public static boolean isState(GameState state) {
		return GameState.currentState == state;
	}
	
	public static GameState getState() {
		return currentState;
	}
}
