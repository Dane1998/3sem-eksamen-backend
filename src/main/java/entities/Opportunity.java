
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dane
 */
@Entity
public class Opportunity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int amount;
    
    @Temporal(TemporalType.DATE)
    private Date closeDate;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Contact contact;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private OpportunityStatus opportunityStatus;
    
    @OneToMany(mappedBy = "opportunity", cascade = CascadeType.ALL)
    private List<Task> task;

    public Opportunity(String name, int amount, Date closeDate) {
        this.name = name;
        this.amount = amount;
        this.closeDate = closeDate;
    }

    public Opportunity() {
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

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
}
