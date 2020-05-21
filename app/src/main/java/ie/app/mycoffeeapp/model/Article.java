package ie.app.mycoffeeapp.model;

import androidx.annotation.Nullable;

import java.util.Date;

public class Article {
    private String article_id;
    private String detail;
    private String image_link;
    private String name;
    private String createdDate;
    private String topic;

    public Article(String article_id,  String detail, String image_link, String name, String createdDate, String topic){
        this.article_id = article_id;
        this.detail = detail;
        this.image_link = image_link;
        this.name = name;
        this.createdDate = createdDate;
        this.topic = topic;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Article aObj = (Article)obj;
        return this.article_id.equalsIgnoreCase(aObj.article_id);
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Article(){
    }

    public void print_info(){
        System.out.println("ID: " + article_id);
        System.out.println("Detail: " + detail);
        System.out.println("Image Link: " + image_link);
        System.out.println("Name: " + name);
        System.out.println("Created date: " + createdDate);
        System.out.println("Topic: " + topic);

    }
}

