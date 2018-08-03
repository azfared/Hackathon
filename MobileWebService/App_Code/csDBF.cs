using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.IO;
using System.Data.Common;
using System.Data.SqlClient;
using Microsoft.Win32;


public class cuDBF
{
    public string DataSource, conStr;
    public cuDBF()
    {
       string  server = ".";
       string sqlInstName = "SQLEXPRESS";
       string db = "hajjCrowd";
       string uid = "hoda";
       string pwd = "AamiraR";

        conStr = 
        "initial catalog=" + db + ";persist security info=False;data source=" + server
        + "\\" + sqlInstName + ";User ID=" + uid + ";Password=" + pwd + ";packet size=4096;MultipleActiveResultSets=True";
    }
    private void SetDefaultConStr()
    {
        string server = ".";
        string sqlInstName = "SQLEXPRESS";
        string db = "hajjCrowd";
        string uid = "hoda";
        string pwd = "AamiraR";

        conStr =
        "initial catalog=" + db + ";persist security info=False;data source=" + server
        + "\\" + sqlInstName + ";User ID=" + uid + ";Password=" + pwd + ";packet size=4096;MultipleActiveResultSets=True";
    }

    private void Connection(ref SqlConnection conn)
    {
        try
        {
           
            conn.ConnectionString = conStr;
            conn.Open();
        }

        catch (Exception)
        {
            if (conn.State != ConnectionState.Closed) conn.Close();
            conn.ConnectionString = conStr;
            conn.Open();
        }
    }
    public SqlDataReader ExecuteReader(string sql)
    {
        //Adopt Sql statement for the 2 databases muPtients or muStudents..
        //sql = AdoptSql(sql);
        SqlConnection conn1 = new SqlConnection();

        SqlDataReader r0 = null;

        SqlCommand cmd0 = new SqlCommand(sql, conn1);
        cmd0.CommandTimeout = 60;
        cmd0.CommandType = CommandType.Text;
        Connection(ref conn1);

        r0 = cmd0.ExecuteReader(CommandBehavior.CloseConnection);
        
        try
        {
            r0 = cmd0.ExecuteReader(CommandBehavior.CloseConnection);
        }
        catch (Exception e)
        {
            try
            {
                //WriteToLog(e.Message, sql);
            }
            catch { }
            //
            //HttpContext.Current.Response.Write("<script>alert(\"Server is too busy\\n Pleas try again after a while\")</script>");
        }
            
        SetDefaultConStr();
        return r0;
    }

