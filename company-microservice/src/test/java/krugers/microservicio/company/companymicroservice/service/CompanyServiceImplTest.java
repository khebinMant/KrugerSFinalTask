package krugers.microservicio.company.companymicroservice.service;

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


import krugers.microservicio.company.companymicroservice.entity.Company;
import krugers.microservicio.company.companymicroservice.repository.CompanyRepository;

@SpringBootTest(classes = {CompanyServiceImplTest.class})
@RunWith(SpringRunner.class)
public class CompanyServiceImplTest {
    
    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyServiceImpl companyServiceImpl;

    //Testing obtener todos las compañias
    @Test
    @Order(1)
    public void test_listAllCompanies(){
        
        List<Company> customCompanies =  new ArrayList<Company>();

        customCompanies.add(new Company(1L,"PizzaHut", "CREATED", new Date(), new Date()));
        customCompanies.add(new Company(2L,"El Brasero", "CREATED", new Date(), new Date()));
        
        when(companyRepository.findAll()).thenReturn(customCompanies);
        assertEquals(2, StreamSupport.stream(companyServiceImpl.listAllCompanies().spliterator(), false).count());
    }

    //Testing obtener compañia por su Id
    @Test
    @Order(2)
    public void test_getCompany(){

        Company customCompany = new Company(1L,"PizzaHut", "CREATED", new Date(), new Date());

        when(companyRepository.findById(1L)).thenReturn(Optional.of(customCompany));
        Assertions.assertThat(customCompany.getId()).isEqualTo(1L);
    }

    //Testing crear compañia
    @Test
    @Order(3)
    public void test_createCompany(){

        Company customCompany = new Company(1L,"PizzaHut", "CREATED", new Date(), new Date());

        when(companyRepository.save(customCompany)).thenReturn(customCompany);
        assertEquals(customCompany,companyRepository.save(customCompany));
    }

    //Testing actualizar branch
    @Test
    @Order(4)
    public void test_updateCompany(){

        Company customCompany = new Company(1L,"PizzaHut", "CREATED", new Date(), new Date());

        when(companyRepository.save(customCompany)).thenReturn(customCompany);
        assertEquals(customCompany,companyRepository.save(customCompany));
    }

    //Testing eliminar branch
    @Test
    @Order(7)
    public void test_deleteCompany(){

        Company customCompany = new Company(1L,"PizzaHut", "CREATED", new Date(), new Date());

        companyRepository.deleteById(customCompany.getId());

        when(companyRepository.save(customCompany)).thenReturn(customCompany);
        doThrow(RuntimeException.class).when(companyRepository).deleteById(customCompany.getId());
        verify(companyRepository, times(1)).deleteById(customCompany.getId());
    }
}
