package pl.my.library.utils.converters;

import pl.my.library.datbase.models.Book;
import pl.my.library.modelFX.BookFx;
import pl.my.library.utils.Utils;

public class ConverterBook {
    public static Book convertToBook(BookFx bookFx) {
        Book book = new Book();
        book.setId(bookFx.getId());
        book.setTitle(bookFx.getTitle());
        book.setDescription(bookFx.getDescription());
        book.setIsbn(bookFx.getIsbn());
        book.setReleaseDate(Utils.convertToDate(bookFx.getReleaseDate()));
        book.setAddedDate(Utils.convertToDate(bookFx.getAddedDate()));
        return book;
    }
}
