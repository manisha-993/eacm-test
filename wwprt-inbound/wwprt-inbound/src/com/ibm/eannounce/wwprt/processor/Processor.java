package com.ibm.eannounce.wwprt.processor;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;

import com.ibm.commons.db.transaction.Transaction;
import com.ibm.commons.db.transaction.connection.ConnectionFactory;
import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.Log;
import com.ibm.eannounce.wwprt.dao.ClearTempPriceTable;
import com.ibm.eannounce.wwprt.dao.CountNewPricesXMLQuery;
import com.ibm.eannounce.wwprt.dao.InsertPrice;
import com.ibm.eannounce.wwprt.dao.MergeTablesUpdate;
import com.ibm.eannounce.wwprt.dao.SelectNewPricesXMLQuery;
import com.ibm.eannounce.wwprt.dao.UpdateOverlappingPrices;
import com.ibm.eannounce.wwprt.dao.UpdatePricesXML;
import com.ibm.eannounce.wwprt.model.PriceErrors;
import com.ibm.eannounce.wwprt.model.PriceTable;
import com.ibm.eannounce.wwprt.model.Prices;
import com.ibm.eannounce.wwprt.model.price.IgnoredPrice;
import com.ibm.eannounce.wwprt.model.price.MergePrice;
import com.ibm.eannounce.wwprt.notification.NotificationBuilder;

public class Processor implements Runnable {

	private Transaction pricesTransaction;

	private Transaction transaction;

	private SelectNewPricesXMLQuery findAndProcessPricesQuery;

	private boolean running = true;

	private Prices prices;

	private Collection<UpdatePricesXML> updateCommands;

	private long interval = 20000;

	private MergeTablesUpdate mergePrices;

	private ProcessorListener processorListener;

	private Date effectiveDate;

	private UpdateOverlappingPrices updateOverlappingPrices;

	private InsertPrice insertPrice;

