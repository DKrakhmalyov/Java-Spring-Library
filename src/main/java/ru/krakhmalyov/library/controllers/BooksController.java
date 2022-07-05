package ru.krakhmalyov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krakhmalyov.library.models.Book;
import ru.krakhmalyov.library.models.Person;
import ru.krakhmalyov.library.services.BooksService;
import ru.krakhmalyov.library.services.PeopleService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }


    @GetMapping()
    public String index(@RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer bpp,
                        @RequestParam(name = "sort_by_year", required = false) Boolean srt
            ,Model model){
        System.out.println("Index for" + page +  bpp);
        if(bpp == null || page == null) {
            bpp = Integer.MAX_VALUE;
            page = 0;
        }

        if(srt!=null)
            model.addAttribute("books", booksService.findAll(page, bpp, srt));
        else
            model.addAttribute("books", booksService.findAll(page, bpp, false));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){

        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("owner", booksService.getOwner(id));
        model.addAttribute("people", peopleService.findAll());
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

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/set")
    public String set(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        Person person1 = peopleService.findOne(person.getId());
        booksService.setOwner(person1, id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/vacate")
    public String vacate(Model model,
                         @PathVariable("id") int id) {
        booksService.setOwner(null, id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model){
        model.addAttribute("books_found", new ArrayList<Book>());
        model.addAttribute("first", true);
        return "books/search";
    }
    @PostMapping("/search")
    public String postSearch(Model model, @RequestParam(name = "prefix") String pref){
        List<Book> bookList = booksService.findByNameStartingWith(pref);

        model.addAttribute("books_found", bookList);
        model.addAttribute("first", false);
        return "books/search";
    }
}