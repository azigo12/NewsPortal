package model;

import ba.newsportal.model.News;
import ba.newsportal.model.User;
import ba.newsportal.model.enums.Gender;
import ba.newsportal.model.enums.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
public class UserTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static News n = new News();
    private static User u = new User();

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @BeforeEach
    public void createNews() {
        u.setId(UUID.randomUUID());
        u.setFirstName("Amila");
        u.setLastName("Zigo");
        u.setEmail("someone@gmail.com");
        u.setPassword("Password1");
        u.setGender("FEMALE");
        u.setRole("ADMIN");
    }

    @Test
    public void testNoViolations() {
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNoFirstName() {
        u.setFirstName("");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("First name can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongFirstName() {
        u.setFirstName("u".repeat(16));

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("First name can't be longer than fifteen characters", violations.get(0).getMessage());
    }

    @Test
    public void testNoLastName() {
        u.setLastName("");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Last name can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongLastName() {
        u.setLastName("u".repeat(16));

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Last name can't be longer than fifteen characters", violations.get(0).getMessage());
    }

    @Test
    public void testNoEmail() {
        u.setEmail("");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Email can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidEmailFormat() {
        u.setEmail("invalidformat");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Invalid email format", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidEmailFormat2() {
        u.setEmail("@");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Invalid email format", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidEmailFormat3() {
        u.setEmail("@invalid");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Invalid email format", violations.get(0).getMessage());
    }

    @Test
    public void testInvalidEmailFormat4() {
        u.setEmail("             ");

        List<String> violations = validator
                .validate(u)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Email can't be blank"));
        assertTrue(violations.contains("Invalid email format"));
    }

    @Test
    public void testBlankPassword() {
        u.setPassword("");

        List<String> violations = validator
                .validate(u)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(3, violations.size());
        assertTrue(violations.contains("Password can't be blank"));
        assertTrue(violations.contains("Password must contain at least eight characters"));
        assertTrue(violations.contains("Password must contain at least one lowercase, one uppercase and one digit"));
    }

    @Test
    public void testShortPassword() {
        u.setPassword("pass");

        List<String> violations = validator
                .validate(u)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(2, violations.size());
        assertTrue(violations.contains("Password must contain at least eight characters"));
        assertTrue(violations.contains("Password must contain at least one lowercase, one uppercase and one digit"));
    }

    @Test
    public void testNoDigitPassword() {
        u.setPassword("Password");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least one lowercase, one uppercase and one digit", violations.get(0).getMessage());
    }

    @Test
    public void testNoLowercasePassword() {
        u.setPassword("PASSWORD1");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least one lowercase, one uppercase and one digit", violations.get(0).getMessage());
    }

    @Test
    public void testNoUppercasePassword() {
        u.setPassword("password1");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least one lowercase, one uppercase and one digit", violations.get(0).getMessage());
    }

    @Test
    public void testGenderNoViolations() {
        u.setGender("MALE");
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNoGender() {
        u.setGender(null);

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Gender can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testBadGender() {
        u.setGender("bad");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Must be any of enum Gender", violations.get(0).getMessage());
    }

    @Test
    public void testBlankGender() {
        u.setGender("");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Must be any of enum Gender", violations.get(0).getMessage());
    }

    @Test
    public void testRoleNoViolations() {
        u.setRole("REGULAR");
        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNoRole() {
        u.setRole(null);

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Role can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testBadRole() {
        u.setRole("bad");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Must be any of enum Role", violations.get(0).getMessage());
    }

    @Test
    public void testBlankRole() {
        u.setRole("");

        List<ConstraintViolation<User>> violations = new ArrayList<>(validator.validate(u));
        assertEquals(1, violations.size());
        assertEquals("Must be any of enum Role", violations.get(0).getMessage());
    }


}
