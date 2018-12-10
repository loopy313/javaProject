package mail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
	private String user;
	private String password;
	private static Mail mail;
	final String smtpServer = "smtp.naver.com";
	final String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
	final String smtpPort = "587";
	final String PORT = "465";
	final Properties props = new Properties();
	private Session session;
	private Multipart mp=new MimeMultipart();  

	private Mail(){
	}
	public void setFrom(String user,String password){
		this.user=user;
		this.password=password;
		config();
	}
	private void config(){
		props.put("mail.smtp.host", smtpServer);
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "false");
		props.put("mail.smtp.socketFactory.port", PORT);
		props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");
		session=Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
	}
	
	public static Mail getInstance(){
		if(mail==null){
			mail=new Mail();
		}
		return mail;
	}
	
	public void addFile(ArrayList<File> files){
		for(File f:files){
			try {
				MimeBodyPart mb=new MimeBodyPart();
				mp.addBodyPart(mb);
				try {
					mb.attachFile(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
		
	public void sendMail(ArrayList<String> to,String subject,String body){
		
		MimeMessage message = new MimeMessage(session);
		InternetAddress[] ia=new InternetAddress[to.size()];
		try{
			message.setFrom(new InternetAddress(user));
			for(int i=0;i<to.size();++i){
				ia[i]=new InternetAddress(to.get(i));
			}
			message.addRecipients(Message.RecipientType.TO,ia);
			MimeBodyPart mb=new MimeBodyPart();
			mb.setText(body);
			mp.addBodyPart(mb);
			
			message.setSubject(subject);
			message.setContent(mp);
			Transport.send(message);
			System.out.println("message sent successfully...");
		}catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}

