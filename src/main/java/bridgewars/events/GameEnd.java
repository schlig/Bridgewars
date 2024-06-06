package bridgewars.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEnd extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final int gameID;

    public static HandlerList getHandletList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public GameEnd(int gameID){
        this.gameID = gameID;
    }

    public int getGameID(){
        return gameID;
    }
}