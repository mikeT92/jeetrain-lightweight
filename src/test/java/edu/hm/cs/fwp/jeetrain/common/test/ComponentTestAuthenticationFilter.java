package edu.hm.cs.fwp.jeetrain.common.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.enterprise.security.ee.auth.login.ProgrammaticLogin;

/**
 * {@code Filter} for component tests which require an authenticated user.
 *
 * @author Michael Theis (mtheis@hm.edu)
 * @version 1.0
 * @since release 2018.0
 */
@WebFilter(urlPatterns = { "/*" })
public class ComponentTestAuthenticationFilter implements Filter {

	private static final String TEST_USER_NAME = "arquillian";
	private static final String TEST_USER_PASSWORD = "arquillian";
	private static final String TEST_USER_REALM = "JEETRAIN_REALM";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final ProgrammaticLogin login = new ProgrammaticLogin();
		try {
			login.login(TEST_USER_NAME, TEST_USER_PASSWORD.toCharArray(), TEST_USER_REALM, (HttpServletRequest) request,
					(HttpServletResponse) response, true);
		} catch (final Exception ex) {
			throw new ServletException(String.format("Unable to login test user [%s]", TEST_USER_NAME), ex);
		}

		chain.doFilter(request, response);

		try {
			login.logout((HttpServletRequest) request, (HttpServletResponse) response, true);
		} catch (final Exception ex) {
			throw new ServletException(String.format("Unable to logout test user [%s]", TEST_USER_NAME), ex);
		}
	}

	@Override
	public void destroy() {
	}
}
