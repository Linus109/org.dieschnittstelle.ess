package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;
import org.dieschnittstelle.ess.mip.components.erp.crud.impl.EntityManagerProvider.ERPDataAccessor;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

@ApplicationScoped
@Transactional
@Logged
public class StockItemCRUDImpl implements StockItemCRUD {

	@Inject
	@ERPDataAccessor
	private EntityManager entityManager;

	@Override
	public StockItem createStockItem(StockItem item) {
		// TODO MIP+JPA4: implement using entityManager
		return item;
	}

	@Override
	public StockItem readStockItem(IndividualisedProductItem prod, PointOfSale pos) {
		// TODO MIP+JPA4: implement using entityManager query
		return null;
	}

	@Override
	public StockItem updateStockItem(StockItem item) {
		// TODO MIP+JPA4: implement using entityManager
		return item;
	}

	@Override
	public List<StockItem> readStockItemsForProduct(IndividualisedProductItem prod) {
		// TODO MIP+JPA4: implement using entityManager query
		return null;
	}

	@Override
	public List<StockItem> readStockItemsForPointOfSale(PointOfSale pos) {
		// TODO MIP+JPA4: implement using entityManager query
		return null;
	}

}