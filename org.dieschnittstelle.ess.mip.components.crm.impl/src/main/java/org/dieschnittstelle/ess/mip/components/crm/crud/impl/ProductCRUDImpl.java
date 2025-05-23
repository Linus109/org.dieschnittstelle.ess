package org.dieschnittstelle.ess.mip.components.crm.crud.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;

import java.util.List;

import static org.dieschnittstelle.ess.utils.Utils.show;

@ApplicationScoped
public class ProductCRUDImpl implements ProductCRUD {

    public ProductCRUDImpl() {
        show("constructor(): ProductCRUDImpl created!");
    }

    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        show("createProduct(): " + prod);
        return prod;
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        show("readAllProducts()");
        return List.of();
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        return null;
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        return null;
    }

    @Override
    public boolean deleteProduct(long productID) {
        return false;
    }

    @Override
    public List<Campaign> getCampaignsForProduct(long productID) {
        return List.of();
    }
}
