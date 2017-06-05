/* PasswordEncoderBean.java
 */
package edu.hm.cs.fwp.jeetrain.business.users.control;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
@Stateless // TODO: change to CDI managed bean
public class PasswordEncoderBean {

	private String encryptionAlgorithm = "SHA-256";
	private Charset encodingCharset = StandardCharsets.UTF_8;

	/**
	 * Verschlüsselt das angegebene Kennwort mit einem SHA-256-Algorithmus und
	 * liefert das Ergebnis der Verschlüsselung als Base64-enkodierten String
	 * zurück.
	 */
	public String encode(String password) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(this.encryptionAlgorithm);
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException(
					String.format("Unable to encrypt password due to missing algorithm [%s]", this.encryptionAlgorithm),
					ex);
		}
		md.reset();
		md.update(password.getBytes(this.encodingCharset));
		byte[] encryptedPassword = md.digest();
		byte[] base64EncodedPassword = Base64.getEncoder().encode(encryptedPassword);
		return new String(base64EncodedPassword, this.encodingCharset);
	}
}
