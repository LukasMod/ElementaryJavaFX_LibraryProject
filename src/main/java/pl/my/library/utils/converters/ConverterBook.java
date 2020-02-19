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

    public static BookFx convertToBookFx(Book book) {
        BookFx bookFx = new BookFx();
        bookFx.setId(book.getId());
        bookFx.setTitle(book.getTitle());
        bookFx.setDescription(book.getDescription());
        bookFx.setRating(book.getRating());
        bookFx.setIsbn(book.getIsbn());
        bookFx.setReleaseDate(Utils.convertToLocalDate(book.getReleaseDate()));
        bookFx.setAuthorFxObjectProperty(ConverterAuthor.convertToAuthorFx(book.getAuthor()));
        bookFx.setCategoryFxObjectProperty(ConverterCategory.convertToCategoryFX(book.getCategory()));

        return bookFx;
    }
}
