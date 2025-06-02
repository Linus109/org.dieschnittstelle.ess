package org.dieschnittstelle.ess.mip.components.erp.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.crud.impl.StockItemCRUD;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.PointOfSaleCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

@ApplicationScoped
@Logged
public class StockSystemImpl implements StockSystem {

	// Requirement 3: Declare instance attributes for dependency injection
	@Inject
	private StockItemCRUD stockItemCRUD;

	@Inject
	private PointOfSaleCRUD pointOfSaleCRUD;

	@Override
	public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
		// TODO MIP+JPA4: implement using stockItemCRUD and pointOfSaleCRUD
	}

	@Override
	public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
		// TODO MIP+JPA4: implement using stockItemCRUD and pointOfSaleCRUD
	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
		// TODO MIP+JPA4: implement using stockItemCRUD and pointOfSaleCRUD
		return null;
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		// TODO MIP+JPA4: implement using stockItemCRUD and pointOfSaleCRUD
		return null;
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
		// TODO MIP+JPA4: implement using stockItemCRUD and pointOfSaleCRUD
		return 0;
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		// TODO MIP+JPA4: implement using stockItemCRUD
		return 0;
	}

	@Override
	public List<Long> getPointsOfSale(IndividualisedProductItem product) {
		// TODO MIP+JPA4: implement using stockItemCRUD
		return null;
	}

}