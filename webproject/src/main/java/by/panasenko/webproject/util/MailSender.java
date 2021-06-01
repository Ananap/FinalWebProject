package by.panasenko.webproject.util;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailSender {
    private static final Logger logger = LogManager.getLogger(MailSender.class);
    private static final Properties properties = new Properties();
    private static final String MAIL_PROPERTIES = "mail.properties";
    private static final String MAIL_USER_NAME = "mail.user.name";
    private static final String MAIL_USER_PASSWORD = "mail.user.password";
    private static final String MAIL_FROM = "mail.from";


    static {
        try (InputStream inputStream = MailSender.class.getClassLoader().getResourceAsStream(MAIL_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Properties exception: " + e.getMessage());
        }

    }

    public static void sendEmail(String emailTo, String subject, String messageText) {
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(properties.getProperty(MAIL_USER_NAME), properties.getProperty(MAIL_USER_PASSWORD));
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty(MAIL_FROM)));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(messageText);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "MessagingException: " + e.getMessage());
        }
    }
}
