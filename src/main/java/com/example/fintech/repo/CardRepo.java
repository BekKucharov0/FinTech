package com.example.fintech.repo;

import com.example.fintech.enums.CardStatus;
import com.example.fintech.module.Card;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CardRepo {

    private static final Map<UUID, Card> cardRepo = new HashMap<>();

    public Card findById(UUID cardId) {
        return cardRepo.get(cardId);
    }

    public List<Card> findAll() {
        return cardRepo.values().stream().toList();
    }

    public List<Card> findAllByUserId(UUID userId) {
        return cardRepo.values().stream()
                .filter(card -> Objects.equals(userId, card.getId()))
                .toList();
    }

    public Card save(Card card) {
        UUID cardId = card.getId();
        if (cardId == null) {
            cardId = UUID.randomUUID();
            card.setId(cardId);
        }
        cardRepo.put(cardId, card);
        return card;
    }

    public Card delete(Card card) {
        UUID cardId = card.getId();
        Card actualCard = cardRepo.get(cardId);
        actualCard.setStatus(CardStatus.DELETED);
        return actualCard;
    }
}
