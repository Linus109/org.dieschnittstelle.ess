package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import java.util.List;

import static org.dieschnittstelle.ess.utils.Utils.show;

@ApplicationScoped
@Transactional
@Logged
@Alternative
@Priority(Interceptor.Priority.APPLICATION+10)
public class ProductCRUDImpl implements ProductCRUD {

    @Inject
    @EntityManagerProvider.ERPDataAccessor
    private EntityManager em;

    public ProductCRUDImpl() {
        show("constructor(): ProductCRUDImpl created!");
    }

    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        show("createProduct() in IMPL: " + prod);
        em.persist(prod);
        return prod;
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        show("readAllProducts()");
        Query q = em.createQuery("select p from AbstractProduct p");
        return q.getResultList();
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        return em.merge(update);
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        return em.find(AbstractProduct.class, productID);
    }

    @Override
    public boolean deleteProduct(long productID) {
        AbstractProduct product = em.find(AbstractProduct.class, productID);
        if (product != null) {
            em.remove(product);
            return true;
        }
        return false;
    }

    @Override
    public List<Campaign> getCampaignsForProduct(long productID) {
        show("getCampaignsForProduct() in IMPL: " + productID);
        Query query = em.createQuery("SELECT DISTINCT c FROM Campaign c JOIN c.bundles b JOIN b.product p where p.id = " + productID);
        return query.getResultList();
    }
}
