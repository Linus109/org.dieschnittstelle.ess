package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;

@ApplicationScoped
@Transactional
public class ProductCRUDImpl implements ProductCRUD {

	@Override
	public AbstractProduct createProduct(AbstractProduct prod) {
		return prod;
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return new ArrayList<>();
	}

	@Override
	public AbstractProduct updateProduct(AbstractProduct update) {
		return update;
	}

	@Override
	public AbstractProduct readProduct(long productID) {
		return null;
	}

	@Override
	public boolean deleteProduct(long productID) {
		return true;
	}

	@Override
	public List<Campaign> getCampaignsForProduct(long productID) {
		return new ArrayList<>();
	}
}