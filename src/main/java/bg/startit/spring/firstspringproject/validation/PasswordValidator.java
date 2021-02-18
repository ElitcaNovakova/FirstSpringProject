package bg.startit.spring.firstspringproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  public static final String SPECIAL_CHARACTERS = "!@#$%^&*()-+.,_";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    //min 8 символа, да включва голяма и малка буква, цифра, специален символ, да няма интервали
    if (value == null || value.length() < 8) {
      return false;
    }
    boolean upper = false;
    boolean lower = false;
    boolean digit = false;
    boolean special = false;
    boolean whiteSpace = false;

    for (char ch : value.toCharArray()) {
      if (Character.isUpperCase(ch)) {
        upper = true;
      }
      if (Character.isLowerCase(ch)) {
        lower = true;
      }
      if (Character.isDigit(ch)) {
        digit = true;
      }
      if (SPECIAL_CHARACTERS.indexOf(ch) >= 0) {
        special = true;
      }
      if (Character.isWhitespace(ch)) {
        whiteSpace = true;
      }

    }
    return upper && lower && digit && special && (!whiteSpace);
  }
}
