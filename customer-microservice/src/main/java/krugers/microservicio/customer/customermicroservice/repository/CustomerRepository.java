package krugers.microservicio.customer.customermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import krugers.microservicio.customer.customermicroservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    public Customer findByIdentificationNumber(String identificationNumber);
    public Customer findByEmail(String email);
    public List<Customer> findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
}
