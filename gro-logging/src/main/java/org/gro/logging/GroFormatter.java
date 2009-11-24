package org.gro.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

//This custom formatter formats parts of a log record to a single line
class GroFormatter extends Formatter {

	@Override
	public String format(final LogRecord rec) {

		final StringBuffer buf = new StringBuffer(1000);

		buf.append("[");
		buf.append(rec.getLevel());
		buf.append("] ");
		buf.append(calcDate(rec.getMillis()));
		buf.append("  ");

		buf.append(formatMessage(rec));
		buf.append('\n');

		return buf.toString();
	}

	private String calcDate(final long millisecs) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:SSS");
		final Date resultdate = new Date(millisecs);
		return dateFormat.format(resultdate);
	}

	// This method is called just after the handler using this
	// formatter is created
	@Override
	public String getHead(final Handler h) {
		return "";
	}

	// This method is called just after the handler using this
	// formatter is closed
	@Override
	public String getTail(final Handler h) {
		return "";
	}

}
