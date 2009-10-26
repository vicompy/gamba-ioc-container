package org.homs.gamba.logging.interfaces;

public interface ILog {

	public abstract void fatal(final String msg);

	public abstract void error(final String msg);

	public abstract void warning(final String msg);

	public abstract void info(final String msg);

	public abstract void debug(final String msg);

	public abstract void fatal(final Exception e);

	public abstract void error(final Exception e);

	public abstract void warning(final Exception e);

	public abstract void info(final Exception e);

	public abstract void debug(final Exception e);

}