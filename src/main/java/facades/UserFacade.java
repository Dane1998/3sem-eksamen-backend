package facades;

import entities.Role;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;


public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em; 
        try {
            em = emf.createEntityManager();
        } catch (NullPointerException e){
        emf=EMF_Creator.createEntityManagerFactory();
        em=emf.createEntityManager();
        }
            User user;
            try {
                user = em.find(User.class, username);
                if (user == null || !user.verifyPassword(password)) {
                    throw new AuthenticationException("Invalid user name or password");
                }
            } finally {
                em.close();
            }
            return user;
        }

    public User addUser(String name, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, name);
            if (user != null) {
                throw new AuthenticationException("Username already exist, try different one");
            } else {
                user = new User(name, password);
                user.addRole(em.find(Role.class, "user"));
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        return user;
    }
    
    public User deleteUser(String username) {
        EntityManager em = getEntityManager();
        User u = em.find(User.class, username);
        if (u != null) {
            try {
                em.getTransaction().begin();
                em.remove(u);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return u;
    }
    
    public User edit(User username) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(username);
            em.getTransaction().commit();
            return username;
        } finally {
            em.close();
        }
    }
    
    
    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> allUsers = em.createQuery("SELECT u.userName from User u", User.class)
            .getResultList();
            return allUsers;
        } finally {
            em.close();
        }

    }
    
   
}