package ru.krakhmalyov.library.models;

import javax.validation.constraints.*;
import java.util.Optional;

public class Book {

    private int id;

    public Optional<Integer> getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Optional<Integer> person_id) {
        this.person_id = person_id;
    }

    private Optional<Integer> person_id;
    @NotEmpty(message = "Name should not be empty")

    @Size(min = 2, max = 200, message = "Name should be between 2 and 30 characters")
    private String name;

    private String author;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
