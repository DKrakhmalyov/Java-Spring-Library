package ru.krakhmalyov.library.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.krakhmalyov.library.models.Book;
import ru.krakhmalyov.library.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int person_id) {
        System.out.println("Want to show id " + person_id);
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{person_id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);

    }

    public Optional<Person> show(String name){
        return jdbcTemplate.query("SELECT * FROM person WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person(name, birthyear) VALUES(?,?)", person.getName(), person.getBirthyear());
    }

    public void update(int person_id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, birthyear=? WHERE id=?", updatedPerson.getName(), updatedPerson.getBirthyear(), person_id);
    }

    public void delete(int person_id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", person_id);
    }

    public List<Book> showBooks(int person_id){
        return jdbcTemplate.query("SELECT * FROM Books WHERE person_id=?", new Object[]{person_id}, new BeanPropertyRowMapper<>(Book.class)).stream().toList();
    }
}
