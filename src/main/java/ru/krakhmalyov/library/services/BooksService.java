package ru.krakhmalyov.library.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krakhmalyov.library.models.Book;
import ru.krakhmalyov.library.models.Person;
import ru.krakhmalyov.library.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class BooksService {


    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAll(int page, int bpp, Boolean srt){
        if(!srt)
            return booksRepository.findAll(PageRequest.of(page, bpp)).getContent();
        return booksRepository.findAll(PageRequest.of(page, bpp, Sort.by("year"))).getContent();
    }

    public Book findOne(int id){
        Optional<Book> book =  booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book){
        book.setEditedAt(new Date());
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    public Optional<Person> getOwner(int id){
        Book book = booksRepository.findById(id).get();
        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    public void setOwner(Person person, int id){
        booksRepository.findById(id).get().setEditedAt(new Date());
        booksRepository.findById(id).get().setOwner(person);
    }

    public List<Book> findByNameStartingWith(String prefix){
        return booksRepository.findByNameStartingWith(prefix);
    }

    public Boolean isOutDated(int id){
        long out = TimeUnit
                .MILLISECONDS
                .toDays(new Date().getTime() - booksRepository.findById(id).get().getEditedAt().getTime());

        return out < 10;
    }
}
