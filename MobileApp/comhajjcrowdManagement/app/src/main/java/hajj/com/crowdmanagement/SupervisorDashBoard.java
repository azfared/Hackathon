package hajj.com.crowdmanagement;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class SupervisorDashBoard extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_dashboard);

        // get the action bar
        ActionBar actionBar = getActionBar();
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        //define the data
        setTitle(R.string.campSupervisor);
        defineLayout();
        //validate the data
    }

    private void defineLayout()
    {

        linkToService(this);
        //finish();
    }

    public static void linkToService(Context context)
    {
        PendingIntent pendingIntent;
        AlarmManager manager;
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 101,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar c = Calendar.getInstance();


        int interval = 1000*30;
        //demo to checkout
        //interval = 1000*30;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),
                interval, pendingIntent);


    }


}
