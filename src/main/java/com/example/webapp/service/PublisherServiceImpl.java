package com.example.webapp.service;

import java.util.List;
import java.util.Optional;

import com.example.webapp.model.Publisher;
import com.example.webapp.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @Override
    public void savePublisher(Publisher publisher) {
        this.publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisherbyId(long id) {
        Optional<Publisher> optional = this.publisherRepository.findById(id);
        Publisher publisher = null;
        if(optional.isPresent()){
            publisher = optional.get();
        }else{
            throw new RuntimeException("Publisher not found with id :: "+id);
        }
        return publisher;    
    }

    @Override
    public void deletePublisherById(long id) {
        this.publisherRepository.deleteById(id);
    }

}
