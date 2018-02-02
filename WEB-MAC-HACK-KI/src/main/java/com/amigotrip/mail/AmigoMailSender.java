package com.amigotrip.mail;

import com.amigotrip.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Naver on 2017. 10. 24..
 */

@Slf4j
@Component
public class AmigoMailSender {
    String emailFromAddress = "amigotrip82@gmail.com";

    @Value("${service.domain}")
    private String serviceDomain; // "localhost:8080" for local, "amigotrip.co.kr" for dev

    public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
        boolean debug = false;
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        String SMTP_HOST_NAME = "gmail-smtp.l.google.com";

        // Properties 설정
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(props, auth);

        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/html");
        Transport.send(msg);
    }

    private String sendMail(String[] emailList, String emailMsgTxt, String emailSubjectTxt) {
        try {
            // 메일보내기
            postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
            log.debug("mail sended to {}", (Object) emailList);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Success";
    }

    @Async
    public void sendEmailConfirmMail(User user) {
        String[] toSend = {user.getEmail()};
        String emailSubject =  "Hi " + user.getName() + ", this is Amigo!";
        STGroup group = new STGroupFile("templates/mailConfigure.stg");
        group.registerRenderer(String.class, new StringRenderer());
        ST st = group.getInstanceOf("page");
        st.add("name", user.getName());
        String urlValue = serviceDomain + "/users/" + user.getId() + "/emailConfirm/" + user.getEmailConfirmKey();
        log.debug("url value : {}", urlValue);
        st.add("url", urlValue);
        String emailTxt = st.render();
        sendMail(toSend, emailTxt, emailSubject);
    }

    /**
     * 구글 사용자 메일 계정 아이디/패스 정보
     */
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "amigotrip82@gmail.com";
            String password = "machackki";
//            try {
//                BufferedReader in = new BufferedReader(new FileReader("src/main/java/amigo/com/mail/user_info"));
//                username = in.readLine();
//                password = in.readLine();
//            } catch(IOException e) {
//                log.debug("{}", e);
//            }
            return new PasswordAuthentication(username, password);
        }
    }
}
