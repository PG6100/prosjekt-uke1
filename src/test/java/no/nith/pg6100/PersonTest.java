package no.nith.pg6100;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

public class PersonTest{
        private EntityManager em;

        @Before
        public void beforeEach(){
            em=javax.persistence.Persistence.createEntityManagerFactory("PG6100").createEntityManager();
        }

        @After
        public void afterEach(){
            em.close();
        }



    @Test
    public void testLagring(){
        em.getTransaction().begin();
        Person person = getPerson();
        em.persist(person);
        em.flush();
        Person p=(Person) em.createQuery("from Person p where p.epost=?1").setParameter(1,"test1@test.no").getSingleResult();
        assertEquals("Ola", p.getFornavn());
        assertEquals("Nordmann", p.getEtternavn());
        em.getTransaction().commit();
    }

    private Person getPerson() {
        Person person = new Person();
        person.setEpost("test1@test.no");
        person.setFornavn("Ola");
        person.setEtternavn("Nordmann");
        return person;
    }

    @Test
    public void testHentPerson(){
        em.getTransaction().begin();
        Person person = getPerson();
        em.persist(person);
        Person person2 = em.find(Person.class, person.getPersonId());
        assertEquals("Ola",person2.getFornavn());
        em.getTransaction().commit();

    }
}
