package ru.krakhmalyov.library.models;

import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "[A-Z]\\w+, {3}", message = "Your name should be in this format: First name, Second name, Surname")
    private String name;


    @Min(value = 1900, message = "Year of birth should be greater that 1900")
    @Max(value = 2022, message = "Year of birth should be less that 2022")
    private int birthyear;

    public Person() {

    }

    public Person(int id, String name, int birthyear) {
        this.id = id;
        this.name = name;
        this.birthyear = birthyear;
    }

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

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }

}
