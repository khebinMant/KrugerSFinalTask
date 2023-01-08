package krugers.microservicio.customer.customermicroservice.entity;



import java.io.Serializable;
import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="branchs")
public class Branch implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "La provincia no puede estar vacía no puede ser vacío")
    @Column(name="province", nullable=false)
    private String province;

    @NotEmpty(message = "La ciudad no puede estar vacía no puede ser vacío")
    @Column(name="city", nullable=false)
    private String city;

    @NotEmpty(message = "La dirección no puede estar vacía no puede ser vacío")
    @Column(name="address", nullable=false)
    private String address;

    @NotEmpty(message = "La calle no puede estar vacía no puede ser vacío")
    @Column(name="street", nullable=false)
    private String street;

    @Column(name="is_matriz")
    private Boolean isMatriz;

    @Column(name = "customer_id")
    private Long customerId;

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
