package ru.krakhmalyov.library.dao;


import net.bytebuddy.dynamic.DynamicType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.krakhmalyov.library.models.Book;
import ru.krakhmalyov.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> index(){
        Session session = sessionFactory.getCurrentSession();

        List<Book> books = session.createQuery("select p from Book p", Book.class).getResultList();
        return books;
    }

    @Transactional
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = (Book) session.createQuery("select p from Book p WHERE id=" + id).getResultList().get(0);
        return book;
    }

    @Transactional
    public Optional<Book> show(String name){
        Session session = sessionFactory.getCurrentSession();
        Query query = (Query) session.createQuery("select p from Book p WHERE name=" + name);

        Book book =  (Book) query.getResultList().get(0);

        return Optional.ofNullable(book);
    }

    @Transactional
    public void save(Book book){
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(updatedBook.getOwner());
        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setYear(updatedBook.getYear());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        session.remove(book);
    }

    @Transactional
    public void vacate(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }

    @Transactional
    public void setOwner(Person person, int book_id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        book.setOwner(person);
    }

    @Transactional
    public Optional<Person> getOwner(int book_id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, book_id);
        Optional<Person> opt = Optional.ofNullable(book.getOwner());
        return opt;
    }

}
