/* QueryParametersUtil.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.view;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class that handles extraction of query parameters from strings and
 * vice versa.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 03.02.2012 11:30:23
 */
public final class QueryParametersUtil {

	public static final char QUERY_START_CHAR = '?';
	public static final char PARAM_SEPARATOR_CHAR = '&';
	public static final char NAME_VALUE_SEPARATOR_CHAR = '=';

	/**
	 * Extracts all parameters from the specified query string and returns them
	 * as parameter map.
	 */
	public static Map<String, String> split(String query) {
		Map<String, String> result = null;
		int queryStartIndex = query.indexOf(QUERY_START_CHAR);
		if (queryStartIndex != -1) {
			result = new LinkedHashMap<String, String>();
			int currentIndex = queryStartIndex + 1;
			while (currentIndex < query.length()) {
				// extract name
				int startOfNameIndex = currentIndex;
				int endOfNameIndex = query.indexOf(NAME_VALUE_SEPARATOR_CHAR, startOfNameIndex);
				String name = query.substring(startOfNameIndex, endOfNameIndex);
				// extract value
				int startOfValueIndex = endOfNameIndex + 1;
				int endOfValueIndex = query.indexOf(PARAM_SEPARATOR_CHAR, startOfValueIndex);
				if (endOfValueIndex == -1)
					endOfValueIndex = query.length();
				String value = query.substring(startOfValueIndex, endOfValueIndex);
				// add name/value pair
				result.put(name, value);
				// contine with next parameter if any
				currentIndex = endOfValueIndex + 1;
			}
		}
		return result;
	}
}
