package com.fedrikp.dto.book;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequestDTO {
	
	@NotBlank(message = "Title is required")
	private String title;

	@NotBlank(message = "Author is required")
	private String author;
	
	@NotBlank(message = "ISBN is required")
	private String isbn;

	@NotNull(message = "Published date is required")
	private LocalDate publishedDate;
	
	@NotNull(message = "Category ID is required")
	private Long categoryId;
	
	public BookRequestDTO() {}
	
	public BookRequestDTO(String title, String author, String isbn, LocalDate publishedDate, Long categoryId) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.publishedDate = publishedDate;
		this.categoryId = categoryId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public LocalDate getPublishedDate() {
		return publishedDate;
	}
	
	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
}
