
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dane
 */
@Entity
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String comment;
    
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Opportunity opportunity;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private TaskStatus taskStatus;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private TaskType taskType;

    public Task(String title, String comment, Date dueDate) {
        this.title = title;
        this.comment = comment;
        this.dueDate = dueDate;
    }

    public Task() {
    }
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
}
