package studyscheduler.service;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CalendarIntegration {

    public static void openEvent(String title, String description, String start, String end) {
        try {

            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String encodedDescription = URLEncoder.encode(
                    description != null ? description : "",
                    StandardCharsets.UTF_8
            );

            String url = "https://www.google.com/calendar/render?action=TEMPLATE"
                    + "&text=" + encodedTitle
                    + "&details=" + encodedDescription
                    + "&dates=" + start + "/" + end;

            Desktop.getDesktop().browse(new URI(url));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}