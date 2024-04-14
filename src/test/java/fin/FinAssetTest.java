package fin;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class FinAssetTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidFinAsset() {
        FinAsset finAsset = new FinAsset();
        finAsset.setTikerId(1L);
        finAsset.setRegistration("ABC");
        finAsset.setDataRegistration(new Date());
        finAsset.setEmitent("Emitent");
        finAsset.setFormIssue("Form Issue");
        finAsset.setPrincipal(1000.0);
        finAsset.setAmount(100);

        Set<ConstraintViolation<FinAsset>> violations = validator.validate(finAsset);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmptyRegistration() {
        FinAsset finAsset = new FinAsset();
        finAsset.setTikerId(1L);
        finAsset.setRegistration(""); // Invalid: empty registration
        finAsset.setDataRegistration(new Date());
        finAsset.setEmitent("Emitent");
        finAsset.setFormIssue("Form Issue");
        finAsset.setPrincipal(1000.0);
        finAsset.setAmount(100);

        Set<ConstraintViolation<FinAsset>> violations = validator.validate(finAsset);
        assertFalse(violations.isEmpty(), "Expected violations for empty registration");
    }

    @Test
    public void testInvalidEmptyDataRegistration() {
        FinAsset finAsset = new FinAsset();
        finAsset.setTikerId(1L);
        finAsset.setRegistration("ABC");
        finAsset.setDataRegistration(null); // Invalid: empty dataRegistration
        finAsset.setEmitent("Emitent");
        finAsset.setFormIssue("Form Issue");
        finAsset.setPrincipal(1000.0);
        finAsset.setAmount(100);

        Set<ConstraintViolation<FinAsset>> violations = validator.validate(finAsset);
        assertFalse(violations.isEmpty(), "Expected violations for empty dataRegistration");
    }
}
