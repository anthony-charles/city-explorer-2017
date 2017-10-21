package fr.hei.devweb.cityexplorer.pojos;

public enum Country {
    FR("France"),
    UK("United Kingdom"),
    USA("United States of America");

    private String label;

    Country(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
