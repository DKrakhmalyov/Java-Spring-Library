package ru.krakhmalyov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krakhmalyov.library.dao.BookDAO;
import ru.krakhmalyov.library.dao.PersonDAO;
import ru.krakhmalyov.library.models.Book;
import ru.krakhmalyov.library.models.Person;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO){
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("owner", bookDAO.getOwner(id));
        model.addAttribute("people", personDAO.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/set")
    public String set(@ModelAttribute("book") Book book) {
        bookDAO.setOwner(book.getPerson_id().get(), book.getId());
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/vacate")
    public String vacate(Model model,
                         @PathVariable("id") int id) {
        bookDAO.vacate(id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}