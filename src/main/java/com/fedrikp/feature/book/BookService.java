package com.fedrikp.feature.book;

import java.util.List;

import com.fedrikp.dto.book.BookRequestDTO;
import com.fedrikp.dto.book.BookResponseDTO;

public interface BookService {
	List<BookResponseDTO> getAll();
	BookResponseDTO getById(Long id);
	BookResponseDTO create(BookRequestDTO request);
	BookResponseDTO update(Long id, BookRequestDTO request);
	BookResponseDTO partialUpdate(Long id, BookRequestDTO request);
	void deleteById(Long id);
}
