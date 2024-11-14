package frontend.controller;

import frontend.view.panels.HomePanel;
import frontend.model.Weather;
import frontend.utils.APIUtils;

public class WeatherController {
    private HomePanel homePanel;

    public WeatherController(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

    public void displayWeather() {
        Weather weather = APIUtils.getCurrentWeather();
        if (weather != null) {
            homePanel.updateWeatherLabel(weather.getCondition(), weather.getTemperature());
        }
        else {
            homePanel.updateWeatherLabel("Unable to load weather at this moment", "0");
        }
    }
    
}
