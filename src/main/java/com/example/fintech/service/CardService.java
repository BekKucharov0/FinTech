package com.example.fintech.service;

import com.example.fintech.dto.card.CardDTO;
import com.example.fintech.dto.card.CreateCardDTO;
import com.example.fintech.dto.card.UpdateCardDTO;
import com.example.fintech.enums.CardStatus;
import com.example.fintech.exception.CustomException;
import com.example.fintech.module.Card;
import com.example.fintech.module.User;
import com.example.fintech.repo.CardRepo;
import com.example.fintech.repo.UserRepo;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final ModelMapper modelMapper;
    private final CardRepo cardRepo;
    private final UserRepo userRepo;

    public Card getCardById(UUID cardId) {
        Card card = cardRepo.findById(cardId);
        if (card == null) {
            throw new CustomException("Could not find a card", HttpStatus.BAD_REQUEST);
        }
        return card;
    }

    public List<CardDTO> getAllCards() {
        List<Card> allCards = cardRepo.findAll();
        return allCards.stream()
                .map(card -> modelMapper.map(card, CardDTO.class))
                .toList();
    }

    public CardDTO createCard(CreateCardDTO createDTO) {

        UUID ownerUserId = createDTO.getOwnerUserId();
        User ownerUser = userRepo.findById(ownerUserId);
        if (ownerUser == null) {
            throw new CustomException("Could not find associated user", HttpStatus.BAD_REQUEST);
        }

        if (createDTO.getExpiryDate().isBefore(LocalDate.now())) {
            throw new CustomException("Your card already expired", HttpStatus.BAD_REQUEST);
        }

        boolean validatedByLuhn = validateByLuhn(createDTO.getCardNumber());
        if (!validatedByLuhn) {
            throw new CustomException("Could not validate your card number", HttpStatus.BAD_REQUEST);
        }

        Card newCard = Card.builder()
                .expiryDate(createDTO.getExpiryDate())
                .cvv(createDTO.getCvv())
                .cardNumber(createDTO.getCardNumber())
                .status(CardStatus.ACTIVE)
                .ownerUserId(ownerUser.getId())
                .ownerUser(ownerUser)
                .build();
        Card card = cardRepo.save(newCard);
        return modelMapper.map(card, CardDTO.class);
    }

    private boolean validateByLuhn(String cardNumber) {

        int cardNumberLength = cardNumber.length();
        int sum = 0;
        boolean isEvenDigit = false;

        for (int i = cardNumberLength - 1; i >= 0; i--) {

            int digit = cardNumber.charAt(i) - '0';
            if (isEvenDigit) {
                // double each even digit
                digit = digit * 2;
            }

            // to handle cases when 2-digit number
            sum += digit / 10;
            sum += digit % 10;

            isEvenDigit = !isEvenDigit;
        }

        return ((sum & 10) == 0);

    }

    public CardDTO updateCardDetails(UUID cardId, UpdateCardDTO updateDTO) {

        Card cardToUpdate = cardRepo.findById(cardId);
        if (cardToUpdate == null) {
            throw new CustomException("Could not find card", HttpStatus.BAD_REQUEST);
        }

        User ownerUser = cardToUpdate.getOwnerUser();
        if (updateDTO.getOwnerUserId() != null
                && !Objects.equals(cardToUpdate.getOwnerUserId(), updateDTO.getOwnerUserId())) {

            ownerUser = userRepo.findById(updateDTO.getOwnerUserId());
            if (ownerUser == null) {
                throw new CustomException("Could not find associated user", HttpStatus.BAD_REQUEST);
            }
        }

        if (updateDTO.getExpiryDate() != null
                && !Objects.equals(cardToUpdate.getExpiryDate(), updateDTO.getExpiryDate())) {

            if (updateDTO.getExpiryDate().isAfter(LocalDate.now())) {
                throw new CustomException("Your card already expired", HttpStatus.BAD_REQUEST);
            }
        }

        if (updateDTO.getCardNumber() != null
                && !Objects.equals(cardToUpdate.getCardNumber(), updateDTO.getCardNumber())) {

            boolean validatedByLuhn = validateByLuhn(updateDTO.getCardNumber());
            if (!validatedByLuhn) {
                throw new CustomException("Could not validate your card number", HttpStatus.BAD_REQUEST);
            }
        }

        cardToUpdate.setExpiryDate(updateDTO.getExpiryDate() != null ? updateDTO.getExpiryDate() : cardToUpdate.getExpiryDate());
        cardToUpdate.setCvv(updateDTO.getCvv() != null ? updateDTO.getCvv() : cardToUpdate.getCvv());
        cardToUpdate.setCardNumber(updateDTO.getCardNumber() != null ? updateDTO.getCardNumber() : cardToUpdate.getCardNumber());
        cardToUpdate.setOwnerUserId(ownerUser.getId());
        cardToUpdate.setOwnerUser(ownerUser);
        Card updatedCard = cardRepo.save(cardToUpdate);
        return modelMapper.map(updatedCard, CardDTO.class);
    }

    public CardDTO deleteCard(UUID cardId) {

        Card cardToDelete = cardRepo.findById(cardId);
        if (cardToDelete == null) {
            throw new CustomException("Could not find card", HttpStatus.BAD_REQUEST);
        }

        Card deletedCard = cardRepo.delete(cardToDelete);
        return modelMapper.map(deletedCard, CardDTO.class);
    }
}
