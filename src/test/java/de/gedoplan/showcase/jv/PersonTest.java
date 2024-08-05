package de.gedoplan.showcase.jv;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonTest {
  private ValidatorFactory validatorFactory;
  private Validator validator;

  @BeforeAll
  void bootstrap() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
  }

  @BeforeEach
  void init() {
    validator = validatorFactory.getValidator();
  }

  @Test
  void testValidPerson() {
    var person = new Person("Mini", LocalDate.now(), new Address("Downtown", "Somewhere", "12345"));
    var result = validator.validate(person);
    assertThat(result).isEmpty();
  }

  @Test
  void testInvalidBirthday() {
    var person = new Person("Maxi", LocalDate.now().plusDays(1), new Address("Downtown", "Somewhere", "12345"));
    var result = validator.validate(person);
    assertThat(result)
        .isNotEmpty()
        .hasAtLeastOneElementOfType(ConstraintViolation.class);
  }

  @Test
  void testInvalidName() {
    var person = new Person("J", LocalDate.now(), new Address("Downtown", "Somewhere", "12345"));
    var result = validator.validate(person);
    assertThat(result)
        .hasAtLeastOneElementOfType(ConstraintViolation.class)
        .extracting(personConstraintViolation -> personConstraintViolation.getPropertyPath().toString())
        .contains("name");
  }

  @Test
  void testInvalidAddress() {
    var person = new Person("Mini", LocalDate.now(), new Address("Downtown", "Somewhere", "1234"));
    var result = validator.validate(person);
    assertThat(result)
        .hasAtLeastOneElementOfType(ConstraintViolation.class)
        .extracting(personConstraintViolation -> personConstraintViolation.getPropertyPath().toString())
        .contains("address.postcode");
  }
}
