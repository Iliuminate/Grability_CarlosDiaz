package com.grability.iliuminate.grabilityprueba.ModelClasses;

/**
 * Created by Iliuminate on 09/01/2016.
 */
public class AuthorClass {

    String autor,uri;

    public AuthorClass() {
    }

    public AuthorClass(String autor, String uri) {
        this.autor = autor;
        this.uri = uri;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "AuthorClass{" +
                "autor='" + autor + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }

}
