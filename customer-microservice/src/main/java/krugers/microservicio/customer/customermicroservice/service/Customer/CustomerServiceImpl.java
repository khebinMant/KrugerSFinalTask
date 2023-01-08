package krugers.microservicio.customer.customermicroservice.service.Customer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import krugers.microservicio.customer.customermicroservice.client.CompanyClient;
import krugers.microservicio.customer.customermicroservice.entity.Customer;
import krugers.microservicio.customer.customermicroservice.model.Company;
import krugers.microservicio.customer.customermicroservice.repository.CustomerRepository;
import krugers.microservicio.customer.customermicroservice.service.Branch.BranchServiceImpl;


@Service
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BranchServiceImpl branchServiceImpl;

    //FeignClient
    @Autowired
    CompanyClient companyClient;


    @Override
    public List<Customer> findCustomerAll() {
        List <Customer> customersDB = customerRepository.findAll();
        return customersDB;
    }

    @Override
    public List<Customer> findCustomersByFirstName(String firstName) {
        return customerRepository.findByFirstName(firstName);
    }

    @Override
    public List<Customer> findCustomersByLastName(String lastname) {
        return customerRepository.findByLastName(lastname);
    }
    
    @Override
    public Customer findCustomerByIdentificationNumber(String identificationNumber) {
        return customerRepository.findByIdentificationNumber(identificationNumber);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer getCustomer(Long id) {
        Customer customerDB = customerRepository.findById(id).orElse(null);
        if(customerDB == null){
            return null;
        }
        else{
            Company company = companyClient.getCompany(customerDB.getCompanyId()).getBody();
            customerDB.setCompany(company); 
            return customerDB;
        }
    }

    @Override
    public Customer createCustomer(Customer customer) {

        Customer customerDB = customerRepository.findByIdentificationNumber(customer.getIdentificationNumber());
        Customer customerDB2 = customerRepository.findByEmail(customer.getEmail());

        if (customerDB != null || customerDB2!=null){
            return  null;
        }
        customer.setStatus("CREATED");
        customer.setCreateAt(new Date());
        customer.setUpdateAt(new Date());

        Long customerId;
        customerDB = customerRepository.save ( customer );
        customerId = customerDB.getId();

        customerDB.getBranchs().forEach(customerBranch->{
            branchServiceImpl.createBranch(customerBranch, customerId);
        });

        return customerDB;
    }
    

    @Override
    public Customer updateCustomer(Customer customer) {

        Customer customerDB = getCustomer(customer.getId());
        if (customerDB == null){
            return  null;
        }

        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setIdentificationType(customer.getIdentificationType());
        customerDB.setCellPhone(customer.getCellPhone());

        return  customerRepository.save(customerDB);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        Customer customerDB = getCustomer(customer.getId());
        if (customerDB !=null){
            customerRepository.delete(customer);
        }        
    }


}
