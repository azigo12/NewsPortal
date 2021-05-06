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
public class NewsTest {
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
        User u = new User();
        u.setId(UUID.randomUUID());
        u.setFirstName("Amila");
        u.setLastName("Zigo");
        u.setEmail("someone@gmail.com");
        u.setPassword("Password1");
        u.setGender("FEMALE");
        u.setRole("ADMIN");

        n.setId(UUID.randomUUID());
        n.setTitle("Covid-19");
        n.setDescription("A third wave of the Covid-19 pandemic is \"inevitable\" in India.");
        n.setOwner(u);
    }

    @Test
    public void testNoViolations() {
        List<ConstraintViolation<News>> violations = new ArrayList<>(validator.validate(n));
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNoTitle() {
        n.setTitle("");

        List<ConstraintViolation<News>> violations = new ArrayList<>(validator.validate(n));
        assertEquals(1, violations.size());
        assertEquals("Title can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testLongTitle() {
        n.setTitle("a".repeat(51));

        List<ConstraintViolation<News>> violations = new ArrayList<>(validator.validate(n));
        assertEquals(1, violations.size());
        assertEquals("Title can contain at most 50 characters", violations.get(0).getMessage());
    }

    @Test
    public void testNoDescription() {
        n.setDescription("");

        List<ConstraintViolation<News>> violations = new ArrayList<>(validator.validate(n));
        assertEquals(1, violations.size());
        assertEquals("Description can't be blank", violations.get(0).getMessage());
    }

    @Test
    public void testNoOwner() {
        n.setOwner(null);

        List<ConstraintViolation<News>> violations = new ArrayList<>(validator.validate(n));
        assertEquals(1, violations.size());
        assertEquals("User can't be null", violations.get(0).getMessage());
    }

    @Test
    public void testMultipleViolations() {
        n.setId(null);
        n.setTitle("");
        n.setDescription("");
        n.setOwner(null);

        List<String> violations = validator
                .validate(n)
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        assertEquals(4, violations.size());
        assertTrue(violations.contains("Title can't be blank"));
        assertTrue(violations.contains("Description can't be blank"));
        assertTrue(violations.contains("User can't be null"));
        assertTrue(violations.contains("User can't be null"));
    }
    

}
