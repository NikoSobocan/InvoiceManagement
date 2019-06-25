package DataAccess;

import InvoiceManagement.Company;

public interface CompanyDao extends DaoCrud<Company>{

	Company getByTaxNo(String regNo);
	
}
