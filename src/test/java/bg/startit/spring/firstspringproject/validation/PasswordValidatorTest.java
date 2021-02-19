package bg.startit.spring.firstspringproject.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class PasswordValidatorTest {

  private ConstraintValidatorContext context;

  private PasswordValidator passwordValidator = new PasswordValidator();

  //min 8 символа, да включва голяма и малка буква, цифра, специален символ, да няма интервали

  @BeforeEach
  void setUp() {
    context = Mockito.mock(ConstraintValidatorContext.class);
    Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
        .thenReturn(Mockito.mock(ConstraintViolationBuilder.class));
  }

  @AfterEach
  void tearDown() {
  }

  // Given, when, then
  @Test
  public void given_null_value_When_validate_Then_returns_false() {
    boolean isValid = this.passwordValidator.isValid(null, context);
    assertFalse(isValid);
  }

  @Test
  public void given_short_value_When_validate_Then_returns_false() {
    assertFalse(this.passwordValidator.isValid("A1_a", context));
    assertFalse(this.passwordValidator.isValid("A1_a123", context));
    assertFalse(this.passwordValidator.isValid("", context));
  }

  @Test
  public void given_valid_password_When_validate_Then_returns_true() {
    assertTrue(this.passwordValidator.isValid("A1_a1234", context));

    // Validate that buildConstraintViolationWithTemplate is called 0 times
    Mockito.verify(context, Mockito.times(0))
        .buildConstraintViolationWithTemplate(Mockito.anyString());
  }

  @Test
  public void given_invalid_password_When_validate_Then_context_messages_set() {
    assertFalse(this.passwordValidator.isValid("AaAaAaAa", context));
    //Missing: special symbol and digit
    Mockito.verify(context, Mockito.times(2))
        .buildConstraintViolationWithTemplate(Mockito.anyString());
  }
}