    public SqlDataReader ExecuteReader(string sql, string conStr)
    {
        //Adopt Sql statement for the 2 databases muPtients or muStudents..
        //cahnge the conStr
        this.conStr = conStr;
        //sql = AdoptSql(sql);
        SqlConnection conn1 = new SqlConnection();

        SqlDataReader r1 = null;

        SqlCommand cmd1 = new SqlCommand(sql, conn1);
        cmd1.CommandTimeout = 0;
        cmd1.CommandType = CommandType.Text;
        Connection(ref conn1);
        r1 = cmd1.ExecuteReader(CommandBehavior.CloseConnection);
        cmd1.Dispose();
        SetDefaultConStr();
        return r1;
    }
    private void WriteToLog(string msg, string sql)
    {
        //string str = "m";
        //for dicom server to see what is the error..
        //FileStream fs = new FileStream(
        string fileName = "sqlError.txt";

        //FileStream fs = new FileStream(@"\\172.16.23.123\dppPatient1\temp\" + fileName, FileMode.OpenOrCreate, FileAccess.Write);
        FileStream fs = new FileStream(@"c:\temp\" + fileName, FileMode.OpenOrCreate, FileAccess.Write);
        StreamWriter m_streamWriter = new StreamWriter(fs);
        m_streamWriter.BaseStream.Seek(0, SeekOrigin.End);
        m_streamWriter.Write(DateTime.Now + " " + msg + "\r\n" + sql + "\r\n");

        //m_streamWriter.Flush();
        m_streamWriter.Close();
        fs.Close();


    }
    public SqlDataReader ExecuteReader(string sql, string[] ParameterName, string[] values)
    {
        SqlConnection conn1 = new SqlConnection();

        SqlDataReader r1 = null;
        SqlCommand cmd1 = new SqlCommand(sql, conn1);
        if (ParameterName != null)
        {
            for (int i = 0; i < ParameterName.Length; i++)
            {
                //SqlParameter pr = new SqlParameter();
                //pr.ParameterName = ParameterName[i];
                //pr.Value = values[i];
                //cmd1.Parameters.Add(pr);
                cmd1.Parameters.Add(ParameterName[i], SqlDbType.VarChar, 80).Value = values[i];
            }
        }
        Connection(ref conn1);
        //conn1.Open();
        r1 = cmd1.ExecuteReader(CommandBehavior.CloseConnection);
        cmd1.Dispose();
        SetDefaultConStr();
        return r1;
    }
    public string ExecuteScalar(string sql)
    {
        
        SqlConnection conn1 = new SqlConnection();
        Connection(ref conn1);
        SqlCommand cmd1 = new SqlCommand(sql, conn1);
        try
        {
            cmd1.CommandType = CommandType.Text;
            string result = "" + cmd1.ExecuteScalar();
            cmd1.Dispose();
            conn1.Close();
            conn1.Dispose();
            SetDefaultConStr();
            return result;
        }
        catch (Exception ex)
        {
            try
            {
                HttpContext.Current.Response.Write("<script>alert(\"Server is too busy\\n Error Message:"
                  + ex.Message + "\")</script>");
            }
            catch (Exception e) { }
            SetDefaultConStr();
            cmd1.Dispose();
            conn1.Close();
            conn1.Dispose();
            try
            {
                HttpContext.Current.Response.End();
            }
            catch (Exception e) { }
            return "0";
        }
    }
    public string ExecuteScalar(string sql, string conStr)
    {
        this.conStr = conStr;
        try
        {
            SqlConnection conn1 = new SqlConnection();
            Connection(ref conn1);
            SqlCommand cmd1 = new SqlCommand(sql, conn1);
            cmd1.CommandType = CommandType.Text;
            string result = "" + cmd1.ExecuteScalar();
            cmd1.Dispose();
            conn1.Close();
            conn1.Dispose();
            SetDefaultConStr();
            return result;
        }
        catch (Exception ex)
        {
            try
            {
                HttpContext.Current.Response.Write("<script>alert(\"Server is too busy\\n Error Message:"
                  + ex.Message + "\")</script>");
                HttpContext.Current.Response.End();
            }
            catch (Exception e) { }
            SetDefaultConStr();
            return "0";
        }
    }
    public int ExecuteNonQuery(string sql)
    {
        SqlTransaction transaction = null;
        SqlConnection conn1 = null;
        int rowsAffected = 0;

        if (sql != "")
        {
            try
            {
                conn1 = new SqlConnection();
                Connection(ref conn1);
                transaction = conn1.BeginTransaction();

                SqlCommand cmd1 = new SqlCommand(sql, conn1, transaction);
                cmd1.CommandType = CommandType.Text;
                rowsAffected = cmd1.ExecuteNonQuery();

                transaction.Commit();
                transaction.Dispose();
                cmd1.Dispose();
                conn1.Close();
                conn1.Dispose();
                SetDefaultConStr();
            }
            catch (Exception ex)
            {
                SetDefaultConStr();
                transaction.Rollback();
                try
                {
                    HttpContext.Current.Response.Write(sql);
                    HttpContext.Current.Response.Write("<script>alert(\"Server is too busy\\n Error Message:Data was not saved\")</script>");
                    HttpContext.Current.Response.End();
                }
                catch (Exception e) { }
                throw ex;

            }

        }
        return rowsAffected;

    }
    public int ExecuteNonQuery(string sql, string conStr)
    {
    
        this.conStr = conStr;
        SqlTransaction transaction = null;
        SqlConnection conn1 = null;
        int rowsAffected = 0;

        if (sql != "")
        {
            try
            {
                conn1 = new SqlConnection();
                Connection(ref conn1);
                transaction = conn1.BeginTransaction();

                SqlCommand cmd1 = new SqlCommand(sql, conn1, transaction);
                cmd1.CommandType = CommandType.Text;
                rowsAffected = cmd1.ExecuteNonQuery();

                transaction.Commit();
                transaction.Dispose();
                cmd1.Dispose();
                conn1.Close();
                conn1.Dispose();
                SetDefaultConStr();
            }
            catch (Exception ex)
            {
                SetDefaultConStr();
                transaction.Rollback();
                try
                {
                    HttpContext.Current.Response.Write("<script>alert(\"Server is too busy\\n Error Message:Data was not saved" + ex.Message + "\")</script>");
                    HttpContext.Current.Response.End();
                }
                catch (Exception e) { }
                throw ex;

            }

        }
        return rowsAffected;

    }




}
