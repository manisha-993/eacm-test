package com.ibm.commons.db.mapping.column;


public abstract class ColumnTypes {

	public static final DateColumn DATE = new DateColumn();

	public static final TimestampColumn TIMESTAMP = new TimestampColumn();

	public static final DoubleColumn DOUBLE = new DoubleColumn();
	
	public static final IntegerColumn INT = new IntegerColumn();

	public static final StringColumn STRING = new StringColumn();

	public static final ClobColumn CLOB = new ClobColumn();

}
