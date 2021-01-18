package facades;

import dto.ContactDTO;
import dto.ContactsDTO;
import dto.OpportunityDTO;
import entities.Contact;
import entities.Opportunity;
import errorhandling.ContactNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
    
    private Contact c1;
    private Contact c2;
    private Opportunity o1;

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
        
        c1 = new Contact("Daniel", "cph@cph.dk", "DinTøjmand", "CEO", "12345678");
        c2 = new Contact("Hans", "hans@hans.dk", "Kiosken", "CEO", "87654321");
        o1 = new Opportunity("Secure sale", 10000, 20-01-2021);
        
        c1.addOpportunity(o1);
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Opportunity.deleteAllRows").executeUpdate();
            em.createNamedQuery("Contact.deleteAllRows").executeUpdate();
                      
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    
    @Test 
    public void testAddContact(){
        ContactDTO contact = new ContactDTO(c1);
        ContactDTO result = facade.addContact(contact);
        assertEquals(contact.getName(), result.getName());
        
    }
    
    @Disabled
    @Test
    public void testAddOpportunity(){
        OpportunityDTO opportunity = new OpportunityDTO(o1);
        OpportunityDTO result = facade.addOpportunity(opportunity);
        assertEquals(opportunity.getName(), result.getName());
        
    }
    
    @Test 
    public void testGetContactById(){
        ContactDTO contact = new ContactDTO(c1);
        Contact result = facade.getContactById(c1.getId());
        assertEquals(contact.getId(), result.getId());
        
    }
    @Disabled
    @Test
    public void testUpdateContact() throws ContactNotFoundException{
        ContactDTO contact = new ContactDTO(c1);
        contact.setName("testName");
        Contact contact1 = facade.updateContact(contact.getId());
        assertEquals(contact.getName(), contact1.getName());
    }

}
