package edu.iastate.coms309.flatfinder.complaints;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for database operations related to the Complaint entity.
 */
public interface ComplaintRepository extends JpaRepository<Complaint, Integer>
{
}
