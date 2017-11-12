package amigo.com.web;

import amigo.com.mail.AmigoMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.mail.MessagingException;


/**
 * Created by Naver on 2017. 10. 16..
 */
@Controller
@Slf4j
public class HomeController {
    public AmigoMailSender amigoMailSender = new AmigoMailSender();

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/mail")
    public String mail() {
        try {
            String[] emailList = { "wkddngus5@naver.com" };// 메일 보낼사람 리스트
            String emailFromAddress = "amigotrip82@gmail.com";// 메일 보내는 사람
            String emailMsgTxt = "메일 테스트 내용 "; // 내용
            String emailSubjectTxt = "잘가는지 테스트 중~~~~~~~~~~";// 제목

            // 메일보내기
            amigoMailSender.postMail(emailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
            System.out.println("모든 사용자에게 메일이 성공적으로 보내졌음~~");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Success";
    }

}

