package COM.ibm.eannounce.abr.sg.rfc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;



public class ConnectionFactoryTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		//test
		String dbconnect= "fvtcloudpdh";
		Connection connection = ConnectionFactory.getConnection(dbconnect);
		connection.close();
		
		dbconnect= "fvtcloudods";
		connection = ConnectionFactory.getConnection(dbconnect);
		connection.close();
		
		
	}
	
	@Test
	public void test() throws ClassNotFoundException, SQLException{
		String dbconnect= "devcloud";
		Connection connection = ConnectionFactory.getConnection(dbconnect);
		
		//Hashtable<String, String> tt = SqlHelper.getSingleRow("select LASTUPDATETIME from SDPI.ASSORTMODSELECTION_T where OID='D111-21' with ur", rdhConnection);
		String sql ="select count(*) as count from cache.XMLIDLCACHE with ur";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		String count = "";
		while (resultSet.next()) {
			count = resultSet.getString("count");
			System.out.println("count=" + count);				
		}
		
		
		
		
		
	}
	
	public static Object getCustomExistingDataWithKeysConcatenated(Connection connection, String sql, String uniqueIDParameter) throws SQLException
    {
        Object object = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, uniqueIDParameter);
        ResultSet rs = preparedStatement.executeQuery();
        try
        {
            if(rs.next()){
                object = rs.getObject(1);
            }

        } finally
        {
            rs.close();
            preparedStatement.close();
        }
        return object;
    }
	
	private static Timestamp getADD_DATE(Connection connection, String sql, String uniqueIDParameter) throws SQLException
	{
    	
		//String sql = "select LASTUPDATETIME from SDPI.ASSORTMODSELECTION_T where OID=? with ur";
		Timestamp ADD_DATE = (Timestamp)getCustomExistingDataWithKeysConcatenated(connection, sql, uniqueIDParameter);
		if(ADD_DATE != null )
		{
			return ADD_DATE;
		}else{
			return getNowTime();
		}
	}
	
	public static Timestamp getNowTime()
    {
    	java.util.Date now = new java.util.Date();
        return new Timestamp(now.getTime());
    }

}
