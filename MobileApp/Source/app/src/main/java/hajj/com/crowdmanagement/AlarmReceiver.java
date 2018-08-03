package hajj.com.crowdmanagement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) {
		//System.out.println("Hello");
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
		{
				//SupervisorDashBoard.linkToService(context);

				Intent myIntent = new Intent(context, GetDataFromServer.class);
				//myIntent.putExtra("doIndex", "updateOldPath");
			    GetDataFromServer.enqueueWork(context, myIntent);

		}
		else
        {
			Intent myIntent = new Intent(context, GetDataFromServer.class);
			GetDataFromServer.enqueueWork(context, myIntent);
		}
	}

}
