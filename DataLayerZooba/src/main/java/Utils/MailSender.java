/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.PasswordAuthentication;
//
/**
 *
 * @author Ehab
 */
public class MailSender {

    public void sendRestPasswordMail(String email, String password) {

//        String username = "z00000uba@gmail.com";
//        String pass = "A1B23456";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.user", username);
//        props.put("mail.smtp.password", password);
//        
//        Session session = Session.getInstance(props, null);
//
//        try {
//            Message msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress("z00000uba@gmail.com"));
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//            msg.setSubject("Your Example.com account has been activated");
//            String mailBody = "Your Password is : " + password;
//            msg.setText(mailBody);
//            Transport transport = session.getTransport("smtp");
//            transport.connect("smtp.gmail.com", username, password);
//            transport.sendMessage(msg, msg.getAllRecipients());
//            transport.close();
//        } catch (AddressException e) {
//        } catch (MessagingException e) {
//        }
    }
}
