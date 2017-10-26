package amigo.com.mail;

import amigo.com.domain.PartyGuest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Naver on 2017. 10. 24..
 */

@Slf4j
public class AmigoMailSender {
    String emailFromAddress = "amigotrip82@gmail.com";

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

    public String sendPartyGuestFormMail(PartyGuest partyGuest) {
        String[] toSend = {partyGuest.getEmail()};
        String[] slackMail = {"register@machackki.mailclark.ai"};

        String emailSubject = "Hi " + partyGuest.getName() + ", this is Amigo!";
        String slackSubject = "We just got a new registration!";
        String emailTxt = "<h4>Nice to meet you <strong>" + partyGuest.getName() + "</strong>.<br>" +
                "your request was successfully verified.<br>" +
                "Amigos on the trip will check the contents and if there is a suitable friend,<br>" +
                "They will contact you by email. I hope you have a good trip. thank you.<br><br></h4>";
        String emailContent =
                "<h3>You applied with the following content:</h3><br>" +
                "<h5>Age: " + partyGuest.getAge() + "<br>" +
                "Gender: " + partyGuest.getGender() + "<br>" +
                "Language: " + partyGuest.getLanguage() + "<br>" +
                "Desired date: " + partyGuest.getDate() + "<br>" +
                "Theme: " + partyGuest.getTheme() + "<br>" +
                "Attarction: " + partyGuest.getAttraction() + "</h5>";

        sendMail(toSend, emailTxt + emailContent, emailSubject);
        sendMail(slackMail, emailContent, slackSubject);
        return "";
    }

    /**
     * 구글 사용자 메일 계정 아이디/패스 정보
     */
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username =  "amigotrip82@gmail.com"; // gmail 사용자;
            String password = "machackki"; // 패스워드;
            return new PasswordAuthentication(username, password);
        }
    }
}
