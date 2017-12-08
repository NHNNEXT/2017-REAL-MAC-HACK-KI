package com.amigotrip.mail;

import com.amigotrip.MachackkiApplication;
import com.amigotrip.domain.PartyGuest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by NEXT on 2017. 10. 26..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MachackkiApplication.class}, loader = AnnotationConfigContextLoader.class)
public class AmigoMailSenderTest {

    @Autowired
    AmigoMailSender amigoMailSender;

    @Test
    public void testAsyncMailSender() throws InterruptedException {
        PartyGuest testGuest = new PartyGuest();

        System.out.println("Start - invoking an asynchronous method." + Thread.currentThread().getName());
        amigoMailSender.sendPartyGuestFormMail(testGuest);
        System.out.println("End - invoking an asynchronous method." + Thread.currentThread().getName());
        Thread.sleep(250);
    }

    /**
     * Created by Naver on 2017. 10. 24..
     */
    public static class MailTest {

        @Test
        public void testMailSend() {
            try {
                String[] emailList = { "wkddngus5@naver.com" };// 메일 보낼사람 리스트
                String emailFromAddress = "amigotrip82@gmail.com";// 메일 보내는 사람
                String emailMsgTxt = ""; // 내용
                String emailSubjectTxt = "잘가는지 테스트 중~~~~~~~~~~";// 제목

                // 메일보내기
                postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
                System.out.println("모든 사용자에게 메일이 성공적으로 보내졌음~~");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        private void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
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

        /**
         * 구글 사용자 메일 계정 아이디/패스 정보
         */
        private class SMTPAuthenticator extends Authenticator {
            public PasswordAuthentication getPasswordAuthentication() {
                String username =  "amigotrip82@gmail.com"; // gmail 사용자;
                String password = "machackki"; // 패스워드;
                return new PasswordAuthentication(username, password);
            }
        }

        @Test
        public void readFile() {
            try {
                BufferedReader in = new BufferedReader(new FileReader("src/main/java/amigo/com/mail/user_info"));
                String email = in.readLine();
                String password = in.readLine();
                System.out.println(email + "\n" + password);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
