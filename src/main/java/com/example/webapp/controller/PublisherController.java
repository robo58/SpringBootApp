package com.example.webapp.controller;

import com.example.webapp.model.Publisher;
import com.example.webapp.service.PublisherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/publishers")
    public String publisherIndex(Model model){
        model.addAttribute("publishers", this.publisherService.getAllPublishers());
        return "publishers";
    }

    @GetMapping("/newPublisher")
    public String showNewPublisherForm(Model model)
    {   
        //create model to bind data
        Publisher publisher = new Publisher();
        model.addAttribute("publisher", publisher);
        return "new_publisher";
    }

    @PostMapping("/savePublisher")
    public String savePublisher(@ModelAttribute("publisher") Publisher publisher)
    {
        //save publisher to database
        publisherService.savePublisher(publisher);
        return "redirect:/publishers";
    }

    @GetMapping("/updatePublisher/{id}")
    public String updatePublisher(@PathVariable(value="id") long id,Model model)
    {

        //get publisher from service
        Publisher publisher = this.publisherService.getPublisherbyId(id);

        //set user as model for population of the update form
        model.addAttribute("publisher", publisher);
        return "new_publisher";
    }

    @GetMapping("/deletePublisher/{id}")
    public String deletePublisher(@PathVariable(value="id") long id)
    {

        // call delete publisher by id
        this.publisherService.deletePublisherById(id);
        
        return "redirect:/publishers";
    }
    
}
