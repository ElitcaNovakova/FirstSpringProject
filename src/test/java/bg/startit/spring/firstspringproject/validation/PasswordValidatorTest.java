package bg.startit.spring.firstspringproject.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PasswordValidatorTest {

  private PasswordValidator passwordValidator = new PasswordValidator();

  //min 8 символа, да включва голяма и малка буква, цифра, специален символ, да няма интервали

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  // Given, when, then
  @Test
  public void given_null_value_When_validate_Then_returns_false() {
    boolean isValid = this.passwordValidator.isValid(null, null);
    assertFalse(isValid);
  }

  @Test
  public void given_short_value_When_validate_Then_returns_false() {
    assertFalse(this.passwordValidator.isValid("A1_a", null));
    assertFalse(this.passwordValidator.isValid("A1_a123", null));
    assertFalse(this.passwordValidator.isValid("", null));
  }

  @Test
  public void given_valid_password_When_validate_Then_returns_true() {
    assertTrue(this.passwordValidator.isValid("A1_a1234", null));
  }

}