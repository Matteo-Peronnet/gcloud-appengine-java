package com.zenika.zencontact.email;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
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

}
