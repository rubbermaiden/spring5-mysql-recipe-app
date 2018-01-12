package guru.springframework.validation;

import guru.springframework.commands.UserCommand;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(final PasswordMatches constraintAnnotation) {
    //
  }

  @Override
  public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
    final UserCommand user = (UserCommand) obj;
    return user.getPassword().equals(user.getMatchingPassword());
  }

}

