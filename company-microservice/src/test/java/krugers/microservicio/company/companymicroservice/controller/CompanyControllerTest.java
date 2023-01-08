package krugers.microservicio.company.companymicroservice.controller;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import krugers.microservicio.company.companymicroservice.entity.Company;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

@RunWith(SpringRunner.class)
public class CompanyControllerTest {

    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port = 9091;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    @Order(1)
    public void test_ListAllCompanies() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/companies"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    
    @Test 
    @Order(2)
    public void test_createBranch() throws Exception  {

        Company customCompany = new Company(100L,"PizzaHut", "CREATED", new Date(), new Date());

        HttpEntity<Company> entity = new HttpEntity<>(customCompany, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/companies"),
                        HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() != HttpStatus.CREATED){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }
    }

    @Test 
    @Order(3)
    public void test_FindById() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/companies/100"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }


    @Test 
    @Order(4)
    public void test_deleteCustomer() throws Exception  {

        ResponseEntity<String> responseDelete = restTemplate.exchange(
                        createURLWithPort(
                                        "/companies/100"),
                        HttpMethod.DELETE, null, String.class);

        if (responseDelete.getStatusCode() != HttpStatus.OK){
            assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }
}
