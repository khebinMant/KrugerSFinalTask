package krugers.microservicio.customer.customermicroservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import krugers.microservicio.customer.customermicroservice.model.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="customers")
public class Customer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Esta el la cédula del cliente
    @NotEmpty(message = "El número de identificación no puede ser vacío")
    @Size( min = 10 , max = 10, message = "El tamaño del número de identicación es 10")
    @Column(name = "identification_number" , unique = true ,length = 10, nullable = false)
    private String identificationNumber;

    @NotEmpty(message = "El nombre no puede ser vacío")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "El apellido no puede ser vacío")
    @Column(name="last_name", nullable=false)
    private String lastName;

    @NotEmpty(message = "El correo no puede estar vacío")
    @Email(message = "No es un dirección de correo valida")
    @Column(unique=true, nullable=false)
    private String email;

    @Column(name = "identification_type")
    private String identificationType;

    @Column(name = "cell_phone")
    private String cellPhone;

    //Compañia a la que representa el cliente
    @Column(name = "company_id", nullable = false)
    private Long companyId;
    
    @Transient
    private Company company;

    @Valid
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Branch> branchs;

    //Para el eliminado lógico
    private String status;

    //Historial de cambios por fechas
    //Fecha de creación
    @Temporal(TemporalType.DATE)
    @Column(name="create_at")
    private Date createAt;

    //Fecha de modificación
    @Temporal(TemporalType.DATE)
    @Column(name="update_at")
    private Date updateAt;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }
}
