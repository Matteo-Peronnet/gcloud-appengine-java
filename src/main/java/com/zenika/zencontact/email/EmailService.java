package com.zenika.zencontact.email;

import com.zenika.zencontact.domain.Email;
import com.zenika.zencontact.resource.auth.AuthenticationService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailService {

    private static EmailService INSTANCE = new EmailService();
    public static EmailService getInstance() { return INSTANCE; }

    private static final Logger LOG = Logger.getLogger(EmailService.class.getName());

    public void logEmail(HttpServletRequest request) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            MimeMessage message = new MimeMessage(session, request.getInputStream());
            LOG.warning("Subject:" + message.getSubject());

            Multipart multipart = (Multipart) message.getContent();
            BodyPart part = multipart.getBodyPart(0);
            LOG.warning("Body: " + (String) part.getContent());

            for(Address sender: message.getFrom()) {
                LOG.warning("From: " + sender.toString());
            }

        } catch (Exception e) {}
    }

    public void sendEmail(Email email) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            MimeMessage message = new MimeMessage(session, null);
            message.setFrom(new InternetAddress(
                    AuthenticationService.getInstance().getUser().getEmail(),
                    AuthenticationService.getInstance().getUsername()
            ));

            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(email.to, email.toName)
            );
            message.setReplyTo(new Address[] {
               new InternetAddress("mp@epsi-20181212-mp.appspotmail.com", "Application team"),
            });

            message.setSubject(email.subject);

            message.setText(email.body);

            Transport.send(message);

            LOG.warning("mail envoyé!");

        } catch (Exception e) { }
    }
}
