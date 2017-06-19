package com.test.springer.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {

	Notification findNotificationByBook(String book);

}
