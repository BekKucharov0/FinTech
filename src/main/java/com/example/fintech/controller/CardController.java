package com.example.fintech.controller;

import com.example.fintech.dto.card.CardDTO;
import com.example.fintech.dto.card.CreateCardDTO;
import com.example.fintech.dto.card.UpdateCardDTO;
import com.example.fintech.module.Card;
import com.example.fintech.service.CardService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
@Validated
public class CardController {

    private final CardService cardService;
    private final ModelMapper modelMapper;

    @GetMapping("/{cardId}")
    public CardDTO getCardById(@PathVariable(value = "cardId") UUID cardId) {

        Card card = cardService.getCardById(cardId);
        return modelMapper.map(card, CardDTO.class);
    }

    @GetMapping("/all")
    public List<CardDTO> getAllCards() {

        return cardService.getAllCards();
    }

    @PostMapping
    public CardDTO createCard(@RequestBody CreateCardDTO createCardDTO) {

        return cardService.createCard(createCardDTO);
    }

    @PutMapping("/{cardId}")
    public CardDTO updateCardDetails(@PathVariable(value = "cardId") UUID cardId,
                                     @RequestBody UpdateCardDTO updateCardDTO) {

        return cardService.updateCardDetails(cardId, updateCardDTO);
    }

    @DeleteMapping("/{cardId}")
    public CardDTO deleteCard(@PathVariable(value = "cardId") UUID cardId) {

        return cardService.deleteCard(cardId);
    }
}
