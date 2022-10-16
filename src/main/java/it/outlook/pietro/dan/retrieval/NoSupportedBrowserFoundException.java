package it.outlook.pietro.dan.retrieval;

/**
 * Signals that no supported browser for scraping was found.
 * @author Pietro Danieli
 *
 */
public class NoSupportedBrowserFoundException extends Exception {
	public NoSupportedBrowserFoundException(String error) {
		super(error);
	}
}
