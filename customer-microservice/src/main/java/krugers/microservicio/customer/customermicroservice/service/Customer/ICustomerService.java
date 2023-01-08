package krugers.microservicio.customer.customermicroservice.service.Customer;

import java.util.List;


import krugers.microservicio.customer.customermicroservice.entity.Customer;


public interface ICustomerService {

    public List<Customer> findCustomerAll();
    public List<Customer> findCustomersByFirstName(String firstName);
    public List<Customer> findCustomersByLastName(String lastname);
    public Customer findCustomerByIdentificationNumber(String identificationNumber);
    public Customer findCustomerByEmail(String email);


    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);//Me sirve para actualizar también la matriz del customer
    public void deleteCustomer(Customer customer);
    public Customer getCustomer(Long id);

}
