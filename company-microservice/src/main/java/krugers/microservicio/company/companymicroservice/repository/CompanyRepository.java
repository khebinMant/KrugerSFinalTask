package krugers.microservicio.company.companymicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import krugers.microservicio.company.companymicroservice.entity.Company;


public interface CompanyRepository extends JpaRepository<Company, Long>{
    
}
