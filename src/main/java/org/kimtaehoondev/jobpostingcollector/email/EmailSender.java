package org.kimtaehoondev.jobpostingcollector.email;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.kimtaehoondev.jobpostingcollector.email.repository.EmailRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {
    private final EmailConfig emailConfig;
    private final Properties emailProperties;
    private final EmailRepository emailRepository;

    public void sendToAll(String title, String content) {
        for (String email : emailRepository.findAll()) {
            send(email, title, content);
        }
    }

    private void send(String email, String title, String content) {
        try {
            Session session = getSession();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfig.getUsername()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(title);
            message.setText(content, emailConfig.getEncType(), "html"); // HTML 내용

            Transport.send(message);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Session getSession() {
        return Session.getDefaultInstance(emailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });
    }
}
