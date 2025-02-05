package bridgewars.game;

public enum GameState {
	STARTING, ACTIVE, ENDING, INACTIVE, EDIT;
	
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
