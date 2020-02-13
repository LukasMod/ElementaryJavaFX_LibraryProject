package pl.my.library.utils.converters;

import pl.my.library.datbase.models.Author;
import pl.my.library.modelFX.AuthorFX;

public class ConverterAuthor {

    //Odwrotna konwersja niz dla ConverterCategory
    public static Author convertAuthorFXToAuthor(AuthorFX authorFX) {


        Author author = new Author();
        author.setName(authorFX.getName());
        author.setSurname(authorFX.getSurname());
        return author;

//        AuthorFX authorFX = new AuthorFX();
//        authorFX.setId(author.getId());
//        authorFX.setName(author.getName());
//        authorFX.setSurname(author.getName());

    }
}
