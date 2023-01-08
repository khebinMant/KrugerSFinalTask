package krugers.microservicio.company.companymicroservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import krugers.microservicio.company.companymicroservice.entity.Company;
import krugers.microservicio.company.companymicroservice.repository.CompanyRepository;



@Service
public class CompanyServiceImpl implements ICompanyService{

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        company.setStatus("CREATED");
        company.setCreateAt(new Date());
        company.setUpdateAt(new Date());
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Company company) {
        Company companyDB = getCompany(company.getId());
        if(companyDB == null){
            return null;
        }
        companyDB.setCompanyName(company.getCompanyName());
        companyDB.setUpdateAt(new Date());

        return companyRepository.save(companyDB);
    }

    @Override
    public Company deleteCompany(Long id) {
        Company companyDB = getCompany(id);
        if(companyDB ==  null){
            return null;
        }
        companyDB.setStatus("DELETED");
        return companyRepository.save(companyDB);
    }

    @Override
    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> listAllCompanies() {
        return companyRepository.findAll();
    }
    
}
