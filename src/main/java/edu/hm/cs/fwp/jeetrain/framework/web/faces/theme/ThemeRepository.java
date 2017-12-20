package edu.hm.cs.fwp.jeetrain.framework.web.faces.theme;

import java.util.List;

/**
 * Repository for {@link Theme}s.
 * <p>
 * This abstraction was added to encapsulate concrete lookup strategy of PrimeFaces themes.
 * </p>
 */
public interface ThemeRepository {
	/**
	 * Retrieves all available PrimeFaces themes.
	 */
	List<Theme> getAvailableThemes();
}
