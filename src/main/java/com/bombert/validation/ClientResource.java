package com.bombert.validation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.stream.Collectors;

public class ClientResource {
    private final ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/api/clients")
    @ResponseStatus(HttpStatus.CREATED)
    void saveClient(@Valid @RequestBody ClientDto client) {
        clientService.register(client);
    }

    // Metoda saveClient() to punkt krańcowy, który obsługuje żądania POST wysyłane pod adres /api/clients,
    // co odpowiada zapisaniu nowego klienta w bazie danych. Wraz z żądaniem powinien być wysłany obiekt
    // JSON z informacjami o kliencie takimi jak: imię, nazwisko, email i wiek. Chcemy wykonać walidację
    // tego obiektu jeszcze przed przekazaniem go do warstwy usług, dlatego do parametru metody dodajemy
    // dodatkową adnotację @Valid. Jeżeli obiekt będzie poprawny i zostanie zapisany w bazie,
    // to wysłana będzie odpowiedź z kodem 201 Created.

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    //
    //  Z obiektu wyjątku MethodArgumentNotValidException pobieramy informacje o złamanych ograniczeniach,
    //  które reprezentowane są jako obiekty typu FieldError. Po ich przekształceniu powstanie mapa,
    //  w której kluczami będą nazwy pól, a wartościami komunikaty ze złamanymi ograniczeniami.
}
