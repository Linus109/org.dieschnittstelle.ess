package org.dieschnittstelle.ess.mip.components.erp.impl;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.transaction.Transactional;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.PointOfSaleCRUD;
import org.dieschnittstelle.ess.mip.components.erp.crud.impl.StockItemCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.*;
import java.util.stream.Collectors;

import static org.dieschnittstelle.ess.utils.Utils.show;

@ApplicationScoped
@Transactional
@Logged
public class StockSystemImpl implements StockSystem {

    @Inject
    private PointOfSaleCRUD posCRUD;

    @Inject
    private StockItemCRUD stockItemCRUD;

    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        show("StockSystemImpl addToStock");
        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        StockItem stockItem = stockItemCRUD.readStockItem(product, pos);

        if (stockItem != null) {
            stockItem.setUnits(stockItem.getUnits() + units);
            stockItemCRUD.updateStockItem(stockItem);
        } else {
            stockItem = new StockItem(product, pos, units);
            stockItemCRUD.createStockItem(stockItem);
        }
    }

    @Override
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        show("StockSystemImpl removeFromStock");

        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        StockItem stockItem = stockItemCRUD.readStockItem(product, pos);

        if (stockItem != null) {
            stockItem.setUnits(stockItem.getUnits() - units);
            stockItemCRUD.updateStockItem(stockItem);
        }
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        show("StockSystemImpl getProductsOnStock");

        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        return stockItemCRUD.readStockItemsForPointOfSale(pos).stream()
                .map(StockItem::getProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        show("StockSystemImpl getAllProductsOnStock");

        return  posCRUD.readAllPointsOfSale().stream()
                .flatMap(pos -> getProductsOnStock(pos.getId()).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        show("StockSystemImpl getUnitsOnStock");

        PointOfSale pos = posCRUD.readPointOfSale(pointOfSaleId);
        StockItem stockItem = stockItemCRUD.readStockItem(product, pos);
        if (stockItem != null) {
            return stockItem.getUnits();
        }
        return 0;
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        show("StockSystemImpl getTotalUnitsOnStock");

        return posCRUD.readAllPointsOfSale()
                .stream()
                .map(poi -> stockItemCRUD.readStockItem(product, poi))
                .filter(Objects::nonNull)
                .mapToInt(StockItem::getUnits)
                .sum();
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        show("StockSystemImpl getPointsOfSale");

        return posCRUD.readAllPointsOfSale().stream()
                .filter(poi -> stockItemCRUD.readStockItem(product, poi) != null)
                .map(PointOfSale::getId)
                .collect(Collectors.toList());
    }
}
