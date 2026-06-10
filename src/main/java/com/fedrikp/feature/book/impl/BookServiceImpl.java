package com.fedrikp.feature.book.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.fedrikp.dto.book.BookRequestDTO;
import com.fedrikp.dto.book.BookResponseDTO;
import com.fedrikp.entity.Book;
import com.fedrikp.entity.Category;
import com.fedrikp.exception.DuplicateResourceException;
import com.fedrikp.exception.ResourceNotFoundException;
import com.fedrikp.feature.book.BookRepository;
import com.fedrikp.feature.book.BookService;
import com.fedrikp.feature.category.CategoryRepository;

@Service
public class BookServiceImpl implements BookService{
	private final BookRepository bookRepository;
	private final CategoryRepository categoryRepository;
	
	public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
		this.bookRepository = bookRepository;
		this.categoryRepository= categoryRepository;
	}
	
	//function untuk convert entity ke dto
	private BookResponseDTO toResponse(Book book) {
        return new BookResponseDTO(
        		book.getId(),
        		book.getTitle(),
        		book.getAuthor(),
        		book.getIsbn(),
        		book.getPublishedDate(),
        		book.getCategory() != null ? book.getCategory().getId() : null,
        		book.getCategory() != null ? book.getCategory().getCategoryName() : null
        );
    }

	//function untuk menarik semua data book
	@Override
	public List<BookResponseDTO> getAll() {
	    List<Book> books = bookRepository.findAll();

	    return books.stream().map(book->toResponse(book)).toList();
	}

	//function untuk mengambil book berdasarkan id, jika tidak ada return ResourceNotFoundException
	@Override
	public BookResponseDTO getById(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(
						"Book not found with id: " + id 
				));
		
		return toResponse(book);
	}
	
	//function untuk membuat book
	@Override
	public BookResponseDTO create(BookRequestDTO request) {
		
		//jika id category tidak ada maka return ResourceNotFoundException
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException(
	            		"Category not found with id: " + request.getCategoryId()
	            ));
		
		//jika isbn tidak unique, maka return DuplicateResourceException
		if (bookRepository.existsByIsbn(request.getIsbn())) {
			throw new DuplicateResourceException("Book with isbn: "+request.getIsbn()+" already exists");
		}
		
		Book book = new Book();
		book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());
        book.setCategory(category);
        return toResponse(bookRepository.save(book));
		
	}

	//function untuk mengupdate keseluruhan book by id
	@Override
	public BookResponseDTO update(Long id, BookRequestDTO request) {
		
		//jika id book tidak ada maka return ResourceNotFoundException
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
	            		"Book not found with id: " + id
	            ));
	            
		//jika isbn ada tapi bukan milik sendiri maka return DuplicateResourceException
		bookRepository.findByIsbn(request.getIsbn())
			.ifPresent(existingBook -> {
				if (!existingBook.getId().equals(id)) {
					throw new DuplicateResourceException("Book with isbn: "+request.getIsbn()+" already used by another book");
					}
			});
	    
		//jika id category tidak ada maka return ResourceNotFoundException
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException(
	            		"Category not found with id: " + request.getCategoryId()
	            ));
		
		book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedDate(request.getPublishedDate());
        book.setCategory(category);
		
		
        return toResponse(bookRepository.save(book));
	}

	//function untuk mengupdate book secara parsial by id
	@Override
	public BookResponseDTO partialUpdate(Long id, BookRequestDTO request) {
		//jika id book tidak ada maka return ResourceNotFoundException
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
	            		"Book not found with id: " + id
	            ));
	            
		//jika isbn ada di request, maka di cek
		if (request.getIsbn()!=null) {
			//jika isbn ada tapi bukan milik sendiri maka return DuplicateResourceException
			bookRepository.findByIsbn(request.getIsbn())
			.ifPresent(existingBook -> {
				if (!existingBook.getId().equals(id)) {
					throw new DuplicateResourceException("Book with isbn: "+request.getIsbn()+" already used by another book");
					}
			});
		}
	    
		Category category = null;
		//jika category ada di request, maka di cek
		if (request.getCategoryId() != null) {
			//jika id category tidak ada maka return ResourceNotFoundException
			category = categoryRepository.findById(request.getCategoryId())
					.orElseThrow(() -> new ResourceNotFoundException(
		            		"Category not found with id: " + request.getCategoryId()
		            ));
		}
		
		//set ke book jika direquest
		if(request.getTitle()!=null) book.setTitle(request.getTitle());
		if(request.getAuthor()!=null) book.setAuthor(request.getAuthor());
		if(request.getIsbn()!=null) book.setIsbn(request.getIsbn());
		if(request.getPublishedDate()!=null) book.setPublishedDate(request.getPublishedDate());
		if(request.getCategoryId()!=null) book.setCategory(category);
		
        return toResponse(bookRepository.save(book));
	}

	//hapus book berdasarkan id
	@Override
	public void deleteById(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(
						"Book not found with id: " + id 
				));
		
		bookRepository.delete(book);
	}
	
}
