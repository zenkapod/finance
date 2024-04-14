package fin;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class BondTest {
    private Validator validator;
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testValidBond() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(new Date());
        bond.setCouponsAmount(3000);
        bond.setCouponsRate(2000);
        Set<ConstraintViolation<Bond>> violations = validator.validate(bond);
        assertEquals(0, violations.size());
    }
    @Test
    public void testInvalidEmptyDataRepayment() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(null);
        bond.setCouponsAmount(3000);
        bond.setCouponsRate(2000);
        Set<ConstraintViolation<Bond>> violations = validator.validate(bond);
        assertFalse(violations.isEmpty(), "Expected violations for empty dataRepayment");
    }
    @Test
    public void testInvalidMaxCouponsAmount() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(new Date());
        bond.setCouponsAmount(6000);
        bond.setCouponsRate(2000);
        Set<ConstraintViolation<Bond>> violations = validator.validate(bond);
        assertFalse(violations.isEmpty(), "Expected violations for exceeding maximum coupons amount");
    }
    @Test
    public void testInvalidMaxCouponsRate() {
        Bond bond = new Bond();
        bond.setFinAssetId(1L);
        bond.setDataRepayment(new Date());
        bond.setCouponsAmount(3000);
        bond.setCouponsRate(6000);
        Set<ConstraintViolation<Bond>> violations = validator.validate(bond);
        assertFalse(violations.isEmpty(), "Expected violations for exceeding maximum coupons rate");
    }
}
