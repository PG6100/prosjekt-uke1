package no.nith.pg6100;

import java.util.List;
import java.util.concurrent.Callable;

public class PersonDataAksess {

    private final javax.persistence.EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("PG6100").createEntityManager();

    public void lagrePerson(Person person) {
        em.getTransaction().begin();
        try {
             em.persist(person);
        } finally {
            em.getTransaction().commit();
        }
    }


    public Person hentPerson(String epost) {
        em.getTransaction().begin();
        try {
            return (Person) em.createQuery("from Person p where p.epost like ?1").setParameter(1,epost).getSingleResult();
        } finally {
            em.getTransaction().commit();
        }
    }

    public Person hentPerson(int personId) {
        em.getTransaction().begin();
        try {
            return em.find(Person.class,personId);
        } finally {
            em.getTransaction().commit();
        }

    }


    public List hentAllePersoner() {
        em.getTransaction().begin();
        try {
            return em.createQuery("from Person").getResultList();
        } finally {
            em.getTransaction().commit();
        }
    }

    public List hentPersonMedFornavn(String fornavn) {
        em.getTransaction().begin();
        try {
            return em.createQuery("from Person p where p.fornavn like ?1").setParameter(1,fornavn).getResultList();
        } finally {
            em.getTransaction().commit();
        }
    }

    public void slettPerson(int personId) {
        em.getTransaction().begin();
        try {
            em.remove(em.find(Person.class,personId));
        } finally {
            em.getTransaction().commit();
        }
    }

    public void slettPerson(Person person) {
        em.getTransaction().begin();
        try {
            em.remove(person);
        } finally {
            em.getTransaction().commit();
        }

    }
}

