package pl.my.library.utils.converters;

import pl.my.library.datbase.models.Category;
import pl.my.library.modelFX.CategoryFx;

//będziemy mogli korzzystać z tej metody wszędzie i w jednym miejscu dodawać nowe pola dla kategorii
public class ConverterCategory {

    public static CategoryFx convertToCategoryFX(Category category) {
        CategoryFx categoryFX = new CategoryFx();
        categoryFX.setId(category.getId());
        categoryFX.setName(category.getName());
        return categoryFX;
    }


}
