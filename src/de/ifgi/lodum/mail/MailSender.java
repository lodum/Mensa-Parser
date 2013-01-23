package de.ifgi.lodum.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.ifgi.lodum.Cfg;

/**
 * Provides a method to send emails.
 * 
 * @author steffan
 * 
 */
public class MailSender {

	private MailSender() {}

	/**
	 * Sends a mail with the specified message. The to and from address are taken from the config file.
	 * 
	 * @param message the message to send.
	 */
	public static void sentMessage(String message) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.uni-muenster.de");
		Session session = Session.getDefaultInstance(props);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom;
		try {
			addressFrom = new InternetAddress(Cfg.FROM);
			msg.setFrom(addressFrom);
			InternetAddress addressTo = new InternetAddress(
					Cfg.TO);
			msg.setRecipient(Message.RecipientType.TO, addressTo);
			msg.setSubject("Error in Cafeteria to Lodum converter");
			msg.setContent(message, "text/plain");
			Transport.send(msg);
		} catch (AddressException e) {
			
			e.printStackTrace();
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

	}

}
