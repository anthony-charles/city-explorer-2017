package fr.hei.devweb.cityexplorer.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Comment {

    private Integer id;
    private String pseudo;
    private LocalDateTime creationDate;
    private String message;

    public Comment(Integer id, String pseudo, LocalDateTime creationDate, String message) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
