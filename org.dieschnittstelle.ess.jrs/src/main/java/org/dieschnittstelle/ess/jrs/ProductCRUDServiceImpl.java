package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.NotFoundException;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;


import java.util.List;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {
    private GenericCRUDExecutor<AbstractProduct> crudExecutor;

    public ProductCRUDServiceImpl(@Context ServletContext servletContext) {
        this.crudExecutor = (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");
    }

    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        return (AbstractProduct) this.crudExecutor.createObject(prod);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AbstractProduct> readAllProducts() {
        return (List<AbstractProduct>) (List<?>) this.crudExecutor.readAllObjects();
    }

    @Override
    public AbstractProduct updateProduct(long id, AbstractProduct update) {
        return (AbstractProduct) this.crudExecutor.updateObject(update);
    }

    @Override
    public boolean deleteProduct(long id) {
        return this.crudExecutor.deleteObject(id);
    }

    @Override
    public AbstractProduct readProduct(long id) {
    	AbstractProduct product = (AbstractProduct) this.crudExecutor.readObject(id);
    	if (product == null) {
    		throw new NotFoundException("Product with id " + id + " not found.");
    	}
    	return product;
    }
   
}
