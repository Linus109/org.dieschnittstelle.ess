package org.dieschnittstelle.ess.mip.components.erp.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;

import static org.dieschnittstelle.ess.utils.Utils.show;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.crud.impl.StockItemCRUD;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.PointOfSaleCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

@ApplicationScoped
@Transactional
@Logged
public class StockSystemImpl implements StockSystem {

	// Requirement 3: Declare instance attributes for dependency injection
	@Inject
	private StockItemCRUD stockItemCRUD;

	@Inject
	private PointOfSaleCRUD pointOfSaleCRUD;

	@Override
	public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
		show("StockSystemImpl addToStock");
		PointOfSale pos = pointOfSaleCRUD.readPointOfSale(pointOfSaleId);
		StockItem stockItem = stockItemCRUD.readStockItem(product, pos);
		
		if (stockItem != null) {
			// Update existing stock item
			stockItem.setUnits(stockItem.getUnits() + units);
			stockItemCRUD.updateStockItem(stockItem);
		} else {
			// Create new stock item
			stockItem = new StockItem(product, pos, units);
			stockItemCRUD.createStockItem(stockItem);
		}
	}

	@Override
	public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
		show("StockSystemImpl removeFromStock");
		
		PointOfSale pos = pointOfSaleCRUD.readPointOfSale(pointOfSaleId);
		StockItem stockItem = stockItemCRUD.readStockItem(product, pos);
		
		if (stockItem != null) {
			stockItem.setUnits(stockItem.getUnits() - units);
			stockItemCRUD.updateStockItem(stockItem);
		}
	}

	@Override
	public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
		show("StockSystemImpl getProductsOnStock");
		
		PointOfSale pos = pointOfSaleCRUD.readPointOfSale(pointOfSaleId);
		return stockItemCRUD.readStockItemsForPointOfSale(pos).stream()
				.map(StockItem::getProduct).collect(Collectors.toList());
	}

	@Override
	public List<IndividualisedProductItem> getAllProductsOnStock() {
		show("StockSystemImpl getAllProductsOnStock");
		
		return pointOfSaleCRUD.readAllPointsOfSale().stream()
				.flatMap(pos -> getProductsOnStock(pos.getId()).stream())
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
		show("StockSystemImpl getUnitsOnStock");
		
		PointOfSale pos = pointOfSaleCRUD.readPointOfSale(pointOfSaleId);
		StockItem stockItem = stockItemCRUD.readStockItem(product, pos);
		if (stockItem != null) {
			return stockItem.getUnits();
		}
		return 0;
	}

	@Override
	public int getTotalUnitsOnStock(IndividualisedProductItem product) {
		show("StockSystemImpl getTotalUnitsOnStock");
		
		int totalUnits = 0;
		for (PointOfSale poi : pointOfSaleCRUD.readAllPointsOfSale()) {
			StockItem stockItem = stockItemCRUD.readStockItem(product, poi);
			if (stockItem != null) {
				totalUnits += stockItem.getUnits();
			}
		}
		return totalUnits;
	}

	@Override
	public List<Long> getPointsOfSale(IndividualisedProductItem product) {
		show("StockSystemImpl getPointsOfSale");
		
		return pointOfSaleCRUD.readAllPointsOfSale().stream()
				.filter(poi -> stockItemCRUD.readStockItem(product, poi) != null)
				.map(PointOfSale::getId)
				.collect(Collectors.toList());
	}

}