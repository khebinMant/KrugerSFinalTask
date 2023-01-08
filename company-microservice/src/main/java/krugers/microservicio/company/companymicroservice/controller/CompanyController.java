package krugers.microservicio.company.companymicroservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import krugers.microservicio.company.companymicroservice.entity.Company;
import krugers.microservicio.company.companymicroservice.service.ICompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping (value = "/companies")
public class CompanyController {

    @Autowired
    ICompanyService iCompanyService;

    //Obtener todas las compañias
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "No content")
	})
    @Operation(summary = "Return a list with all companies", description = "Returns a JSON response with the companies information")
    @Tag(name = "GET all companies ", description = "Retrieve information of all companies")
    @GetMapping
    public ResponseEntity<List<Company>> litCompanies(){
        List<Company> companies = new ArrayList<>();

        companies = iCompanyService.listAllCompanies().stream()
            .filter(product->product.getStatus()!="DELETED")
            .collect(Collectors.toList());
        if(companies.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(companies);
    }

    //Obtener compañia dado su id
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "No content")
	})
    @Operation(summary = "Return a company by Id", description = "Returns a JSON response with the company information")
    @Tag(name = "GET company by Id ", description = "Retrieve information of company by Id")
    @GetMapping(value="/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable(name="id") Long id){
        Company company = iCompanyService.getCompany(id);

        if(company==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(company);
    }
    
    //Crear compañia
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "No content")
	})
    @Operation(summary = "Create a new company", description = "Returns a JSON response with the company information")
    @Tag(name = "POST company", description = "Retrieve information of a created company")
    @PostMapping
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company, BindingResult result){
        
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  this.formatMessage(result));
        }
        
        Company createdCompany = iCompanyService.createCompany(company);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    //Actualizar la información de la compañia
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find a company with that id not found.")
	})
    @Operation(summary = "Update company", description = "Returns a JSON response with the company information")
    @Tag(name = "PUT update company information", description = "Retrieve information of a created company")
    @PutMapping(value="/{id}")
    public ResponseEntity<Company> updateBranch(@PathVariable(name="id") Long id, @RequestBody Company company){
        company.setId(id);
        Company companyDB = iCompanyService.updateCompany(company);
        if(companyDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(companyDB);
    }

    //Eliminar una compañia
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find a company with that id not found.")
	})
    @Operation(summary = "Delete company", description = "Return OK status")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Company> deleteProduct(@PathVariable(name="id") Long id){
        Company companyDeleted =  iCompanyService.deleteCompany(id);
        if(companyDeleted == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(companyDeleted);
    }


    private String formatMessage(BindingResult bindingResult){
        List<Map<String,String>> errors =  bindingResult.getFieldErrors().stream()
            .map(err -> {
                Map<String,String> error = new HashMap<>();
                error.put(err.getField(), err.getDefaultMessage());
                return error;
            }).collect(Collectors.toList());
        
        //Aqui guardo utilizando la clase que creamos para gestionar el error
        ErrorMessage errorMessage = ErrorMessage.builder()
            .code("01")
            .messages(errors).build();

        ObjectMapper mapper = new ObjectMapper();

        String jsoString="";
        try{
            jsoString = mapper.writeValueAsString(errorMessage);
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsoString;
    } 
}
