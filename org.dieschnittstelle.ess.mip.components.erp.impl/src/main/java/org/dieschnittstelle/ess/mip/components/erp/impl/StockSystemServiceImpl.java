package org.dieschnittstelle.ess.mip.components.erp.impl;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystemService;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

@ApplicationScoped
@Logged
public class StockSystemServiceImpl implements StockSystemService {

	@Inject
	private StockSystem stockSystem;

	@Inject
	private ProductCRUD productCRUD;

	@Override
	public void addToStock(long productId, long pointOfSaleId, int units) {
		// Convert ID to object as required by StockSystem interface
		IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
		if (product != null) {
			stockSystem.addToStock(product, pointOfSaleId, units);
		}
	}

	@Override
	public void removeFromStock(long productId, long pointOfSaleId, int units) {
		// Convert ID to object as required by StockSystem interface
		IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
		if (product != null) {
			stockSystem.removeFromStock(product, pointOfSaleId, units);
		}
	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
		// Handle special case: pointOfSaleId 0 means "all products on stock"
		if (pointOfSaleId == 0) {
			return stockSystem.getAllProductsOnStock();
		} else {
			return stockSystem.getProductsOnStock(pointOfSaleId);
		}
	}

	@Override
	public int getUnitsOnStock(long productId, long pointOfSaleId) {
		// Convert ID to object as required by StockSystem interface
		IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
		if (product != null) {
			// Handle special case: pointOfSaleId 0 means "total units on stock"
			if (pointOfSaleId == 0) {
				return stockSystem.getTotalUnitsOnStock(product);
			} else {
				return stockSystem.getUnitsOnStock(product, pointOfSaleId);
			}
		}
		return 0;
	}

	@Override
	public List<Long> getPointsOfSale(long productId) {
		// Convert ID to object as required by StockSystem interface
		IndividualisedProductItem product = (IndividualisedProductItem) productCRUD.readProduct(productId);
		if (product != null) {
			return stockSystem.getPointsOfSale(product);
		}
		return null;
	}

}