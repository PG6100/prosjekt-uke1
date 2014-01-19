package no.nith.pg6100;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;

public class PersonTest{
        private EntityManager em;

        @Before
        public void beforeEach(){
            //TODO
            em = null;
        }

        @After
        public void afterEach(){
            em.close();
        }

        @Test
        public void testLagring(){

        }



}
