/* PasswordEncoderBean.java
 */
package edu.hm.cs.fwp.jeetrain.business.users.control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.ejb.Stateless;

/**
 * {@code Control}, der Kennwörter verschlüsselt, damit sie sicher in der
 * User-Datenbank abgespeichert werden können.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 22:53:48
 */
@Stateless
public class PasswordEncoderBean {
	/**
	 * Verschlüsselt das angegebene Kennwort mit einem SHA-256-Algorithmus und
	 * liefert das Ergebnis der Verschlüsselung als Base64-enkodierten String
	 * zurück.
	 */
	public String encode(String password) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Unable to encrypt password", ex);
		}
		md.reset();

		byte[] encryptedPassword = md.digest(password.getBytes());
		return Base64.getEncoder().encodeToString(encryptedPassword);
	}
}