	public void setProcessorListener(ProcessorListener processorListener) {
		this.processorListener = processorListener;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public void initialize() {
		ConnectionFactory connectionFactory = Context.get().getConnectionFactory();
		try {
			insertPrice = new InsertPrice();
			updateOverlappingPrices = new UpdateOverlappingPrices();
			effectiveDate = Context.XML_DATE_FORMAT.parse("9999-12-31");
			updateCommands = new ArrayList<UpdatePricesXML>();
			prices = new Prices();
			pricesTransaction = new Transaction(connectionFactory.getConnection());
			transaction = new Transaction(connectionFactory.getConnection());
			// ** move the action into the processPrice
			//mergePrices = new MergeTablesUpdate(new MergePrice().getColumns());
			findAndProcessPricesQuery = new SelectNewPricesXMLQuery() {

				@Override
				protected void handleDocument(String pricesId, Document document) {
					if (running) {
						if (Log.isLevel(Log.VERBOSE))
							Log.v("Processing Prices XML: " + pricesId);
						try {
							processPrices(document);
						} catch (ProcessorException e) {
							Log.e("ProcessorException error:" + e.getMessage());
							handleProcessorError(e);
						}
					} else {
						Log.i("Processor stopped before Prices XML: " + pricesId);
					}
				}

				@Override
				protected void handleParseError(String pricesId, Exception e) {
					Log.e("handleParseError error:" + e.getMessage());
					handleProcessorError(new ProcessorException(pricesId, "Unable to parse XML", e));
				}

			};
		} catch (SQLException e) {
			throw new IllegalStateException("Unable to connect to DB", e);
		} catch (ParseException e) {
			throw new IllegalStateException("Unable to parse effective date", e);
		}
	}

	public void dispose() {
		try {
			if (pricesTransaction != null) {
				commit(pricesTransaction.getConnection());
				pricesTransaction.getConnection().close();
			}
			if (transaction != null)
				transaction.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		Log.i("Processor is initializing...");
		try {
			initialize();
		} catch (Throwable e) {
			Log.e( "Error on processor initializing", e);
			Log.i("Processor is stopped");
			running = false;
			return;
		}
		running = true;
		Log.i("Processor is now running...");
		while (running) {
			try {
				execute();
				Thread.sleep(interval);
				if (processorListener != null) {
					processorListener.onCheck();
				}
			} catch (InterruptedException e) {
				Log.e("Error on InterruptedException loop", e);
			} catch (Throwable e) {
				Log.e("Error on processor loop", e);
				running = false;
			}
			if (!running)
				break;
		}
		Log.i("Processor is closing...");
		dispose();
		Log.i("Processor is stopped");
		if (processorListener != null) {
			processorListener.onStop();
		}
	}

	public void execute() {
		if (pricesTransaction == null)
			throw new IllegalStateException("Processor not initialized. Call initialize() before.");
		try {
			updateCommands.clear();

			//Read all pending prices and process them
			pricesTransaction.executeQuery(findAndProcessPricesQuery);

			//Update WWPRTXML table status
			int count = 0;
			for (UpdatePricesXML command : updateCommands) {
				count++;
				pricesTransaction.executeUpdate(command);
			}
			commit(pricesTransaction.getConnection());

			if (count > 0) {
				CountNewPricesXMLQuery countQuery = new CountNewPricesXMLQuery();
				pricesTransaction.executeQuery(countQuery);
				Log.i("Messages processed in this cycle: " + count + " - Total remaining: "
						+ countQuery.result);
			}
		} catch (Exception e) {
			Log.e("Error on execute method", e);
			NotificationBuilder nb = new NotificationBuilder("WWPRT Inbound - Processor problem");
			nb.exception(e);
			Context.get().getNotification().post(null, nb);
			Log.e(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			//Stop the processor thread
			running = false;
		}
	}

	private void commit(Connection connection) throws SQLException {
		Context.get().getConnectionFactory().commit(connection);
	}

	public void processPrices(Document document) throws ProcessorException {
		/*
		 * Processor
		 * 
		 * 1) Loop thru each price in the XML
		 *    Save the price in a new row in PRICE table
		 * 2) Send final MQ response
		 * 3) Update WWPRTXML (valid or invalid if there are errors)
		 */
		Log.i("Start processPrices");
		prices.loadXML(document);
		// Clear the temp table
		try {
			transaction.executeUpdate(new ClearTempPriceTable());
			Log.i("Clear temp table");
		} catch (SQLException e) {
			running = false;
			Log.e("Unable to clear temp price table", e);
			throw new ProcessorException(prices.getId(), "Unable to clear temp price table", e);
		}
		
		int count = 0;
		long time = System.currentTimeMillis();
		List<PriceErrors> errors = new LinkedList<PriceErrors>();
		Date now = new Date();
		while (prices.hasNextPrice()) {
			String sqlCommand = "";
			PriceTable price = null;
			PriceErrors validate = null;
			try {
				count++;
				/** add the new V2 price xml in the nextPrice	 */
				price = prices.nextPrice();
				if(price == null) {
					Log.i("The offeringtype is not correct, the price will be ignored");
					continue;
				}			
				
				mergePrices = new MergeTablesUpdate(new MergePrice(price.get_version()).getColumns());				
				
				
				validate = price.populateAndValidate();
				if (!(price instanceof IgnoredPrice)) {
					if (!validate.hasErrors()) {
						//Disable overlapping dates
						sqlCommand = "Updating overlapping dates";
						updateOverlappingPrices.prepare(price, now, effectiveDate);
						int result = transaction.executeUpdate(updateOverlappingPrices);
						if (result > 0) {
							if (Log.isLevel(Log.DEBUG)) {
								String startDate = Context.XML_DATE_FORMAT.format(price.getColumn(
										"START_DATE").getValue());
								String endDate = Context.XML_DATE_FORMAT.format(price.getColumn(
										"END_DATE").getValue());
								Log.d("Offering: " + price.getOffering() + " [" + startDate
										+ " -> " + endDate + "] is overlapping, " + result
										+ " overlapping date(s) set to ACTION='D'");
							}
						}
						//Insert the price in the temporary price table to merge later
						sqlCommand = "Inserting price";
						insertPrice.prepare(price);
						
						//Log.i("Start processPrices 30 insert SQL:" + insertPrice.buildSql(transaction));
						transaction.executeUpdate(insertPrice);
					}
				}
			} catch (SQLException e) {
				String offering = "NONE";
				if (price != null) {
					String startDate = Context.XML_DATE_FORMAT.format(price.getColumn("START_DATE")
							.getValue());
					String endDate = Context.XML_DATE_FORMAT.format(price.getColumn("END_DATE")
							.getValue());
					sqlCommand += " [" + startDate + " -> " + endDate + "]";
					offering = price.getOffering();
					Log.e("SQLException - " + sqlCommand + " - " + e.getMessage());
				} else {
					Log.e("SQLException - " + sqlCommand + " - " + e.getMessage());
				}

				if (validate == null) {
					validate = new PriceErrors(price == null ? "NULL_PRICE" : price.getOffering());
				}
				validate.addError(offering, sqlCommand);
			}
			if (validate != null && validate.hasErrors()) {
				errors.add(validate);
			}
		}

		mergeTemporaryTable();

		Log.i("Processed prices id: " + prices.getId() + " - Elements: " + count
				+ " - Errors: " + errors.size() + " Time: " + (System.currentTimeMillis() - time)
				+ "ms");

		//Can commit now
		try {
			commit(transaction.getConnection());
		} catch (SQLException e1) {
			Log.e("Error on commit method", e1);
			throw new ProcessorException(prices.getId(), "Unable to commit transaction", e1);
		}

		//2. Send MQ
		Context context = Context.get();
		context.sendFinalResponse(errors.isEmpty(), prices.getId(), errors);

		//3. Update WWPRTXML table
		if (errors.isEmpty()) {
			updateCommands.add(new UpdatePricesXML(prices.getId(), Prices.STATUS_COMPLETE, null));
		} else {
			String errorMsg = context.errorsToString(errors);
			updateCommands.add(new UpdatePricesXML(prices.getId(), Prices.STATUS_ERROR, errorMsg));
			NotificationBuilder nb = new NotificationBuilder("WWPRT Inbound - Processor problem ("
					+ prices.getId() + ")");
			errorMsg = errorMsg.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replace("\n",
					"<br>");
			nb.p(errorMsg);
			context.getNotification().post(prices.getId(), nb);
		}
	}

	private void mergeTemporaryTable() throws ProcessorException {
		//Merge all prices (insert or update)
		try {
			transaction.executeUpdate(mergePrices);
			commit(transaction.getConnection());
		} catch (SQLException e) {
			Log.e( "Unable to merge temp with price table: "+e.getMessage(), e);
			throw new ProcessorException(prices.getId(), "Unable to merge temp with price table", e);
		}
	}

	protected void handleProcessorError(ProcessorException e) {
		String pricesId = e.getPricesId();
		Log.e("Processor error - Prices ID = " + pricesId + " - " + e.getMessage());
		String errorMsg = e.getClass().getName() + ": " + e.getMessage();
		if (e.getCause() != null) {
			errorMsg += "\nCause: " + e.getCause().getClass().getName() + ": "
					+ e.getCause().getMessage();
		}
		updateCommands.add(new UpdatePricesXML(pricesId, Prices.STATUS_ERROR, errorMsg));
		e.printStackTrace();

		NotificationBuilder nb = new NotificationBuilder("WWPRT Inbound - Processor problem");
		nb.exception(e);
		Context.get().getNotification().post(pricesId, nb);

		// Send MQ fail response
		if (e.isSendFailResponse()) {
			Context.get().sendFinalResponse(false, prices.getId(), new ArrayList<PriceErrors>());
		}
	}

}
