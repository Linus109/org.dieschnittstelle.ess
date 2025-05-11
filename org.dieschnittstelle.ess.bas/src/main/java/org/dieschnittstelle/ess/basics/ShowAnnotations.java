package org.dieschnittstelle.ess.basics;


import org.dieschnittstelle.ess.basics.annotations.AnnotatedStockItemBuilder;
import org.dieschnittstelle.ess.basics.annotations.StockItemProxyImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static org.dieschnittstelle.ess.utils.Utils.*;

public class ShowAnnotations {

	public static void main(String[] args) {
		// we initialise the collection
		StockItemCollection collection = new StockItemCollection(
				"stockitems_annotations.xml", new AnnotatedStockItemBuilder());
		// we load the contents into the collection
		collection.load();

		for (IStockItem consumable : collection.getStockItems()) {
			showAttributes(((StockItemProxyImpl)consumable).getProxiedObject());
		}

		// we initialise a consumer
		Consumer consumer = new Consumer();
		// ... and let them consume
		consumer.doShopping(collection.getStockItems());
	}

	/*
	 * TODO BAS2
	 */
	private static void showAttributes(Object instance) {
		try {
			// TODO BAS2: create a string representation of instance by iterating
			//  over the object's attributes / fields as provided by its class
			//  and reading out the attribute values. The string representation
			//  will then be built from the field names and field values.
			//  Note that only read-access to fields via getters or direct access
			//  is required here.

			StringBuilder builder = new StringBuilder("{");

			Class<?> klass = instance.getClass();
			DisplayAs classAnnotation = klass.getAnnotation(DisplayAs.class);
			String klassName = classAnnotation != null ? classAnnotation.value() : klass.getSimpleName();
			builder.append(klassName).append(" ");

            Field[] fields = klass.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				DisplayAs fieldAnnotation = field.getAnnotation(DisplayAs.class);
				String displayName = fieldAnnotation != null ? fieldAnnotation.value() : fieldName;
				builder.append(displayName).append(":");
				String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method getter = klass.getDeclaredMethod(getterName);
                // invoke the setter
				Object value = getter.invoke(instance);
                builder.append(value);

                // check if field is the last field of the fields array
                if (field != fields[fields.length - 1]) {
                    builder.append(", ");
                }
			}

            builder.append("}");
            System.out.println(builder);


			// TODO BAS3: if the new @DisplayAs annotation is present on a field,
			//  the string representation will not use the field's name, but the name
			//  specified in the the annotation. Regardless of @DisplayAs being present
			//  or not, the field's value will be included in the string representation.

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("showAnnotations(): exception occurred: " + e,e);
		}

	}

}
