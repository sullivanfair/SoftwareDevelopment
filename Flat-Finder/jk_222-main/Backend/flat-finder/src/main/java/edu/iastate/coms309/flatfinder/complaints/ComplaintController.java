package edu.iastate.coms309.flatfinder.complaints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController
{
    @Autowired
    ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint)
    {
        complaintService.save(complaint);
        return ResponseEntity.ok(complaint);
    }

    @GetMapping("/landlords/{id}")
    public List<Complaint> getLandlordComplaints(@PathVariable int id)
    {
        List<Complaint> complaints = complaintService.findAll();
        ArrayList<Complaint> landlordComplaints = new ArrayList<>();

        for(Complaint complaint : complaints)
        {
            if(complaint.getReceivingLandlord().getUserId() == id)
            {
                landlordComplaints.add(complaint);
            }
        }

        return landlordComplaints;
    }

    @GetMapping("/tenants/{id}")
    public List<Complaint> getTenantComplaints(@PathVariable int id)
    {
        List<Complaint> complaints = complaintService.findAll();
        ArrayList<Complaint> tenantComplaints = new ArrayList<>();

        for(Complaint complaint : complaints)
        {
            if(complaint.getSendingTenant().getUserId() == id)
            {
                tenantComplaints.add(complaint);
            }
        }

        return tenantComplaints;
    }

    @PutMapping("/{complaintId}")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable int complaintId, @RequestBody Complaint newComplaint)
    {
        if(!complaintService.existsById(complaintId))
        {
            return ResponseEntity.notFound().build();
        }

        newComplaint.setComplaintId(complaintId);
        return ResponseEntity.ok(complaintService.save(newComplaint));
    }

    @PutMapping("/{complaintId}/status/{newStatus}")
    public ResponseEntity<Complaint> updateComplaintStatus(@PathVariable int complaintId, @PathVariable String newStatus)
    {
        return updateComplaintField(complaintId, complaint -> complaint.setStatus(newStatus));
    }

    private ResponseEntity<Complaint> updateComplaintField(int complaintId, java.util.function.Consumer<Complaint> fieldUpdater)
    {
        return complaintService.findById(complaintId)
                .map(complaint -> {
                    fieldUpdater.accept(complaint);
                    complaintService.save(complaint);
                    return ResponseEntity.ok(complaint);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable int id)
    {
        complaintService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
