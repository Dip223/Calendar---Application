import java.util.Timer;
import java.util.TimerTask;

public class Reminder {

    public static void scheduleReminder(Event event) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Reminder: " + event.getReminderMessage());
            }
        }, event.getDate());
    }
}
