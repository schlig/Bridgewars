package bridgewars.behavior;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import bridgewars.Main;

public class DisableWeather implements Listener {

	
	public DisableWeather(Main plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
}
