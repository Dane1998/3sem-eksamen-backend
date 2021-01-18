
package facades;

import dto.ContactDTO;
import dto.ContactsDTO;
import entities.Contact;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Dane
 */
public class ContactFacade {
    
    private static ContactFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private ContactFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static ContactFacade getContactFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ContactFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public ContactDTO addContact(ContactDTO contactDTO){
        EntityManager em = getEntityManager();
        Contact contact = new Contact(contactDTO);
        
        try{
            em.getTransaction().begin();
            em.persist(contact);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new ContactDTO(contact);
    }
    
    public ContactsDTO getAllContacts(){
        EntityManager em = getEntityManager();
        try{
            return new ContactsDTO(em.createNamedQuery("Contact.getAllContacts").getResultList());
        }finally{
            em.close();
        }
    }
    
    public Contact getContactById(int id ){
        EntityManager em = getEntityManager();        
        try{
            em.getTransaction().begin();
            TypedQuery<Contact> query = em.createQuery("SELECT c FROM Contact c WHERE c.id = :id", Contact.class);
            query.setParameter("id", id);
            Contact contact = query.getSingleResult();
            return contact;
            
        }finally{
            em.close();
        }
    }
    
    public ContactDTO deleteContact (int id){
        EntityManager em = getEntityManager();
        Contact contact = em.find(Contact.class, id);
        try{
            em.getTransaction().begin();
            em.remove(contact);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new ContactDTO(contact);
    }
    
    public ContactDTO updateContact (int id){
        EntityManager em = getEntityManager();
        Contact contact = em.find(Contact.class, id);
        try{
            em.getTransaction().begin();
            em.merge(contact);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        return new ContactDTO(contact);
    }
}
