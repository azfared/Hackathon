using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;

/// <summary>
/// Summary description for WebService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
[System.Web.Script.Services.ScriptService]
public class WebService : System.Web.Services.WebService {

    public WebService () 
    {

        //Uncomment the following line if using designed components 
        //InitializeComponent(); 
    }

    [WebMethod]
    [ScriptMethod(UseHttpGet = true, ResponseFormat = ResponseFormat.Json)]
    public void getCampsSupervisors() 
    {
        clSys mu = new clSys();
        string Comm1 = "select top 10 item_id id, f2 name, f3 age, f4 ageUnit, f5 address from persons" ;
        System.Data.Common.DbDataReader R1 = mu.ExecuteReader(Comm1);
        string json = "";
        if (R1.HasRows)
        {
            string dataStr = "";


            while (R1.Read())
            {
                dataStr += dataStr == "" ? "{\"id\":\"" + R1["id"] + "\",\"name\":\"" + R1["name"] + "\" "
                    + "\"}" 
                    :
                      ","
                      + "{\"id\":\"" + R1["id"] + "\",\"name\":\"" + R1["name"] + "\" "
                    + "\"}";

            }
            json = "{\"success\":\"1\", \"error\":\"0\", \"title\"  :[" + dataStr + "]}";
        }
        else
        {
            json = "{\"success\":\"1\", \"error\":\"0\", \"title\"  :[{\"id\":\"noData\",\"name\":\"No data\"}]}";
        }
        R1.Close();

      

        HttpContext.Current.Response.Write(json);

    }

    [WebMethod]
    [ScriptMethod(UseHttpGet = true, ResponseFormat = ResponseFormat.Json)]
    public void getMovementOrder()
    {
        clSys mu = new clSys();
        string Comm1 = "select f2 movementTime from moveTime" ;
        System.Data.Common.DbDataReader R1 = mu.ExecuteReader(Comm1);
        
        string moveTime = mu.ExecuteScalar(Comm1) ;
        string[] moveDateAr = moveTime.Split(' ');
        string[] dateValue = moveDateAr[0].Split('/');
        int hours = int.Parse(moveDateAr[1].Split(':')[0]);
        int mins = int.Parse(moveDateAr[1].Split(':')[1]);
        string dataStr = "";

        DateTime moveTimeDate = new DateTime(int.Parse(dateValue[2]), int.Parse(dateValue[1]), int.Parse(dateValue[0]),
               hours, mins, 0);
        if (DateTime.Now > moveTimeDate)
        {


            dataStr = getCurrentOrder();
        }
        else 
        {
            dataStr = "{\"move\":\"0\"}";
            
        }
        R1.Close();
        HttpContext.Current.Response.Write(dataStr);   
    }

    private string getCurrentOrder() 
    { 
        clSys mu = new clSys();
        string Comm1 = "select top 1 item_id campID, f2 campName,  f3 personID, f4 moveOrder from camps where f5 is null"
            +" order by convert(int, f4) \n";
        System.Data.Common.DbDataReader R1 = mu.ExecuteReader(Comm1);
        string json = "";

        string dataStr = "";
        if (R1.HasRows)
        {
            while (R1.Read())
            {
                //dataStr += R1["campName"] + ", " + R1["personID"] + ", " + R1["moveOrder"] + " \n";
                dataStr = "{\"campID\":\"" + R1["campID"] + "\",\"campName\":\"" + R1["campName"] + "\", \"moveOrder\":\"" + R1["moveOrder"] + "\" } ";
                
            }
        }
        else 
        {
            //return -1 means the cycle completed
            dataStr = "{\"move\":\"-1\"}";
        }
        R1.Close();
        return dataStr;

    }
    
}
