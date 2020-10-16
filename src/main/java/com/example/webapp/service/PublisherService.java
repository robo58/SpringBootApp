package com.example.webapp.service;

import java.util.List;

import com.example.webapp.model.Publisher;

public interface PublisherService {
    List<Publisher> getAllPublishers();

    void savePublisher(Publisher publisher);
    
    Publisher getPublisherbyId(long id);

    void deletePublisherById(long id);
}
