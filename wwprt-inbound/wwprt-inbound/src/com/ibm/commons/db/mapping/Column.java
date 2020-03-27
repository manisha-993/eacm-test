package com.ibm.commons.db.mapping;

import com.ibm.commons.db.mapping.column.ColumnType;
import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;

public class Column<T> {

	private String columnName;

	private ColumnType<T> columnType;

	private ValueSource<T> valueSource;

	private boolean pk = false;

	private boolean required = false;

	private T value;

	public Column(String columnName, ColumnType<T> columnType, ValueSource<T> valueSource,
			boolean required, boolean pk) {
		this.columnName = columnName;
		this.columnType = columnType;
		this.valueSource = valueSource;
		this.pk = pk;
		this.required = required;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public ColumnType<T> getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType<T> columnType) {
		this.columnType = columnType;
	}

	public ValueSource<T> getValueSource() {
		return valueSource;
	}

	public void setValueSource(ValueSource<T> valueSource) {
		this.valueSource = valueSource;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public T getValue() {
		return value;
	}

	public void validate() throws InvalidColumnException {
		if (valueSource == null) {
			if (required) {
				throw new InvalidColumnException(columnName, "Cannot be null");
			} else {
				value = null;
				return;
			}
		}

		value = valueSource.getValue();
		if (required && !(valueSource instanceof ConstantStringSource)) {
			if (value == null) {
				throw new InvalidColumnException(columnName, "Cannot be null");
			}

			if (value instanceof String) {
				if (((String) value).length() == 0) {
					throw new InvalidColumnException(columnName, "Cannot be empty");
				}
			}
		}
	}

}
