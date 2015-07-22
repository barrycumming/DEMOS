import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavamailDemo {
	public static void main(String[] args) {
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
		System.out.println("properties setup success");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mail@gmail.com", "password");
			}
		});
		
		/* TO CONNECT VIA HOTMAIL SERVERS:
		props.setProperty("mail.transport.protocol", "smtp");
	    props.setProperty("mail.host", "smtp.live.com");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.auth", "true");
	    
	    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mail@hotmail.co.uk", "password");
			}
		});*/
		
		try {
			String htmlString = readHTMLfile();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mail@gmail.com"));
			message.setSubject("Weekly newsletter!");
			message.setContent(htmlString, "text/html");
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mail@somewhere.com"));
			Transport.send(message);
			System.out.println("Message sent succesfully!");
		} catch(Exception e) {
			System.out.println("Error in sending mail! Error message: " + e.getMessage());
		}
	}
	
	/**
	 * Function reads a file from local storage and converts .html into a String to parse onto email
	 * @return A String of the .html file with email contents
	 */
	private static String readHTMLfile() {
		String str = "";
		try {
			Scanner sc = new Scanner(new File("src/htmlMessage.html")).useDelimiter("'");
			str += sc.next();
			str += "\'";
			while (sc.hasNext()) {
				str += "\'";
				str += sc.next();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		return str;
	}
}