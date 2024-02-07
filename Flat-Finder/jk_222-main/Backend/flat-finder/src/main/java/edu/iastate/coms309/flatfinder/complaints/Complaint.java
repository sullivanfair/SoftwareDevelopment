package edu.iastate.coms309.flatfinder.complaints;

import edu.iastate.coms309.flatfinder.users.landlord.Landlord;
import edu.iastate.coms309.flatfinder.users.tenant.Tenant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "complaints")
@Getter
@Setter
public class Complaint
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer complaintId;

    /**
     * Content of the complaint
     */
    private String content;

    /**
     * Date that the complaint was sent
     */
    private Date date;

    /**
     * Shows whether the complaint is resolved or not
     */
    private String status;

    /**
     * The landlord to which the complaint is addressed
     */
    @ManyToOne
    private Landlord receivingLandlord;

    /**
     * The tenant that has sent the complaint
     */
    @ManyToOne
    private Tenant sendingTenant;
}
