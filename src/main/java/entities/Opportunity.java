
package entities;

import dto.OpportunityDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dane
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Opportunity.deleteAllRows", query = "DELETE from Opportunity")  
})
public class Opportunity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int amount;
    
    
    
    private int closeDate;
    
    @ManyToOne
    private Contact contact;
    

    public Opportunity(OpportunityDTO dto){
        this.name = dto.getName();
        this.amount = dto.getAmount();
        this.closeDate = dto.getCloseDate();
                
    }
    
    public Opportunity(String name, int amount, int closeDate) {
        this.name = name;
        this.amount = amount;
        this.closeDate = closeDate;
    }

    public Opportunity() {
    }
    
    public void addContact (Contact contact){
        this.contact = contact;
        if(contact != null) {
            contact.getOpportunity().add(this);
        }
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(int closeDate) {
        this.closeDate = closeDate;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
