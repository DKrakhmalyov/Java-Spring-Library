package ru.krakhmalyov.library.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.krakhmalyov.library.models.Book;
import ru.krakhmalyov.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Person> index(){
        Session session = sessionFactory.getCurrentSession();

        List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();
        return people;

    }
    @Transactional
    public Person show(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = (Query) session.createQuery("select p from Person p WHERE id=:paramName");

        query.setParameter("paramName", person_id);
        return (Person) query.getResultList().get(0);
    }
    @Transactional
    public Optional<Person> show(String name){
        Session session = sessionFactory.getCurrentSession();
        Query query = (Query) session.createQuery("select p from Person p WHERE name=" + name);

        Person person =  (Person) query.getResultList().get(0);

        return Optional.ofNullable(person);
          }
    @Transactional
    public void save(Person person){
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
      }
    @Transactional
    public void update(int person_id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        person.setName(updatedPerson.getName());
        person.setBirthyear(updatedPerson.getBirthyear());
      }
    @Transactional
    public void delete(int person_id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, person_id);
        session.remove(person);
    }
    @Transactional
    public List<Book> showBooks(int person_id){
        Session session = sessionFactory.getCurrentSession();
        return  session.createQuery("from Book where owner=" + person_id).getResultList();

    }
}
