package DataAccess;

import InvoiceManagement.Article;

public interface ArticleDao  extends DaoCrud<Article>{

	Article getByBarcode(String code);
	
}
