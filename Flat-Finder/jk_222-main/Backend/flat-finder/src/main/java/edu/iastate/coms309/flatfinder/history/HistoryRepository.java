package edu.iastate.coms309.flatfinder.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<History> findByTenantUserName(String userName); // Updated method name

    List<History> findByTenant2UserName(String userName);

    List<History> findByListingListingName(String listingName);
}