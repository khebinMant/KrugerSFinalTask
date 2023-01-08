package krugers.microservicio.customer.customermicroservice.client.fallbacks;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import krugers.microservicio.customer.customermicroservice.client.CompanyClient;
import krugers.microservicio.customer.customermicroservice.model.Company;

@Component
public class CompanyFallback implements CompanyClient{

    @Override
    public ResponseEntity<Company> getCompany(Long id) {
        Company company = new Company();
        company.setCompanyName("Default");
        company.setCreateAt(new Date());
        company.setStatus("NO AVALIABLE");
        company.setUpdateAt(new Date());
        return ResponseEntity.ok(company);
    }
    
}
