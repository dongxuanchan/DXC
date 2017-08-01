import com.blch.Util.Constants;
import com.blch.Util.Util;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 4/24/17
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class test {
  public static void main(String[] args)  {
    /*System.out.println(Util.enscriptPassMD5("123456"));
    System.out.println(Util.enscriptPassMD5("123456"));
    System.out.println(Util.enscriptBCrypt("123456"));
    System.out.println(Util.enscriptBCrypt("123456"));
    System.out.println(Util.enscriptBCrypt("654321"));*/
   /* System.out.println(Util.formatDate("4/04/2016"));
    System.out.println(4/0);*/
    //System.out.println(new Date(1495558800000L));

    /*String name ="aa sdfa af";
    String email= "nguyenvana@gmail.com";
    String phone = "+(84) 9731 17908";

    System.out.println(name.matches(Constants.NAME_REGEX));
    System.out.println(email.matches(Constants.EMAIL_REGEX));
    System.out.println(phone.matches(Constants.PHONE_REGEX));*/

    sendmailSSL();

  }

  public static void sendmailTLS(){
    final String username = "cxdong";
    final String password = "kc@S83185003";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "cxexvn1.codixfr.private");
    props.put("mail.smtp.port", "25");

    Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      });

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username+"@codix.eu"));
      message.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse("dongxuanchan@gmail.com"));
      message.setSubject("Testing Subject");
      message.setText("Dear Mail Crawler,"
        + "\n\n No spam to my email, please!");

      Transport.send(message);

      System.out.println("Done");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public static void sendmailSSL(){

    final String username = "cxdong";
    final String password = "kc@S83185003";

    Properties props = new Properties();
    props.put("mail.smtp.host", "cxexvn1.codixfr.private");
    props.put("mail.smtp.socketFactory.port", "25");
    props.put("mail.smtp.socketFactory.class",
      "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "25");

    Session session = Session.getDefaultInstance(props,
      new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username,password);
        }
      });

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username+"@codix.eu"));
      message.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse("dongxuanchan@gmail.com"));
      message.setSubject("Testing Subject");
      VelocityEngine velocityEngine = new VelocityEngine();
      Map model = new HashMap();
      model.put("user", "dxcccc");
      message.setText("Hellooooo");

      Transport.send(message);

      System.out.println("Done");

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
