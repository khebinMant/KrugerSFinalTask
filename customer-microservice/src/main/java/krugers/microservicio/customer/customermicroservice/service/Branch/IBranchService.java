package krugers.microservicio.customer.customermicroservice.service.Branch;

import java.util.List;

import krugers.microservicio.customer.customermicroservice.entity.Branch;

public interface IBranchService {

    public List<Branch> findBranchs();
    public Branch createBranch(Branch branch, Long customerId);
    public Branch updateBranch(Branch branch);//Me sirve para actualizar también la matriz del customer
    public Branch deleteBranch(Long id);
    public Branch getBranch(Long id);
    public List<Branch> findByCustomerId(Long customerId);
    public Branch updateMatrizBranch(Branch branch, Boolean isMatriz);
}
