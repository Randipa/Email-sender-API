package com.example.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("api/mail")
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;


    @PostMapping("/send")
    public String sendEmail(@RequestBody MailDetailsDTO mailDetailsDTO){

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(mailDetailsDTO.getSubject());
            message.setTo(mailDetailsDTO.getTo());
            message.setFrom(mailDetailsDTO.getFrom());
            message.setText(mailDetailsDTO.getMessage());

            javaMailSender.send(message);

            return "Success";

        }catch (Exception e){
            return e.getMessage();
        }



    }

    @PostMapping("/att/send")
    public ResponseEntity<String> sendAttachment(@RequestBody AttchmentDTO attchmentDTO) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(attchmentDTO.getFrom());
            mimeMessageHelper.setTo(attchmentDTO.getTo());
            mimeMessageHelper.setText(attchmentDTO.getMessage());
            mimeMessageHelper.setSubject(attchmentDTO.getSubject());


            FileSystemResource fileSystemResource = new FileSystemResource(new File(attchmentDTO.getAttchement()));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            javaMailSender.send(mimeMessage);

            return ResponseEntity.ok("Success");

        } catch (MessagingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
