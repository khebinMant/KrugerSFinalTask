package krugers.microservicio.customer.customermicroservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import krugers.microservicio.customer.customermicroservice.entity.Customer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

@RunWith(SpringRunner.class)
public class CustomerControllerTest {
    

    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port = 9090;


    private String createURLWithPort(String uri) {
            return "http://localhost:" + port + uri;
    }


    @Test
    public void test_ListAllCustomers() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/customers/all"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_FindByName() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/customers/name?firstName=José"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_FindByLastName() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/customers/lastname?lastName=Madero"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_FindByIdentificatioNumber() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/customers/identification?identification=1725273823"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

    }
    
    @Test 
    public void test_FindById() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/customers/1"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_getBranchsByCustomerId() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/customers/1/branchs"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_createCustomer() throws Exception  {

        Customer customCustomer = new Customer(100L,"1711111823","Juan","Perez","ju1111anP@gmail.com","RUC","0986261197",1L,null,null, "CREATED", new Date(), new Date());

        HttpEntity<Customer> entity = new HttpEntity<>(customCustomer, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/customers"),
                        HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() != HttpStatus.CREATED){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_deleteCustomer() throws Exception  {

        ResponseEntity<String> responseDelete = restTemplate.exchange(
                        createURLWithPort(
                                        "/customers/100"),
                        HttpMethod.DELETE, null, String.class);

        if (responseDelete.getStatusCode() != HttpStatus.OK){
            assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }
}
