package facades;

import dto.ContactDTO;
import dto.ContactsDTO;
import entities.Contact;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class ContactFacadeTest {

    private static EntityManagerFactory emf;
    private static ContactFacade facade;
    

    public ContactFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = ContactFacade.getContactFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Contact.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    @Disabled
    public void testAFacadeMethod() {
       // assertEquals(2, facade.getRenameMeCount(), "Expects two rows in the database");
    }
    
    @Test 
    public void testAddContact(){
        ContactFacade contactFacade = ContactFacade.getContactFacade(emf);
        int expResult = 1;
        ContactDTO c1 = new ContactDTO("Daniel", "cph@cph.dk", "DinTøjmand", "CEO", "12345678");
        
        contactFacade.addContact(c1);
        ContactsDTO result = contactFacade.getAllContacts();
        assertEquals(expResult, result.getAll().size());
        
    }

}
