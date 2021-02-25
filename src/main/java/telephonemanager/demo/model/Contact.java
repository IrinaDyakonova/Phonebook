package telephonemanager.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Name can not be empty.")
    @Size(min = 3, max = 20, message = "Name must be between 3-20 characters.")
    private String name;
    private String numberPhone;
    @Email(regexp = "^[a-zA-Z0-9+.-]+@[a-zA-Z0-9+.-]+$")
    private String email;
    @ManyToOne
    private User user;
}
