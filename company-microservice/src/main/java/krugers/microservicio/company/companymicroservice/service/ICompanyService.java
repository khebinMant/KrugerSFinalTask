package krugers.microservicio.company.companymicroservice.service;

import java.util.List;

import krugers.microservicio.company.companymicroservice.entity.Company;


public interface ICompanyService {

    public List<Company> listAllCompanies();
    public Company createCompany(Company company);
    public Company updateCompany(Company company);//Me sirve para actualizar también la matriz del customer
    public Company deleteCompany(Long id);
    public Company getCompany(Long id);
}
