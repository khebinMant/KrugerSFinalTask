package krugers.microservicio.customer.customermicroservice.service.Branch;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import krugers.microservicio.customer.customermicroservice.entity.Branch;
import krugers.microservicio.customer.customermicroservice.repository.BranchRepository;

@Service
public class BranchServiceImpl implements IBranchService{


    @Autowired
    BranchRepository branchRepository;

    @Override
    public Branch createBranch(Branch branch, Long customerId) {

        branch.setCustomerId(customerId);
        branch.setStatus("CREATED");
        branch.setCreateAt(new Date());
        branch.setUpdateAt(new Date());
        return branchRepository.save(branch);
    }

    @Override
    public Branch updateBranch(Branch branch) {
        Branch branchDB = getBranch(branch.getId());
        if(branchDB == null){
            return null;
        }
        branchDB.setCity(branch.getCity());
        branchDB.setProvince(branch.getProvince());
        branchDB.setStreet(branch.getStreet());
        branchDB.setAddress(branch.getAddress());
        branchDB.setUpdateAt(new Date());
        

        return branchRepository.save(branchDB);
    }

    @Override
    public Branch deleteBranch(Long id) {
        Branch branchDB = getBranch(id);
        if(branchDB ==  null){
            return null;
        }
        branchDB.setStatus("DELETED");
        return branchRepository.save(branchDB);
    }

    @Override
    public Branch getBranch(Long id) {
        return branchRepository.findById(id).orElse(null);
    }

    @Override
    public List<Branch> findByCustomerId(Long id) {
        return branchRepository.findByCustomerId(id);
    }

    @Override
    public List<Branch> findBranchs() {
        return branchRepository.findAll();
    }

    @Override
    public Branch updateMatrizBranch(Branch branch,Boolean isMatriz) {
        Branch branchDB = getBranch(branch.getId());
        branchDB.setIsMatriz(isMatriz);
        branchRepository.save(branchDB);
        return branchDB;
    }
    
}
