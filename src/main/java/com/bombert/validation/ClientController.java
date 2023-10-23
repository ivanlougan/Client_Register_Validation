package com.bombert.validation;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    String home(Model model) {
        model.addAttribute("client", new ClientDto());
        return "index";
    }

    @PostMapping("/register")
    String register(@Valid @ModelAttribute("client") ClientDto clientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        } else {
            clientService.register(clientDto);
            return "redirect:success";
        }
    }

    @GetMapping("/success")
    String success() {
        return "success";
    }

    // Metoda home() odpowiada za wyświetlenie strony głównej. Formularz, który będzie się na niej znajdował,
    // powinien być wysłany pod adres /register metodą POST. Za obsługę tego żądania odpowiada metoda register().
    // Jeżeli dane w formularzu będą poprawne, to przekierujemy użytkownika pod adres /success,
    // natomiast jeżeli będą błędne, to wyświetlimy mu formularz ponownie z podświetlonymi na czerwono błędami.
    // Metoda success() obsługuje stronę docelową z potwierdzeniem rejestracji.
}
