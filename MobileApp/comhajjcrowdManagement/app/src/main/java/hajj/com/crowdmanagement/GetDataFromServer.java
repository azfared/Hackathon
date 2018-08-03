package hajj.com.crowdmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class GetDataFromServer extends JobIntentService
{

    public static final int JOB_ID = 109;
    private JSONObject data;
    private String doIndex;
    private String callService;
    final static String ServerUrl =
            "http://192.168.42.130/HajjCrowdMang/WebService.asmx/";

    public static void enqueueWork(Context context, Intent work)
    {

        enqueueWork(context, GetDataFromServer.class, JOB_ID, work);
    }


    @Override
    protected void onHandleWork(Intent intent)
    {
        //get the data from server
        System.out.println("Service is ready");
        //get the data
        requestJson(getApplicationContext(), "getMovementOrder");

    }

    public  void requestJson(Context context, String fnName)
    {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ServerUrl + fnName;
        //final ProgressDialog pDialog = new ProgressDialog(context);
        //pDialog.setMessage("Loading...");
       // pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        //Log.d(TAG, response.toString());
                        System.out.println("Res: " +response);
                        //call the SuperVisor Activity
                        Intent dialogIntent = new Intent(getApplicationContext(), SupervisorDashBoard.class);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(dialogIntent);
                    }
                }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                System.out.println("Res: " +error.getMessage());
                //pDialog.hide();
            }
        });

        queue.add(jsonObjReq);
    }









    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //before finishing do things here
    }
}