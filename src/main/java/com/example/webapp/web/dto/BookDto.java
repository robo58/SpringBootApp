package com.example.webapp.web.dto;

public class BookDto {

    private Long id;
    private String title;
    private String description;
    private int firstPublish;
    private String authorName;
    private String categoryName;
    private String publisherName;
    private String coverImage;

    public BookDto(Long id, String title, String description, int firstPublish, String authorName, String categoryName, String publisherName, String coverImage){
        this.id=id;
        this.title=title;
        this.description=description;
        this.firstPublish=firstPublish;
        this.authorName=authorName;
        this.categoryName=categoryName;
        this.publisherName=publisherName;
        this.coverImage = coverImage;
    }

    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
    public String getPublisherName() {
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCoverImage() {
        return coverImage;
    }
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
    
}
