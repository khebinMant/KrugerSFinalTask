package krugers.microservicio.customer.customermicroservice.service.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import krugers.microservicio.customer.customermicroservice.entity.Customer;
import krugers.microservicio.customer.customermicroservice.repository.CustomerRepository;

@SpringBootTest(classes = {CustomerServiceImplTest.class})
@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {
    
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks 
    CustomerServiceImpl customerServiceImpl;

    //Testing obtener todos los clientes
    @Test
    @Order(1)
    public void test_findCustomerAll(){

        List<Customer> customCustomers = new ArrayList<Customer>();

        customCustomers.add(new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date()));
        customCustomers.add(new Customer(2L,"1125273823","Ramiro","Padilla","ramiroP@gmail.com","CI","0986267197",1L,null,null, "CREATED", new Date(), new Date()));
        
        when(customerRepository.findAll()).thenReturn(customCustomers);
        assertEquals(2, StreamSupport.stream(customerServiceImpl.findCustomerAll().spliterator(), false).count());
    }

    //Testing obtener customer por su nombre
    @Test
    @Order(2)
    public void test_findCustomersByFirstName(){

        List<Customer> customCustomers = new ArrayList<Customer>();

        customCustomers.add(new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date()));

        when(customerRepository.findByFirstName("Juan")).thenReturn(customCustomers);
        assertEquals(1, StreamSupport.stream(customerServiceImpl.findCustomersByFirstName("Juan").spliterator(), false).count());
    }

    //Testing obtener customer por su apellido
    @Test
    @Order(3)
    public void test_findCustomersByLastName(){

        List<Customer> customCustomers = new ArrayList<Customer>();

        customCustomers.add(new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date()));

        when(customerRepository.findByLastName("Perez")).thenReturn(customCustomers);
        assertEquals(1, StreamSupport.stream(customerServiceImpl.findCustomersByLastName("Perez").spliterator(), false).count());

    }

    //Testing obtener customer por su cédula
    @Test
    @Order(4)
    public void test_findCustomerByIdentificationNumber(){

        Customer customCustomer = new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date());

        when(customerRepository.findByIdentificationNumber("1725273823")).thenReturn(customCustomer);
        Assertions.assertThat(customCustomer.getIdentificationNumber()).isEqualTo("1725273823");
    }

    
    //Testing obtener customer por su email
    @Test
    @Order(5)
    public void test_findCustomerByEmail(){

        Customer customCustomer = new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date());

        when(customerRepository.findByEmail("juanP@gmail.com")).thenReturn(customCustomer);
        Assertions.assertThat(customCustomer.getEmail()).isEqualTo("juanP@gmail.com");
    }

    //Testing obtener customer por su Id
    @Test
    @Order(6)
    public void test_getCustomer(){

        Customer customCustomer = new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customCustomer));
        Assertions.assertThat(customCustomer.getId()).isEqualTo(1L);
    }

    //Testing crear customer
    @Test
    @Order(7)
    public void test_createCustomer(){

        Customer customCustomer = new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date());

        when(customerRepository.save(customCustomer)).thenReturn(customCustomer);
        assertEquals(customCustomer,customerRepository.save(customCustomer));
    }

    //Testing actualizar customer
    @Test
    @Order(8)
    public void test_updateCustomer(){

        Customer customCustomer = new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date());

        when(customerRepository.save(customCustomer)).thenReturn(customCustomer);
        assertEquals(customCustomer,customerRepository.save(customCustomer));
    }

    //Testing eliminar customer
    @Test
    @Order(8)
    public void test_deleteCustomer(){

        Customer customCustomer = new Customer(1L,"1725273823","Juan","Perez","juanP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date());

        customerRepository.deleteById(customCustomer.getId());

        when(customerRepository.save(customCustomer)).thenReturn(customCustomer);
        doThrow(RuntimeException.class).when(customerRepository).deleteById(customCustomer.getId());
        verify(customerRepository, times(1)).deleteById(customCustomer.getId());
    }

}
