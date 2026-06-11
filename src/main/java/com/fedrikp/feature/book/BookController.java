package com.fedrikp.feature.book;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fedrikp.dto.book.BookRequestDTO;
import com.fedrikp.dto.book.BookResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GetMapping
	public ResponseEntity<List<BookResponseDTO>> getAll() {
		return ResponseEntity.ok(bookService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookResponseDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.getById(id));
	}

	@PostMapping
	public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookResponseDTO> update(@Valid @PathVariable Long id, @RequestBody BookRequestDTO request) {
		return ResponseEntity.ok(bookService.update(id, request));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<BookResponseDTO> partialUpdate(@PathVariable Long id, @RequestBody BookRequestDTO request) {
		return ResponseEntity.ok(bookService.partialUpdate(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		bookService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}