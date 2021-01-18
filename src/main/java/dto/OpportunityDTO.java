
package dto;

import entities.Opportunity;
import java.util.Date;

/**
 *
 * @author Dane
 */
public class OpportunityDTO {
    
    private int id;
    private String name;
    private int amount;
    private int closeDate;

    public OpportunityDTO(Opportunity opportunity) {
        this.name = opportunity.getName();
        this.amount = opportunity.getAmount();
        this.closeDate = opportunity.getCloseDate();
    }
   
    public OpportunityDTO(String name, int amount, int closeDate) {
        this.name = name;
        this.amount = amount;
        this.closeDate = closeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    
    
    
    
    
}
