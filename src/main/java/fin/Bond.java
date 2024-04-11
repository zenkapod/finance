package fin;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Table
public class Bond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long finAssetId;
    @NotNull(message = "Дата погашения купона не может быть пустой")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataRepayment;
    @NotNull(message = "Поле не может быть пустым.")
    @Max(value = 5000, message = "Максимум 5000")
    private Integer couponsAmount;
    @NotNull(message = "Поле не может быть пустым.")
    @Max(value = 5000, message = "Максимум 5000")
    private Integer couponsRate;
}