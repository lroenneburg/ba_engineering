package ba_roenneburg;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class ScheduledTasks {
    private DataDownloader dataDownloader = new DataDownloader();

    @Scheduled(cron = "0 */10 * ? * *")
    //@Scheduled(cron = "0 0 */12 ? * *")
    public void dailyRIIRSSRequest() {
        ArrayList<String> new_ids = new ArrayList<>();

        try {
            new_ids = dataDownloader.downloadTheNewestDecisions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        System.out.println("Daily RII-RSS-Feed Request executed on " + formatter.format(date));
        System.out.println( new_ids.size() + " new Decisions downloaded.");
    }
}
