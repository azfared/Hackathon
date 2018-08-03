package hajj.com.crowdmanagement;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Supervisor extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_supervisor);

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
        Spinner dropdown = findViewById(R.id.campOrderSp);
        String[] items = new String[]{"Itmam", "Alhwejy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

}
