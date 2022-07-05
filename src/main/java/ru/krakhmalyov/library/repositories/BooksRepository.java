package ru.krakhmalyov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krakhmalyov.library.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    public List<Book> findByNameStartingWith(String prefix);
}