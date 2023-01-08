package krugers.microservicio.customer.customermicroservice.controller;

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
import krugers.microservicio.customer.customermicroservice.entity.Branch;
import krugers.microservicio.customer.customermicroservice.service.Branch.IBranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping (value = "/branchs")
public class BranchController {

    @Autowired
    IBranchService iBranchService;

    //Obtener todas las sucursales
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "No content")
	})
    @Operation(summary = "Return a list with all branchs", description = "Returns a JSON response with the branchs information")
    @Tag(name = "GET all branchs ", description = "Retrieve information of all branchs")
    @GetMapping
    public ResponseEntity<List<Branch>> listBranch(){
        java.util.List<Branch> products = new ArrayList<>();

            products = iBranchService.findBranchs().stream()
                .filter(product->product.getStatus()!="DELETED")
                .collect(Collectors.toList());
            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }

        return ResponseEntity.ok(products);
    }

    //Obtener sucursal dado su id
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "No content")
	})
    @Operation(summary = "Return a branch by Id", description = "Returns a JSON response branch information")
    @Tag(name = "GET branch by Id ", description = "Retrieve information of branch by Id")
    @GetMapping(value="/{id}")
    public ResponseEntity<?> getBranch(@PathVariable(name="id") Long id){
        Branch branch = iBranchService.getBranch(id);

        if(branch==null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("We cant find a branch with that id not found. ");
        }
        return ResponseEntity.ok(branch);
    }
    
    //Crear sucursal y adjuntar a cliente
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "No content")
	})
    @Operation(summary = "Create a new branch to a customer", description = "Returns a JSON response with the branch information")
    @Tag(name = "POST branch", description = "Retrieve information of a created branch")
    @PostMapping
    public ResponseEntity<Branch> createBranch(@Valid @RequestBody Branch branch, BindingResult result){
        
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  this.formatMessage(result));
        }
        
        Branch createdBranch = iBranchService.createBranch(branch, branch.getCustomerId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    //Actualizar la información de la sucursal
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find a branch with that id not found.")
	})
    @Operation(summary = "Update branch of a customer", description = "Returns a JSON response with the branch information")
    @Tag(name = "PUT update branch information", description = "Retrieve information of a created branch")
    @PutMapping(value="/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable(name="id") Long id, @RequestBody Branch branch){
        branch.setId(id);
        Branch branchDB = iBranchService.updateBranch(branch);
        if(branchDB == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("We cant find a branch with that id not found. ");
        }
        return ResponseEntity.ok(branchDB);
    }

    //Eliminar una sucursal
    @ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "Successfully operation"),
		@ApiResponse(responseCode = "404", description = "We cant find a branch with that id not found.")
	})
    @Operation(summary = "Delete branch of a customer", description = "Returna OK status")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name="id") Long id){
        Branch branchDeleted =  iBranchService.deleteBranch(id);
        if(branchDeleted == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("We cant find a branch with that id not found. ");
        }
        return ResponseEntity.ok(branchDeleted);
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
