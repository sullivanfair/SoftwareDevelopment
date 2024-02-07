package edu.iastate.coms309.flatfinder.complaints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService
{
    @Autowired
    ComplaintRepository complaintRepository;

    public Complaint save(Complaint complaint){return complaintRepository.save(complaint);}

    public List<Complaint> findAll(){return complaintRepository.findAll();}

    public Optional<Complaint> findById(Integer id){return complaintRepository.findById(id);}

    public void deleteById(Integer id){complaintRepository.deleteById(id);}

    public boolean existsById(Integer id){return complaintRepository.existsById(id);}
}
