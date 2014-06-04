import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Hw3
{
    private static String JDBC_URL,userName,password;
    public static void main(String[] args) throws IOException
    {
        String[] line= new String[5];
        int i = 0;    
        try{
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
     
            while (((line[i++] = br.readLine()) != null) && i < 5);
            JDBC_URL = "jdbc:mysql://" + line[0] + ":" + line[1] + "/" + line[2];
            userName = line[3];
            password = line[4];
            br.close();
        }catch (Exception e) {
			System.out.println("error occured when reading configured parameter");
            e.printStackTrace();
		}  
		try {
            Connection conn = getCon(JDBC_URL, userName, password);
            if(args[1].equals("q1"))
                problem1(conn,args[2],args[3]);
            else if(args[1].equals("q2")) 
                problem2(conn,Integer.parseInt(args[2]),Integer.parseInt(args[3]));
            else
                System.out.println("wrong input parameter:2");  

		} catch (Exception e) {
			e.printStackTrace();
		}        
        
    }

	public static void problem1(Connection conn,String para1, String para2)
    {
    	PreparedStatement  stmt;
        String query,real_name,tag, nationality;

        try
        {
            query = "select  Players.real_name, Players.tag,Players.nationality from Players where  birthday like '%"+para1+"-"+para2+"%';";
            stmt = conn.prepareStatement(query);            
            ResultSet rs = stmt.executeQuery(query);
            System.out.format("%-20s%-20s%-10s\n","real_name","tag","nationality");
            while (rs.next()) { 
             real_name = rs.getString("real_name");
             if(rs.wasNull()) real_name = "";
             tag = rs.getString("tag");
             if(rs.wasNull()) tag = "";
             nationality = rs.getString("nationality");
             if(rs.wasNull()) nationality = "";
             System.out.format("%-20s%-20s%-10s",real_name,tag,nationality);
             System.out.println();

            }
            rs.close();                       
            stmt.close();   
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            stmt = null;
        }
    }

    public static void problem2(Connection conn, int player_id, int team_id) {
		
    	PreparedStatement pstmt = null;
    	String insertMembers = "INSERT INTO Members(player,team,start_date,end_date) VALUES(?,?,?,?);";
    	
    	if(!existsPlayer(conn, player_id))
    	{
    		System.err.println("Player does not exists!");
    	}
    	else
    	{
    		if(!existsTeam(conn,team_id))
    		{
    			System.err.println("Team does not exists!");
    		}
    		else
    		{
    			if(!existsCurrentMembership(conn, player_id))
    			{
    				try {
						pstmt = conn.prepareStatement(insertMembers);
						insertMember(conn,pstmt,player_id,team_id);
					} catch (SQLException e) {
						e.printStackTrace();
					}
    				
    			}
    			else
    			{
    				if(!existsCurrentMembership(conn, player_id, team_id))
    				{
    					//update old team and insert new membership to new team  					
						try {							
							pstmt = conn.prepareStatement(insertMembers);
							insertMember(conn,pstmt,player_id,team_id);
                            if(existsCurrentMembership(conn,player_id,team_id)){
                                departFromOldTeam(conn,player_id);
                                String update = "UPDATE Members SET end_date= NULL WHERE player ="+player_id+" AND team ="+team_id+";";      	
                                try {
                                    pstmt = conn.prepareStatement(update);
                                    pstmt.executeUpdate();
                                    } catch (SQLException e) {
                                            e.printStackTrace();
                                    }
                                System.out.println("Update: Player add successfully.");
                            }
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
    				}
                    else
                    {
                        System.out.println("No Update: Player is already in the team.");
                    }
    			}
    		}
    	}
	}
    private static boolean existsPlayer(Connection conn, int player_id){
    	PreparedStatement pstmt;
    	String query = "SELECT player_id FROM Players WHERE player_id ="+player_id+";";
    	try{
	    	pstmt = conn.prepareStatement(query); 
	    	ResultSet rs = pstmt.executeQuery(query);	    	
	    	if(rs.next())
	    		return true;

    	} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
    private static boolean existsTeam(Connection conn, int team_id){
    	PreparedStatement pstmt;
    	String query = "SELECT team_id FROM Teams WHERE team_id ="+team_id+";";
    	try{
	    	pstmt = conn.prepareStatement(query); 
	    	ResultSet rs = pstmt.executeQuery(query);
	    	if(rs.next())
	    		return true;

    	} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
    private static boolean existsCurrentMembership(Connection conn, int player_id){
    	PreparedStatement pstmt;
    	String query = "SELECT * FROM Members WHERE player ="+player_id+" AND end_date is null;";
    	try{
	    	pstmt = conn.prepareStatement(query); 
	    	ResultSet rs = pstmt.executeQuery(query);
	    	if(rs.next()){	    	    
	    		    return true;
	    	}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
    private static boolean existsCurrentMembership(Connection conn, int player_id, int team_id){
    	PreparedStatement pstmt;
    	String query = "SELECT * FROM Members WHERE player ="+player_id+" AND team = "+team_id+" AND end_date is null;";
    	try{
	    	pstmt = conn.prepareStatement(query); 
	    	ResultSet rs = pstmt.executeQuery(query);
	    	if(rs.next()){	    	    
    		    return true;
    	}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
	private static java.sql.Date setCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date= new Date();
        String utildate_s = sdf.format(date);
        java.util.Date utildate_d = null; 
		try {
			utildate_d = sdf.parse(utildate_s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date sd = new java.sql.Date(utildate_d.getTime());
		return sd;		
	}

    private static void insertMember(Connection conn, PreparedStatement pstmt, int player_id,int team_id){
   
		try {
		    pstmt.setInt(1,player_id);
		    pstmt.setInt(2,team_id);
		    pstmt.setDate(3, setCurrentDate());
		    pstmt.setNull(4, java.sql.Types.DATE);
		    pstmt.executeUpdate();	  
		} catch (SQLException e) {
			e.printStackTrace();
		}   
    }

    private static void departFromOldTeam(Connection conn, int player_id){
    	
    	String update = "UPDATE Members SET end_date=? WHERE player ="+player_id+" AND end_date is null;";      	
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setDate(1, setCurrentDate());
			pstmt.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
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


