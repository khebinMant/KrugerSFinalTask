package krugers.microservicio.customer.customermicroservice.service.Branch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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

import krugers.microservicio.customer.customermicroservice.entity.Branch;
import krugers.microservicio.customer.customermicroservice.repository.BranchRepository;
import krugers.microservicio.customer.customermicroservice.service.Customer.CustomerServiceImplTest;

@SpringBootTest(classes = {CustomerServiceImplTest.class})
@RunWith(SpringRunner.class)
public class BranchServiceImplTest {
    

    @Mock
    BranchRepository branchRepository;

    @InjectMocks
    BranchServiceImpl branchServiceImpl;

    //Testing obtener todos las sucursales
    @Test
    @Order(1)
    public void test_findBranchs(){
        
        List<Branch> customBranches =  new ArrayList<Branch>();
        customBranches.add(new Branch(1L,"Pichincha","Quito","Av del test","CALLE 1",false,1L,"CREATED", new Date(), new Date()));
        customBranches.add(new Branch(2L,"Guayas","Guayaquil","Av del test2","CALLE 2",false,1L,"CREATED", new Date(), new Date()));
        
        when(branchRepository.findAll()).thenReturn(customBranches);
        assertEquals(2, StreamSupport.stream(branchServiceImpl.findBranchs().spliterator(), false).count());
    }

    //Testing obtener sucursal por su Id
    @Test
    @Order(2)
    public void test_getBranch(){

        Branch customBranch = new Branch(1L,"Pichincha","Quito","Av del test","CALLE 1",false,1L,"CREATED", new Date(), new Date());

        when(branchRepository.findById(1L)).thenReturn(Optional.of(customBranch));
        Assertions.assertThat(customBranch.getId()).isEqualTo(1L);
    }

    //Testing obtener sucursales por el Id del cliente
    @Test
    @Order(3)
    public void test_findByCustomerId(){

        List<Branch> customBranches =  new ArrayList<Branch>();
        customBranches.add(new Branch(1L,"Pichincha","Quito","Av del test","CALLE 1",false,1L,"CREATED", new Date(), new Date()));
        customBranches.add(new Branch(2L,"Guayas","Guayaquil","Av del test2","CALLE 2",false,1L,"CREATED", new Date(), new Date()));
        
        when(branchRepository.findByCustomerId(1L)).thenReturn(customBranches);
        assertEquals(2, StreamSupport.stream(branchServiceImpl.findByCustomerId(1L).spliterator(), false).count());
    }

    //Testing crear branch
    @Test
    @Order(4)
    public void test_createBranch(){

        Branch customBranch = new Branch(1L,"Pichincha","Quito","Av del test","CALLE 1",false,1L,"CREATED", new Date(), new Date());

        when(branchRepository.save(customBranch)).thenReturn(customBranch);
        assertEquals(customBranch,branchRepository.save(customBranch));
    }

    //Testing actualizar branch
    @Test
    @Order(5)
    public void test_updateBranch(){

        Branch customBranch = new Branch(1L,"Pichincha","Quito","Av del test","CALLE 1",false,1L,"CREATED", new Date(), new Date());

        when(branchRepository.save(customBranch)).thenReturn(customBranch);
        assertEquals(customBranch,branchRepository.save(customBranch));
    }

    //Testing eliminar branch
    @Test
    @Order(7)
    public void test_deleteCustomer(){

        Branch customBranch = new Branch(1L,"Pichincha","Quito","Av del test","CALLE 1",false,1L,"CREATED", new Date(), new Date());

        branchRepository.deleteById(customBranch.getId());

        when(branchRepository.save(customBranch)).thenReturn(customBranch);
        doThrow(RuntimeException.class).when(branchRepository).deleteById(customBranch.getId());
        verify(branchRepository, times(1)).deleteById(customBranch.getId());
    }
    
}
