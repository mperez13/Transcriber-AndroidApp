package com.transcriber.com.transcriber.data;

public class ToDoItem {
    private String title;
    private String text;
    private String category;



    public ToDoItem(String title, String text, String category) {
        this.title = title;
        this.text = text;
        this.category = category;

    }
    // getters and setters for the new attributes categories and done
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}