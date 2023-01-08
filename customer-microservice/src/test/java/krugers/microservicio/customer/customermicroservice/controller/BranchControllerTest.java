package krugers.microservicio.customer.customermicroservice.controller;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import krugers.microservicio.customer.customermicroservice.entity.Branch;

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
public class BranchControllerTest {

    private final HttpHeaders headers = new HttpHeaders();
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port = 9090;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


    @Test
    public void test_ListAllBranchs() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/branchs"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_FindById() throws Exception  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/branchs/1"),
                        HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test 
    public void test_createBranch() throws Exception  {

        Branch customBranch = new Branch(100L,"Pichincha","Quito","Av del test","CALLE 1",false,1L,"CREATED", new Date(), new Date());

        HttpEntity<Branch> entity = new HttpEntity<>(customBranch, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                        createURLWithPort("/branchs"),
                        HttpMethod.POST, entity, String.class);
        if (response.getStatusCode() != HttpStatus.CREATED){
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }
    }

    @Test 
    public void test_deleteCustomer() throws Exception  {

        ResponseEntity<String> responseDelete = restTemplate.exchange(
                        createURLWithPort(
                                        "/branch/100"),
                        HttpMethod.DELETE, null, String.class);

        if (responseDelete.getStatusCode() != HttpStatus.OK){
            assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
        else{
            assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }


}
