package fin;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class TikerTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTiker() {
        Tiker tiker = new Tiker();
        tiker.setNameTiker("AAPL");
        tiker.setDealPlace("NYSE");

        Set<ConstraintViolation<Tiker>> violations = validator.validate(tiker);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidNameTikerPattern() {
        Tiker tiker = new Tiker();
        tiker.setNameTiker("AAPL@");
        tiker.setDealPlace("NYSE");

        Set<ConstraintViolation<Tiker>> violations = validator.validate(tiker);
        assertFalse(violations.isEmpty(), "Expected violations for invalid pattern in nameTiker");
    }
}


