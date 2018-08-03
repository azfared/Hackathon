using System;
using System.Text;
using System.Data;
using System.Text.RegularExpressions;
using System.Web;
using System.Data.Common;


public class clSys: cuDBF
{
    public string UserID, UserN, UserDept;
    public double TopButMarg = 0.04, RightM = 0.02, appH = 0.2, scopeH = 0.25, dataW = 0.73, indexH = 0.50;
    public string projName, cc1, cc2;
    public string
        dbe, AppLang,
        parentFrameColor = "", //"#dddddd",
        appFrameColor = "#FDE6C5",//"#f9e486",	
        scopeFrameColor = "#FDE6C5",//"#f2d766",
        indexFrameColor = "#FDE6C5", //"#fbfebe", 
        indexDetFrameColor = "#DCDCDC",
        parentFrameBG = "", //"images/rectangle.gif",
        libraryParentFrameBG = "";

    public clSys()
    {
    }
    public clSys(string mobile)
    { 
    
    }

 
}