package com.cabapplication.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cabapplication.user.entity.Notification;

public interface NotificationRepository extends MongoRepository<Notification ,Long> {

}
