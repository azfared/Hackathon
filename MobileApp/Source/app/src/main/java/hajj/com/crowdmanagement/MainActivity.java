package hajj.com.crowdmanagement;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hajj.com.crowdmanagement.library.CampsAdapter;
import hajj.com.crowdmanagement.library.ListItemData;

public class MainActivity extends Activity
{
    ArrayList<ListItemData> listItems;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent myIntent = new Intent(this, SupervisorDashBoard.class);
       // startActivity(myIntent);
        lv = (ListView) findViewById(R.id.pshlistview);
        try {
            draw();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_data, menu);
        return true;
    }

    private void draw() throws JSONException {
        int objLen = 10;

        JSONArray campsData = new JSONArray() ;
        for(int i = 0; i < objLen; i++){
            JSONObject campData = new JSONObject();
            campData.put("name", "Camp No."+(i+1)+"");
            campsData.put(campData);
        }

        //get the testsData
        try
        {
            listItems = new ArrayList<ListItemData>(objLen);
            for (int i = 0; i < objLen; i++)
            {
                JSONObject oneCamp = campsData.getJSONObject(i);
                listItems.add(new ListItemData(oneCamp));
            }
            System.out.println(listItems);
            lv.setAdapter(new CampsAdapter(listItems, this));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
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

            case R.id.action_moving:
                myIntent = new Intent(this, SupervisorDashBoard.class);
                startActivity(myIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
