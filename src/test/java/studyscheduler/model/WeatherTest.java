package studyscheduler.model;

import org.junit.jupiter.api.Test;
import studyscheduler.model.Weather;
import studyscheduler.service.WeatherService;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherTest {

    @Test
    void deveBuscarClimaComSucesso() {

        WeatherService service = new WeatherService();

        Weather clima = service.buscarClima();

        assertNotNull(clima);

        assertNotNull(clima.getDescricao());

        assertFalse(clima.getDescricao().isBlank());

        assertTrue(clima.getTemperatura() > -100);
    }
}