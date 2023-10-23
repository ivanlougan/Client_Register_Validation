package com.bombert.validation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;

    public ClientService(ClientRepository clientRepository, ClientDtoMapper clientDtoMapper) {
        this.clientRepository = clientRepository;
        this.clientDtoMapper = clientDtoMapper;
    }


    public void register(ClientDto clientDto) {
        Client client = clientDtoMapper.map(clientDto);
        String uniqueUsername = generateUsername(client);
        client.setUsername(uniqueUsername);
        clientRepository.save(client);
    }

    private String generateUsername(Client client) {
        String firstNameLetters = client.getFirstName().substring(0, 2).toUpperCase();
        String lastNameLetters = client.getLastName().substring(0, 2).toUpperCase();
        String usernameBeginning = firstNameLetters + lastNameLetters;
        String username;
        do {
            username = usernameBeginning + randomCode(3);
        } while (clientRepository.existsByUsername(username));
        return username;
    }

    private String randomCode(int length) {
        List<String> digits = Arrays.asList("0123456789".split(""));
        Collections.shuffle(digits);
        return digits.subList(0, length).stream().reduce(String::concat).get();
    }

    // Metoda register() odpowiada za rejestrację klienta. Nazwa użytkownika będzie generowana
    // w metodzie generateUsername(). Tworzymy ją z połączenia dwóch pierwszych liter imienia,
    // dwóch pierwszych liter nazwiska i losowego ciągu 3 cyfr. Jeżeli trafimy na nazwę użytkownika,
    // która jest już zajęta, to generujemy ją ponownie aż do skutku.


}
