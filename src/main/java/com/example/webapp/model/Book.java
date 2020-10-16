package com.example.webapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="first_publish")
    private int firstPublish;

    @Column(name="description")
    private String description;

    @Column(name="cover_image",nullable = true)
    private String coverImage;

    @ManyToOne()
    @JoinColumn(name="ab_fid")
    private Author author;

    @ManyToOne()
    @JoinColumn(name="pb_fid")
    private Publisher publisher;

    @ManyToOne()
    @JoinColumn(name="cb_fid")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "BR_fid",referencedColumnName = "id")
    List<Review> reviews = new ArrayList<>();

    public Book() {
        this.coverImage = "default.png";
    }

    public Book(String title, int firstPublish, String description, Author author, Category category, Publisher publisher){
        super();
        this.title = title;
        this.firstPublish = firstPublish;
        this.description = description;
        this.author=author;
        this.category = category;
        this.publisher = publisher;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getFirstPublish() {
        return firstPublish;
    }
    public void setFirstPublish(int firstPublish) {
        this.firstPublish = firstPublish;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Publisher getPublisher() {
        return publisher;
    }
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    public String getCoverImage() {
        return coverImage;
    }
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


}
