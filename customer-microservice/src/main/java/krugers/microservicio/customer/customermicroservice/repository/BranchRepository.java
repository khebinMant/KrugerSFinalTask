package krugers.microservicio.customer.customermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import krugers.microservicio.customer.customermicroservice.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long>{
    public List<Branch> findByCustomerId(Long id);
}
