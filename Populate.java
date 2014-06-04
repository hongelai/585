import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Populate
{
    private static String JDBC_URL,userName,password;
    public static void main(String[] args) throws IOException
    {
        String[] line= new String[5];
        int i = 0;       
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
 
        while (((line[i++] = br.readLine()) != null) && i < 5);
        JDBC_URL = "jdbc:mysql://" + line[0] + ":" + line[1] + "/" + line[2];
        userName = line[3];
        password = line[4];
        br.close();
		try {
             Connection conn = getCon(JDBC_URL, userName, password);
             deleteTable(conn,"Earnings");
             deleteTable(conn,"Matches");
             deleteTable(conn,"Tournaments");
             deleteTable(conn,"Members");
             deleteTable(conn,"Teams");
             deleteTable(conn,"Players");
             importPlayers(conn,args[1]);             
             importTeams(conn,args[2]);             
             importMembers(conn,args[3]);            
             importTournaments(conn,args[4]);            
             importMatches(conn,args[5]);
             importEarnings(conn,args[6]);

		} catch (Exception e) {
			e.printStackTrace();
		}        
        
    }
    
    public static java.sql.Date convertDate(String str) throws ParseException
    {
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        str = str.replaceAll("\"","");
		java.util.Date ud = format.parse(str);
		java.sql.Date date = new java.sql.Date(ud.getTime());
		return date;
    }
    
    public static void importPlayers(Connection conn,String filename)
    {
    	PreparedStatement  pstmt;
        String query;

        try
        {
            query = "INSERT INTO Players(player_id,tag,real_name,nationality,birthday,game_race) VALUES(?,?,?,?,?,?);";
            pstmt = conn.prepareStatement(query);
            
            BufferedReader br = new BufferedReader(new FileReader("cs585_20133_assignment_3_data_v2\\"+filename));
            String ret;

            while((ret = br.readLine()) != null)
            {
                ret = ret.trim();
                if(!"".equals(ret) && ret.length() > 0)
                {                   
                    String[] str = ret.split(",",-1);
                    for(int i = 0; i<str.length; i++) 
                        str[i] = str[i].replaceAll("\"","");
                    if(str[0] == null || str[0].trim().equals(""))
                    	pstmt.setNull(1, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(1,Integer.parseInt(str[0]));
                    if(str[1] == null || str[1].trim().equals(""))
                    	pstmt.setNull(2, java.sql.Types.VARCHAR);
                    else                       
                        pstmt.setString(2,str[1]);
                    if(str[2] == null || str[2].trim().equals(""))
                    	pstmt.setNull(3, java.sql.Types.VARCHAR);
                    else
                        pstmt.setString(3,str[2]);
                    if(str[3] == null || str[3].trim().equals(""))
                    	pstmt.setNull(4, java.sql.Types.CHAR);
                    else
                        pstmt.setString(4,str[3]);
                    if(str[4] == null || str[4].trim().equals("")) 
                    	pstmt.setNull(5, java.sql.Types.DATE);
                    else
                        pstmt.setDate(5,convertDate(str[4]));      
                    if(str[5] == null || str[5].trim().equals(""))
                    	pstmt.setNull(6, java.sql.Types.VARCHAR);
                    else
                        pstmt.setString(6,str[5]);     
                        
                    pstmt.executeUpdate();
                }
            }            
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            pstmt = null;
        }
    }
    public static void importTeams(Connection conn,String filename)
    {
    	PreparedStatement  pstmt;
        String query;

        try
        {
            query = "INSERT INTO Teams(team_id,name,founded,disbanded) VALUES(?,?,?,?);";
            pstmt = conn.prepareStatement(query);
            
            BufferedReader br = new BufferedReader(new FileReader("cs585_20133_assignment_3_data_v2\\"+filename));
            String ret;

            while((ret = br.readLine()) != null)
            {
                ret = ret.trim();
                if(!"".equals(ret) && ret.length() > 0)
                {                   
                    String[] str = ret.split(",",-1);
                    for(int i = 0; i<str.length; i++) 
                        str[i] = str[i].replaceAll("\"","");
                    if(str[0] == null || str[0].trim().equals(""))
                    	pstmt.setNull(1, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(1,Integer.parseInt(str[0]));
                    if(str[1] == null || str[1].trim().equals(""))
                    	pstmt.setNull(2, java.sql.Types.VARCHAR);
                    else                       
                        pstmt.setString(2,str[1]);
                    if(str[2] == null || str[2].trim().equals(""))
                    	pstmt.setNull(3, java.sql.Types.DATE);
                    else
                        pstmt.setDate(3,convertDate(str[2]));
                    if(str[3] == null || str[3].trim().equals(""))
                    	pstmt.setNull(4, java.sql.Types.DATE);
                    else
                        pstmt.setDate(4,convertDate(str[3]));
            
                    pstmt.executeUpdate();
                }
            }            
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            pstmt = null;
        }
    }
    public static void importMembers(Connection conn,String filename)
    {
    	PreparedStatement  pstmt;
        String query;

        try
        {
            query = "INSERT INTO Members(player,team,start_date,end_date) VALUES(?,?,?,?);";
            pstmt = conn.prepareStatement(query);
            
            BufferedReader br = new BufferedReader(new FileReader("cs585_20133_assignment_3_data_v2\\"+filename));
            String ret;

            while((ret = br.readLine()) != null)
            {
                ret = ret.trim();
                if(!"".equals(ret) && ret.length() > 0)
                {                   
                    String[] str = ret.split(",",-1);
                    for(int i = 0; i<str.length; i++) 
                        str[i] = str[i].replaceAll("\"","");
                    if(str[0] == null || str[0].trim().equals(""))
                    	pstmt.setNull(1, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(1,Integer.parseInt(str[0]));
                    if(str[1] == null || str[1].trim().equals(""))
                    	pstmt.setNull(2, java.sql.Types.INTEGER);
                    else                       
                        pstmt.setInt(2,Integer.parseInt(str[1]));
                    if(str[2] == null || str[2].trim().equals(""))
                    	pstmt.setNull(3, java.sql.Types.DATE);
                    else
                        pstmt.setDate(3,convertDate(str[2]));
                    if(str[3] == null || str[3].trim().equals(""))
                    	pstmt.setNull(4, java.sql.Types.DATE);
                    else
                        pstmt.setDate(4,convertDate(str[3]));
            
                    pstmt.executeUpdate();
                }
            }            
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            pstmt = null;
        }
    }
    public static void importTournaments(Connection conn,String filename)
    {
    	PreparedStatement  pstmt;
        String query;

        try
        {
            query = "INSERT INTO Tournaments(tournament_id,name,region,major) VALUES(?,?,?,?);";
            pstmt = conn.prepareStatement(query);
            
            BufferedReader br = new BufferedReader(new FileReader("cs585_20133_assignment_3_data_v2\\"+filename));
            String ret;

            while((ret = br.readLine()) != null)
            {
                ret = ret.trim();
                if(!"".equals(ret) && ret.length() > 0)
                {                   
                    String[] str = ret.split(",",-1);
                    for(int i = 0; i<str.length; i++) 
                        str[i] = str[i].replaceAll("\"","");
                    if(str[0] == null || str[0].trim().equals(""))
                    	pstmt.setNull(1, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(1,Integer.parseInt(str[0]));
                    if(str[1] == null || str[1].trim().equals(""))
                    	pstmt.setNull(2, java.sql.Types.VARCHAR);
                    else                       
                        pstmt.setString(2,str[1]);
                    if(str[2] == null || str[2].trim().equals(""))
                    	pstmt.setNull(3, java.sql.Types.CHAR);
                    else
                        pstmt.setString(3,str[2]);
                    if(str[3] == null || str[3].trim().equals(""))
                    	pstmt.setNull(4, java.sql.Types.CHAR);
                    else
                        pstmt.setString(4,str[3]);
            
                    pstmt.executeUpdate();
                }
            }            
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            pstmt = null;
        }
    }
    public static void importMatches(Connection conn,String filename)
    {
    	PreparedStatement  pstmt;
        String query;

        try
        {
            query = "INSERT INTO Matches(match_id,date,tournament,playerA,playerB,scoreA,scoreB,offline) VALUES(?,?,?,?,?,?,?,?);";
            pstmt = conn.prepareStatement(query);
            
            BufferedReader br = new BufferedReader(new FileReader("cs585_20133_assignment_3_data_v2\\"+filename));
            String ret;

            while((ret = br.readLine()) != null)
            {
                ret = ret.trim();
                if(!"".equals(ret) && ret.length() > 0)
                {                   
                    String[] str = ret.split(",",-1);
                    for(int i = 0; i<str.length; i++) 
                        str[i] = str[i].replaceAll("\"","");
                    if(str[0] == null || str[0].trim().equals(""))
                    	pstmt.setNull(1, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(1,Integer.parseInt(str[0]));
                    if(str[1] == null || str[1].trim().equals(""))
                    	pstmt.setNull(2, java.sql.Types.DATE);
                    else                       
                        pstmt.setDate(2,convertDate(str[1]));
                    if(str[2] == null || str[2].trim().equals(""))
                    	pstmt.setNull(3, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(3,Integer.parseInt(str[2]));
                    if(str[3] == null || str[3].trim().equals(""))
                    	pstmt.setNull(4, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(4,Integer.parseInt(str[3]));
                    if(str[4] == null || str[4].trim().equals("")) 
                    	pstmt.setNull(5, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(5,Integer.parseInt(str[4]));      
                    if(str[5] == null || str[5].trim().equals(""))
                    	pstmt.setNull(6, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(6,Integer.parseInt(str[5]));      
                    if(str[6] == null || str[6].trim().equals(""))
                    	pstmt.setNull(7, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(7,Integer.parseInt(str[6]));     
                    if(str[7] == null || str[7].trim().equals(""))
                    	pstmt.setNull(8, java.sql.Types.CHAR);
                    else
                        pstmt.setString(8,str[7]);      
                        
                    pstmt.executeUpdate();
                }
            }            
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            pstmt = null;
        }
    }
    public static void importEarnings(Connection conn,String filename)
    {
    	PreparedStatement  pstmt;
        String query;

        try
        {
            query = "INSERT INTO Earnings(tournament,player,prize_money,position) VALUES(?,?,?,?);";
            pstmt = conn.prepareStatement(query);
            
            BufferedReader br = new BufferedReader(new FileReader("cs585_20133_assignment_3_data_v2\\"+filename));
            String ret;

            while((ret = br.readLine()) != null)
            {
                ret = ret.trim();
                if(!"".equals(ret) && ret.length() > 0)
                {                   
                    String[] str = ret.split(",",-1);
                    for(int i = 0; i<str.length; i++) 
                        str[i] = str[i].replaceAll("\"","");
                    if(str[0] == null || str[0].trim().equals(""))
                    	pstmt.setNull(1, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(1,Integer.parseInt(str[0]));
                    if(str[1] == null || str[1].trim().equals(""))
                    	pstmt.setNull(2, java.sql.Types.INTEGER);
                    else                       
                        pstmt.setInt(2,Integer.parseInt(str[1]));
                    if(str[2] == null || str[2].trim().equals(""))
                    	pstmt.setNull(3, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(3,Integer.parseInt(str[2]));
                    if(str[3] == null || str[3].trim().equals(""))
                    	pstmt.setNull(4, java.sql.Types.INTEGER);
                    else
                        pstmt.setInt(4,Integer.parseInt(str[3]));
            
                    pstmt.executeUpdate();
                }
            }            
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            pstmt = null;
        }
    }
    public static void deleteTable(Connection conn, String tableName){
    	PreparedStatement  pstmt;
        String delete;

        try
        {
            delete = "DELETE FROM "+tableName+";";
            pstmt = conn.prepareStatement(delete);            
            pstmt.executeUpdate();           
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            pstmt = null;
        }    
    
    }
	private static Connection getCon(String JDBC_URL, String userName, String password) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(JDBC_URL,userName,password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
}


