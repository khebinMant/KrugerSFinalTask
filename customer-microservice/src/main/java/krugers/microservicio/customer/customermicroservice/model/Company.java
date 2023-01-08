package krugers.microservicio.customer.customermicroservice.model;

import java.util.Date;

import lombok.Data;

@Data
public class Company {
    
    private Long id;

    private String companyName;

    private String status;

    private Date createAt;

    private Date updateAt;
}
