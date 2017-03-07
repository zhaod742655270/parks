package com.hbyd.parks.guava;

import com.google.common.base.Objects;

/**
 * Created by allbutone on 2014/10/29.
 */
public class Book implements Comparable<Book>{
    private Person author;
    private String title;
    private String publisher;
    private double price;
    private String isbn;//ISBN 编号

    public Book() {
    }

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public Book(Person author, String title, String publisher, double price, String isbn) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.price = price;
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, publisher);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("author", author)
                .add("title", title)
                .add("publisher", publisher)
                .add("price", price).toString();
    }

    @Override
    public int compareTo(Book o) {

//      1. 自己实现的 compareTo() 方法
        int result = this.title.compareTo(o.getTitle());

        if(result != 0){
            return result;
        }

        //如果 title 相同
        return Double.compare(this.price, o.getPrice());

//      2. 使用 Guava 实现的 compareTo() 方法
//        the ComparisonChain will stop comparisons with the first non-zero
//        result, the only way a zero will be returned is if all comparisons
//        result in zero.
//        return ComparisonChain.start()
//                .compare(this.getTitle(), o.getTitle())
//                .compare(this.getPrice(), o.getPrice())
//                .result();
    }
}

