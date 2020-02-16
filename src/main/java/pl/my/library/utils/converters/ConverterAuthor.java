package pl.my.library.utils.converters;

import pl.my.library.datbase.models.Author;
import pl.my.library.modelFX.AuthorFx;

public class ConverterAuthor {

    //Odwrotna konwersja niz dla ConverterCategory
    public static Author convertToAuthor(AuthorFx authorFx) {
        Author author = new Author();
        author.setId(authorFx.getId());
        author.setName(authorFx.getName());
        author.setSurname(authorFx.getSurname());
        return author;
    }
    //w drugą stronę niż wyżej
    public static AuthorFx convertToAuthorFx (Author author) {
        AuthorFx authorFx  = new AuthorFx();
        authorFx.setId(author.getId());
        authorFx.setName(author.getName());
        authorFx.setSurname(author.getSurname());
        return authorFx;
    }


}
