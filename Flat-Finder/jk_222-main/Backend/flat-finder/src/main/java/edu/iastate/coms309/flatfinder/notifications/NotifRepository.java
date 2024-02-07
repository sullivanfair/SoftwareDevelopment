package edu.iastate.coms309.flatfinder.notifications;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifRepository extends JpaRepository<Notification, Long> {
}