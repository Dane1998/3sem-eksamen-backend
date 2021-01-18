
package dto;

import entities.Contact;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dane
 */
public class ContactsDTO {
    
    private List<ContactDTO> all = new ArrayList<>();
    
    public ContactsDTO(List<Contact> contactEntities) {
        contactEntities.forEach((c) -> {
            all.add(new ContactDTO(c));
        });
    }

    public ContactsDTO() {
    }

    public List<ContactDTO> getAll() {
        return all;
    }
    
    
}
