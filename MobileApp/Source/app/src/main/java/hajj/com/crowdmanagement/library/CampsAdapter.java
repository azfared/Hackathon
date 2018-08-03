package hajj.com.crowdmanagement.library;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import hajj.com.crowdmanagement.R;

public class CampsAdapter extends BaseAdapter{
    private ArrayList<ListItemData> _data;
    Context _c;
    public CampsAdapter (ArrayList<ListItemData> data, Context c){
        _data = data;
        this._c = c;
    }
    public int getCount() {
        return _data.size();
    }

    public Object getItem(int position) {
        return _data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)_c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.search_layout, null);
        }

        //get views
        ImageView image = (ImageView) v.findViewById(R.id.shCampPic);
        ImageView pDetails = (ImageView) v.findViewById(R.id.pDetails);

        image.setImageResource(R.drawable.camp);

        TextView campName = (TextView) v.findViewById(R.id.shCampName);
        TextView campID = (TextView) v.findViewById(R.id.shCampID);


        //get the data..
        final ListItemData campData = _data.get(position);
        final int curPosition = position;

        try {
            final JSONObject jsonObject = campData.jsObj;

            //always will be one row
            campName.setText(jsonObject.getString("name"));

            //get imgs paths
            String imgUrl = jsonObject.getString("imgUrl");
            //load the image
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return v;

    }

}
