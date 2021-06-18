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
    private static final String MAIL_SUBJECT = "MyFlowershop";
    private static final String MAIN_MESSAGE = "Hello, %s! \n" + "Welcome to My FlowerShop. \n" + "Your password is %s.";
    private static final String FORGET_PASSWORD_MESSAGE = "Hello, %s! \n" + "Welcome to My FlowerShop. \n" + "Your new password is %s \n Change your password in your account, if you wish";


    static {
        try (InputStream inputStream = MailSender.class.getClassLoader().getResourceAsStream(MAIL_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Properties exception: " + e.getMessage());
        }

    }

    public static String messageEmailUser(String username, String password) {
        return String.format(MAIN_MESSAGE,
                username,
                password
        );
    }

    public static String messageForgetPassword(String username, String password) {
        return String.format(FORGET_PASSWORD_MESSAGE,
                username,
                password
        );
    }

    public static void send(String emailTo, String messageText) {
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
            message.setSubject(MAIL_SUBJECT);
            message.setText(messageText);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "MessagingException: " + e.getMessage());
        }
    }
}
