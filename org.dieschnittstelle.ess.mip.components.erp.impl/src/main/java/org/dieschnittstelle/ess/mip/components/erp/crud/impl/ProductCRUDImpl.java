package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.mip.components.erp.crud.impl.EntityManagerProvider.ERPDataAccessor;

@ApplicationScoped
@Transactional
public class ProductCRUDImpl implements ProductCRUD {

	@Inject
	@ERPDataAccessor
	private EntityManager entityManager;

	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		entityManager.persist(prod);
		return prod;
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return entityManager.createQuery("SELECT p FROM AbstractProduct p", AbstractProduct.class)
				.getResultList();
	}

	@Override
	public AbstractProduct updateProduct(AbstractProduct update) {
		return entityManager.merge(update);
	}

	@Override
	public AbstractProduct readProduct(long productID) {
		return entityManager.find(AbstractProduct.class, productID);
	}

	@Override
	public boolean deleteProduct(long productID) {
		AbstractProduct product = entityManager.find(AbstractProduct.class, productID);
		if (product != null) {
			entityManager.remove(product);
			return true;
		}
		return false;
	}

	@Override
	public List<Campaign> getCampaignsForProduct(long productID) {
		return new ArrayList<>();
	}
}