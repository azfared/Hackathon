package hajj.com.crowdmanagement;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent myIntent = new Intent(this, SupervisorDashBoard.class);
        startActivity(myIntent);

        //requestJsonAr();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_data, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        switch (item.getItemId()) {

            case R.id.add_supervisor:
                myIntent = new Intent(this, Supervisor.class);
                startActivity(myIntent);

                return true;

            case R.id.add_camp:
                myIntent = new Intent(this, Camp.class);
                startActivity(myIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
