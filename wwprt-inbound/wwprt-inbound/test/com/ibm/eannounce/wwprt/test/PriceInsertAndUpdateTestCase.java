package com.ibm.eannounce.wwprt.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;
import org.w3c.dom.Document;

import com.ibm.commons.db.mapping.TableUpdate;
import com.ibm.commons.db.mapping.TableUpdate.UpdateMode;
import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.commons.db.transaction.connection.DB2ConnectionFactory;
import com.ibm.commons.db.transaction.query.Query;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.dao.ClearTempPriceTable;
import com.ibm.eannounce.wwprt.dao.MergeTablesUpdate;
import com.ibm.eannounce.wwprt.model.PriceTable;
import com.ibm.eannounce.wwprt.model.Prices;
import com.ibm.eannounce.wwprt.model.price.IgnoredPrice;
import com.ibm.eannounce.wwprt.model.price.MergePrice;

public class PriceInsertAndUpdateTestCase {

	@Test
	public void testInsertUpdatePricesSQL() throws Exception {
		InputStream in = new FileInputStream("test_data/prices.xml");
		Document insertDocument = Context.get().createDocumentBuilder().parse(in);
		in.close();

		Context.get().setPriceTable("PRICE");
		Context.get().setTempPriceTable("TEMP_PRICE");

		Prices prices = new Prices();

		//Insert data
		prices.loadXML(insertDocument);
		System.out.println("INSERT Prices ID: " + prices.getId());
		while (prices.hasNextPrice()) {
			PriceTable price = prices.nextPrice();
			System.err.println(price.getClass());
			if (!(price instanceof IgnoredPrice)) {
				price.populateAndValidate();
				System.err.println(price.buildInsertSql());
				System.err.println(price.dumpValues());
			}
			System.err.println();
		}

		//Merge
		System.out.println(new MergeTablesUpdate(new MergePrice().getColumns()).buildSql(null));
	}

	@Test
	public void testInsertAndUpdate() throws Exception {
		Connection connection = null;
		try {
			FileInputStream in = new FileInputStream(
					new File(TestUtils.PROPERTIES));
			Properties properties = new Properties();
			properties.load(in);
			in.close();

			ConnectionFactory connectionFactory = new DB2ConnectionFactory(properties);
			connection = connectionFactory.getConnection();

			Transaction transaction = new Transaction(connection);

			in = new FileInputStream("test_data/price-insert.xml");
			Document insertDocument = Context.get().createDocumentBuilder().parse(in);
			in.close();

			in = new FileInputStream("test_data/price-update.xml");
			Document updateDocument = Context.get().createDocumentBuilder().parse(in);
			in.close();

			Context.get().setupFromProperties(properties);

			Prices prices = new Prices();

			transaction.executeUpdate(new ClearTempPriceTable());

			
			//Insert data
			prices.loadXML(insertDocument);
			System.out.println("INSERT Prices ID: " + prices.getId());
			while (prices.hasNextPrice()) {
				PriceTable price = prices.nextPrice();
				if (!(price instanceof IgnoredPrice)) {
					price.populateAndValidate();
					System.out.println(price.buildInsertSql());
					System.out.println(price.dumpValues());
					transaction.executeUpdate(new TableUpdate(price, UpdateMode.INSERT));
				}
			}

			transaction.executeUpdate(new MergeTablesUpdate(new MergePrice().getColumns()));
			

			System.out.println("---------------TEMP PRICE---------------------------");

			transaction.executeQuery(new DebugQuery(Context.get().getTempPriceTable()));

			System.out.println("--------------- PRICE ---------------------------");
			
			transaction.executeQuery(new DebugQuery(Context.get().getPriceTable()));

			System.out.println("------------------------------------------");


			//Update data
			
			transaction.executeUpdate(new ClearTempPriceTable());
			
			prices.loadXML(updateDocument);
			System.out.println("UPDATE Prices ID: " + prices.getId());
			while (prices.hasNextPrice()) {
				PriceTable price = prices.nextPrice();
				if (!(price instanceof IgnoredPrice)) {
					price.populateAndValidate();
					System.out.println(price.buildInsertSql());
					System.out.println(price.dumpValues());
					transaction.executeUpdate(new TableUpdate(price, UpdateMode.INSERT));
				}
			}

			transaction.executeUpdate(new MergeTablesUpdate(new MergePrice().getColumns()));

			System.out.println("---------------TEMP PRICE---------------------------");

			transaction.executeQuery(new DebugQuery(Context.get().getTempPriceTable()));

			System.out.println("--------------- PRICE ---------------------------");
			
			transaction.executeQuery(new DebugQuery(Context.get().getPriceTable()));

			System.out.println("------------------------------------------");

			transaction.executeUpdate(new ClearTempPriceTable());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.rollback();
				connection.close();
			}
		}
	}
	
	
	class DebugQuery implements Query {
		
		String table;
		
		public DebugQuery(String table) {
			this.table = table;
		}

		public String buildSql(Transaction transaction) {
			return "select * from " + table;
		}

		public void handleResult(ResultSet rs) throws SQLException {
			ResultSetMetaData rsmd = rs.getMetaData();
			System.out.println("");
			int numberOfColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnName = rsmd.getColumnName(i);
				System.out.print(columnName);
			}
			System.out.println("");
			while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
				}
				System.out.println("");
			}
		}

		public void prepareStatement(PreparedStatement ps) throws SQLException {
		}

	};
}
