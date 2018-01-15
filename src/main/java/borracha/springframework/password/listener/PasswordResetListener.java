package borracha.springframework.password.listener;


import borracha.springframework.password.OnPasswordResetCompleteEvent;
import borracha.springframework.service.UserService;
import borracha.springframework.domain.User;
import borracha.springframework.password.OnPasswordResetCompleteEvent;
import borracha.springframework.registration.OnRegistrationCompleteEvent;
import borracha.springframework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetCompleteEvent> {
  @Autowired
  private UserService service;

  @Autowired
  private MessageSource messages;

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private Environment env;

  // API
  @Async
  @Override
  public void onApplicationEvent(final OnPasswordResetCompleteEvent event) {
    this.confirmPasswordReset(event);
  }

  private void confirmPasswordReset(final OnPasswordResetCompleteEvent event) {
    final User user = event.getUser();
    final String token = UUID.randomUUID().toString();
    service.createPasswordResetTokenForUser(user, token);

    final SimpleMailMessage email = constructEmailMessage(event, user, token);
    mailSender.send(email);
  }

  private final SimpleMailMessage constructEmailMessage(final OnPasswordResetCompleteEvent event, final User user, final String token) {
    final String recipientAddress = user.getEmail();
    final String subject = "Reset Password";
    final String confirmationUrl = event.getAppUrl() + "/user/changePassword?id=" + user.getId() + "&token=" + token;
    final String message = messages.getMessage("message.resetPassword", null, event.getLocale());
    final SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(recipientAddress);
    email.setSubject(subject);
    email.setText(message + " \r\n" + confirmationUrl);
    email.setFrom(env.getProperty("support.email"));
    return email;
  }





}
