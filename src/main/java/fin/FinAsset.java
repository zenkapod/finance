package fin;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@Table
public class FinAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tiker_id")
    private Long tikerId;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-я ]+$", message = "Может содержать только буквы.")
    private String registration;
    @NotNull(message = "Дата регистрации ценной бумаги не может быть пустой")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Формат даты "год-месяц-день"
    private Date dataRegistration;
    @NotBlank(message = "Эмитент не может быть пустым")
    private String emitent;
    @NotBlank(message = "Форма выпуска не может быть пустой")
    private String formIssue;
    @NotNull(message = "Номинал не может быть пустым")
    private Double principal;
    @NotNull(message = "Количество ЦБ в выпуске не может быть пустым")
    private Integer amount;

}
