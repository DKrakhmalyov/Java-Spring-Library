package ru.krakhmalyov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.krakhmalyov.library.models.Book;
import ru.krakhmalyov.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Books WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public Optional<Book> show(String name){
        return jdbcTemplate.query("SELECT * FROM Books WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Books(name, year, author) VALUES(?,?,?)", book.getName(), book.getYear(), book.getAuthor());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Books SET name=?, year=?, author=? WHERE id=?",  updatedBook.getName(), updatedBook.getYear(), updatedBook.getAuthor(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Books WHERE id=?", id);
    }

    public void vacate(int id) {
        jdbcTemplate.update("UPDATE Books SET person_id=null WHERE id=?", id);
    }

    public void setOwner(int person_id, int book_id){
        jdbcTemplate.update("UPDATE Books SET person_id=? WHERE id=?", person_id, book_id);
    }
    public Optional<Person> getOwner(int book_id){
        Optional<Book> p_id = jdbcTemplate.query("SELECT * FROM Books WHERE id=?", new Object[]{book_id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
            System.out.println(p_id.get().getName() + " " + p_id.get().getPerson_id());
        if (p_id.get().getPerson_id().isPresent())
            return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{p_id.get().getPerson_id().get()}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
        else
            return Optional.empty();
    }
}
