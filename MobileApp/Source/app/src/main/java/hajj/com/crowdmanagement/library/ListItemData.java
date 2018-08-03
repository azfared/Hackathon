package hajj.com.crowdmanagement.library;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListItemData {
    //declarations
    public JSONArray jsArray;
    public JSONObject jsObj;
    public String itemType;
    public int viewType;
    public ListItemData(JSONArray jsArray)
    {
        //get the data of the json arra
        this.jsArray = jsArray;
    }
    public ListItemData(JSONObject jsObj)
    {
        //get the data of the json arra
        this.jsObj = jsObj;
    }



}
