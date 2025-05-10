package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
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
    public IndividualisedProductItem createProduct(IndividualisedProductItem prod) {
        return (IndividualisedProductItem) this.crudExecutor.createObject(prod);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IndividualisedProductItem> readAllProducts() {
        return (List<IndividualisedProductItem>) (List<?>) this.crudExecutor.readAllObjects();
    }

    @Override
    public IndividualisedProductItem updateProduct(long id, IndividualisedProductItem update) {
        return (IndividualisedProductItem) this.crudExecutor.updateObject(update);
    }

    @Override
    public boolean deleteProduct(long id) {
        return this.crudExecutor.deleteObject(id);
    }

    @Override
    public IndividualisedProductItem readProduct(long id) {
    	IndividualisedProductItem product = (IndividualisedProductItem) this.crudExecutor.readObject(id);
    	if (product == null) {
    		throw new NotFoundException("Product with id " + id + " not found.");
    	}
    	return product;
    }
   
}
