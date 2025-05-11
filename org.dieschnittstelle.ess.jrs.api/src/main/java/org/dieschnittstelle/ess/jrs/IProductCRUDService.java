package org.dieschnittstelle.ess.jrs;

import jakarta.ws.rs.*;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import java.util.List;

/*
 * UE JRS2: 
 * deklarieren Sie hier Methoden fuer:
 * - die Erstellung eines Produkts
 * - das Auslesen aller Produkte
 * - das Auslesen eines Produkts
 * - die Aktualisierung eines Produkts
 * - das Loeschen eines Produkts
 * und machen Sie diese Methoden mittels JAX-RS Annotationen als WebService verfuegbar
 */

/*
 * TODO JRS3: aendern Sie Argument- und Rueckgabetypen der Methoden von IndividualisedProductItem auf AbstractProduct
 */

@Path("/products")
@Consumes({"application/json"})
@Produces({"application/json"})
public interface IProductCRUDService {

	@POST
	public AbstractProduct createProduct(AbstractProduct prod);

	@GET
	public List<AbstractProduct> readAllProducts();

	@PUT
	@Path("/{productsId}")
	public AbstractProduct updateProduct(@PathParam("productsId") long id, AbstractProduct update);

	@DELETE
	@Path("/{productsId}")
	boolean deleteProduct(@PathParam("productsId") long id);

	@GET
	@Path("/{productsId}")
	public AbstractProduct readProduct(@PathParam("productsId") long id);
			
}
