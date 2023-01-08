package krugers.microservicio.customer.customermicroservice.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import krugers.microservicio.customer.customermicroservice.client.CompanyClient;
import krugers.microservicio.customer.customermicroservice.entity.Branch;
import krugers.microservicio.customer.customermicroservice.entity.Customer;
import krugers.microservicio.customer.customermicroservice.model.Company;
import krugers.microservicio.customer.customermicroservice.service.Branch.IBranchService;
import krugers.microservicio.customer.customermicroservice.service.Customer.ICustomerService;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    ICustomerService icustomerService;

    @Autowired
    IBranchService iBranchService;

    //FeingClient

    @Autowired
    CompanyClient companyClient;

    //Obtener todos los clientes
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find the customers")
	})
    @Operation(summary = "Return a list with all customers", description = "Returns a JSON response with the customers information")
    @Tag(name = "GET all customers", description = "Retrieve all customers")
    @GetMapping(value="/all")
    public ResponseEntity<List<Customer>> listAllCustomers() {
        List<Customer> customers =  new ArrayList<>();
        customers = icustomerService.findCustomerAll().stream()
            .map(customer-> {
                Company company = companyClient.getCompany(customer.getCompanyId()).getBody();
                customer.setCompany(company);
                return customer;
            }).collect(Collectors.toList());
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            else{
                return ResponseEntity.ok(customers);
            }
    }

    //Obtener todos los clientes que coincidan con el nombre enviado por parametro
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find the customers")
	})
    @Operation(summary = "Return a list with all customers with the provided name", description = "Returns a JSON response with the customers information with the provided name")
    @Tag(name = "GET all customers by first name", description = "Retrieve all customers with the provided name")
    @GetMapping(value = "/name")
    public ResponseEntity<List<Customer>> listAllCustomersFilteredByFirstName(@RequestParam(name = "firstName" , required = false) String firstName) {

        List<Customer> customers =  new ArrayList<>();

        if(firstName != null){ 
            customers = icustomerService.findCustomersByFirstName(firstName).stream()
                .filter(customer -> customer.getStatus()!="DELETED")
                .map(customer-> {
                    Company company = companyClient.getCompany(customer.getCompanyId()).getBody();
                    customer.setCompany(company);
                    return customer;
                })
                .collect(Collectors.toList());
            if ( customers == null ) {
                log.error("Customers with firstname: {} not found.", firstName);
                return  ResponseEntity.notFound().build();
            }
        }
        else{
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(customers);

    }

    //Obtener todos los clientes que coincidan con el apellido enviado por parametro
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find the customers")
	})
    @Operation(summary = "Return a list with all customers with the provided lastname", description = "Returns a JSON response with the customers information with the provided lastname")
    @Tag(name = "GET all customers by first lastname", description = "Retrieve all customers with the provided lastname")
    @GetMapping(value = "/lastname")
    public ResponseEntity<List<?>> listAllCustomersFilteredByLastName(@RequestParam(name = "lastName" , required = false) String lastName) {

        List<Customer> customers =  new ArrayList<>();

        if(lastName != null){ 
            customers = icustomerService.findCustomersByLastName(lastName).stream()
                .filter(customer -> customer.getStatus()!="DELETED")
                .map(customer-> {
                    Company company = companyClient.getCompany(customer.getCompanyId()).getBody();
                    customer.setCompany(company);
                    return customer;
                })
                .collect(Collectors.toList());
            if ( customers == null ) {
                log.error("Customers with lastname: {} not found.", lastName);
                return  ResponseEntity.notFound().build();
            }
        }
        else{
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(customers);

    }

    //Obtener todos los clientes que coincidan con el número de identificación enviado por parametro
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find the customer")
	})
    @Operation(summary = "Return a customer with the provided number identification", description = "Returns a JSON response with the customer information with the provided number identification")
    @Tag(name = "GET  customer by number identification", description = "Retrieve a customer with the provided number identification")
    @GetMapping(value = "/identification")
    public ResponseEntity<?> listAllCustomersFilteredByIdentificationNumber(@RequestParam(name = "identification" , required = false) String identification) {

        Customer customer =  new  Customer();

        if(identification != null){ 
            customer = icustomerService.findCustomerByIdentificationNumber(identification);
            if ( customer == null ) {
                log.error("Customers with identification: {} not found.", identification);
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("We cant find a customer with that identification not found. ");
            }
            else{
                return ResponseEntity.ok(customer);
            }

        }
        return  ResponseEntity.notFound().build();
    }

    //Obtener un cliente dado su ID
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find the customer")
	})
    @Operation(summary = "Return a customer with the provided Id", description = "Returns a JSON response with the customer information with the provided Id")
    @Tag(name = "GET  customer by  Id", description = "Retrieve a customer with the provided Id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") long id) {
        log.info("Fetching Customer with id {}", id);
        Customer customer = icustomerService.getCustomer(id);
        if (  null == customer) {
            log.error("Customer with id {} not found.", id);
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("We cant find a customer with that id not found. ");
        }
        return  ResponseEntity.ok(customer);
    }

    //Obtener todas las sucursales de un cliente dado su ID
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find the customer")
	})
    @Operation(summary = "Return all customer branches  with the provided customer Id", description = "Returns a JSON response with the customer branchs information with the provided customer Id")
    @Tag(name = "GET  customer branches by customer Id", description = "Retrieve a customer branches with the provided customer Id")
    @GetMapping(value="/{id}/branchs")
    public ResponseEntity<List<Branch>> getBranchsByCustomerId(@PathVariable(name="id") Long id){
        List <Branch> branchs = iBranchService.findByCustomerId(id);

        if(branchs==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(branchs);
    }

    //Crear nuevo cliente 
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "Multiple validations responses")
	})
    @Operation(summary = "Create a new customer with his branches", description = "Returns a JSON response with the new customer information ")
    @Tag(name = "POST create a customer ", description = "Retrieve information of a new customer")
    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        log.info("Creating Customer : {}", customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

       Customer customerDB = icustomerService.createCustomer (customer);
       if(customerDB == null){
         return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("An user with that CI or Email already exist. ");
       }

        return  ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }
    

    // Actualizar cliente
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "Multiple validations responses")
	})
    @Operation(summary = "Update a customer information by Id", description = "Returns a JSON response with the customer updated information ")
    @Tag(name = "PUT update a customer ", description = "Retrieve information of a updated customer")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        log.info("Updating Customer with id {}", id);

        Customer currentCustomer = icustomerService.getCustomer(id);

        if ( null == currentCustomer ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("We cant find a customer with that id not found. ");
        }
        customer.setId(id);
        currentCustomer=icustomerService.updateCustomer(customer);
        return  ResponseEntity.ok(currentCustomer);
    }


    //Eliminar cliente
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find a customer with that id not found.")
	})
    @Operation(summary = "Delete a customer by Id", description = "Returns a message if everything is ok")
    @Tag(name = "DELETE delete a customer ", description = "Retrieve a message if everything its ok")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Customer with id {}", id);

        Customer customer = icustomerService.getCustomer(id);
        if ( null == customer ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("We cant find a customer with that id not found. ");
        }
        icustomerService.deleteCustomer(customer);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Successfully operation. ");
    }
    
    //Establecer sucursal matriz de un cliente
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "Customer information")
	})
    @Operation(summary = "Update a customer matriz branch by Id", description = "Returns a JSON response with the customer updated information ")
    @Tag(name = "PUT update a customer matriz branch ", description = "Retrieve information of a updated customer")
    @PutMapping(value = "/{customerId}/branch/{branchId}")
    public ResponseEntity<Customer> updateCustomerMatriz(@PathVariable("customerId") long customerId, @PathVariable("branchId") long branchId) {
        log.info("Updating Customer matriz branch with id {}", customerId);

        //Obtengo todas las sucursales que son del cliente
        List <Branch> branchs = iBranchService.findByCustomerId(customerId);
        
        //Obtengo la sucursal que deseo establecer como matriz
        Branch branch = iBranchService.getBranch(branchId);
        
        //Si no encontro entonce retorno vacio
        if ( branchs.isEmpty() ) {
            log.error("Unable to update. Customer with id {} not found.", customerId);
            return  ResponseEntity.notFound().build();
        }
        //Si encontro actualizo una por una si es matriz o no

        for (Branch branch2 : branchs) {
            if(branch2.getId()==branch.getId()){
                iBranchService.updateMatrizBranch(branch2,true);
            }
            else{
                iBranchService.updateMatrizBranch(branch2,false);
            }
        }

        Customer currentCustomer=icustomerService.getCustomer(customerId);
        

        return  ResponseEntity.ok(currentCustomer);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
