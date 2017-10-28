package fr.hei.devweb.cityexplorer.pojos;

import java.time.LocalDate;

public class Comment {

    private Integer id;
    private String pseudo;
    private LocalDate creationDate;
    private String message;

    public Comment(Integer id, String pseudo, LocalDate creationDate, String message) {
        this.id = id;
        this.pseudo = pseudo;
        this.creationDate = creationDate;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
