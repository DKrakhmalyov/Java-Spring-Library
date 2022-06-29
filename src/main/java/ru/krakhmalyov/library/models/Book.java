package ru.krakhmalyov.library.models;

import javax.validation.constraints.*;

public class Book {

    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 200, message = "Name should be between 2 and 30 characters")
    private String name;

    private String autor;

    @Max(value = 2022, message = "Year of birth should be less that 2022")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
