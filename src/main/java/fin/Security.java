package fin;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Table
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long finAssetId;
    @NotNull(message = "Дата размещения не может быть пустой")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAccommodation;

    @NotNull(message = "Дата регистрации отчета о размещении не может быть пустой")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateReport;
}