package hajj.com.crowdmanagement;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class SupervisorDashBoard extends Activity
{
    Button beginCamp;
    String moveOrder = "1";
    String campID = "1.";
    String campName = "Camp No.1";
    TextView campInstruction;
    ImageView alarmIg;
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

        try {
            defineLayout();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //validate the data
    }

    private void defineLayout() throws JSONException
    {
        campInstruction = (TextView) findViewById(R.id.campInstruction);
        beginCamp = (Button) findViewById(R.id.beginCamp);
        alarmIg = (ImageView) findViewById(R.id.alarmIg);
        beginCamp.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        confirmDel();
                    }
                }
        );
        //linkToService(this);
        //finish();
        Bundle extras;
        extras = getIntent().getExtras();
        if(extras != null)
        {
            String stDataStr = extras.getString("data");
            if(stDataStr != null )
            {
                //set the data
                JSONObject stData = new JSONObject(stDataStr);
                String move = stData.getString("move");
                if(move.equals("-1"))
                {

                    campInstruction.setText("Congrats: We have finihsed !!");
                    beginCamp.setVisibility(View.GONE);
                    alarmIg.setVisibility(View.GONE);
                }
                else
                {
                    campID = stData.getString("campID");
                    campName = stData.getString("campName");
                    moveOrder = stData.getString("moveOrder");
                    campInstruction.setText(campName+" Please move" );
                }
            }

        }


    }

    void confirmDel()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Are you sure?!");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure, All pilgrims finished");

        //alertDialog.setIcon(R.drawable.ic_action_help);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which)
            {
                //linkToService(SupervisorDashBoard.this);
                Intent myIntent = new Intent(SupervisorDashBoard.this, GetDataFromServer.class);
                myIntent.putExtra("activeCampId", campID);
                GetDataFromServer.enqueueWork(SupervisorDashBoard.this, myIntent);
                finish();


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke NO event

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
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
