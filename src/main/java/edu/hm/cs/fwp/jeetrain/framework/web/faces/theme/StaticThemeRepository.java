package edu.hm.cs.fwp.jeetrain.framework.web.faces.theme;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of a {@link ThemeRepository} based on a constant array.
 */
@Singleton
public class StaticThemeRepository implements ThemeRepository {
	@Override
	public List<Theme> getAvailableThemes() {
		List<Theme> result = new ArrayList<>();
		result.add(new Theme(
				"afterdark", "primefaces-afterdark", "/resources/primefaces-afterdark/layouts", "images",
				"styles", "scripts"));
		result.add(new Theme(
				"blitzer", "primefaces-blitzer", "/resources/primefaces-blitzer/layouts", "images",
				"styles", "scripts"));
		result.add(new Theme(
				"bootstrap", "primefaces-bootstrap", "/resources/primefaces-bootstrap/layouts", "images",
				"styles", "scripts"));
		return result;
	}
}
