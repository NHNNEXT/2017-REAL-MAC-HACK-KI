package amigo.com.mail;

import amigo.com.MachackkiApplication;
import amigo.com.domain.PartyGuest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
}
