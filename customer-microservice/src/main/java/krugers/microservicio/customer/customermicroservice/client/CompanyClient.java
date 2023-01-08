package krugers.microservicio.customer.customermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import krugers.microservicio.customer.customermicroservice.client.fallbacks.CompanyFallback;
import krugers.microservicio.customer.customermicroservice.model.Company;

@FeignClient(name="company-microservice", path="/companies", fallback = CompanyFallback.class)
public interface CompanyClient {

    @GetMapping(value="/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable(name="id") Long id);

}
