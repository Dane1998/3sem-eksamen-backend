
package entities;

import dto.ContactDTO;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Dane
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Contact.deleteAllRows", query = "DELETE from Contact"),
    @NamedQuery(name = "Contact.getAllContacts", query = "SELECT c from Contact c"),
    
})
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String company;
    private String jobtitle;
    private String phone;

    public Contact(String name, String email, String company, String jobtitle, String phone) {
        this.name = name;
        this.email = email;
        this.company = company;
        this.jobtitle = jobtitle;
        this.phone = phone;
    }
    
    public Contact(ContactDTO dto){
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.company = dto.getCompany();
        this.jobtitle = dto.getJobtitle();
        this.phone = dto.getPhone();
    }

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  
   
}
