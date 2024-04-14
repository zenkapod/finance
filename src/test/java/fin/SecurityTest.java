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
public class SecurityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidSecurity() {
        Security security = new Security();
        security.setFinAssetId(1L);
        security.setDateAccommodation(new Date());
        security.setDateReport(new Date());

        Set<ConstraintViolation<Security>> violations = validator.validate(security);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmptyDateAccommodation() {
        Security security = new Security();
        security.setFinAssetId(1L);
        security.setDateAccommodation(null);
        security.setDateReport(new Date());

        Set<ConstraintViolation<Security>> violations = validator.validate(security);
        assertFalse(violations.isEmpty(), "Expected violations for empty dateAccommodation");
    }

    @Test
    public void testInvalidEmptyDateReport() {
        Security security = new Security();
        security.setFinAssetId(1L);
        security.setDateAccommodation(new Date());
        security.setDateReport(null);

        Set<ConstraintViolation<Security>> violations = validator.validate(security);
        assertFalse(violations.isEmpty(), "Expected violations for empty dateReport");
    }
}