package com.f1rst.bookapi.clients;

import com.f1rst.bookapi.dtos.BookDTO;
import com.f1rst.bookapi.dtos.OpenLibraryResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
public class OpenLibraryClient {
    Logger logger = LogManager.getLogger(OpenLibraryClient.class);

    @Value("${api.openLibrary.url.books}")
    String baseUrlOpenLibrary;

    final RestClient restClient;

    public OpenLibraryClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public List<BookDTO> getAllBooks(String q, int page, int limit) {
        String url = String.format("%s/search.json?q=%s&page=%d&limit=%d", baseUrlOpenLibrary, q, page, limit);
        logger.debug("Request URL: {} ", url);

        try {
            OpenLibraryResponseDTO response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(OpenLibraryResponseDTO.class);

            return response != null ? response.getDocs() : List.of();
        } catch (RestClientException e) {
            logger.error("Error Request RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }
    }

    public List<BookDTO> getBooksByAuthor(String author, int page, int limit) {
        String url = String.format("%s/search.json?author=%s&page=%d&limit=%d", baseUrlOpenLibrary, author, page, limit);
        logger.debug("Request URL: {} ", url);

        try {
            OpenLibraryResponseDTO response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(OpenLibraryResponseDTO.class);

            return response != null ? response.getDocs() : List.of();
        } catch (RestClientException e) {
            logger.error("Error Request RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }
    }

    public List<BookDTO> getBookById(String id) {
        String url = String.format("%s/books/%s", baseUrlOpenLibrary, id);
        logger.debug("Request URL: {} ", url);

        try {
            OpenLibraryResponseDTO response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(OpenLibraryResponseDTO.class);

            return response != null ? response.getDocs() : List.of();
        } catch (RestClientException e) {
            logger.error("Error Request RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }
    }

    public List<BookDTO> getBooksByGender(String gender, int limit, int page) {
        String url = String.format("%s/subjects/%s.json?limit=%d&offset=%d", baseUrlOpenLibrary, gender, limit, page);
        logger.debug("Request URL: {} ", url);

        try {
            OpenLibraryResponseDTO response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(OpenLibraryResponseDTO.class);

            return response != null ? response.getDocs() : List.of();
        } catch (RestClientException e) {
            logger.error("Error Request RestClient with cause: {} ", e.getMessage());
            throw new RuntimeException("Error Request RestClient", e);
        }
    }
}
