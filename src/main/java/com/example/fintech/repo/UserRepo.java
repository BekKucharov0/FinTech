package com.example.fintech.repo;

import com.example.fintech.enums.UserStatus;
import com.example.fintech.module.Card;
import com.example.fintech.module.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserRepo {

    private static final Map<UUID, User> userRepo = new HashMap<>();

    public User findById(UUID userId) {
        return userRepo.get(userId);
    }

    public List<User> findAll() {
        return userRepo.values().stream().toList();
    }

    public User findByAccountId(UUID accountId) {
        List<User> allUsers = userRepo.values().stream().toList();
        for (User user : allUsers) {
            List<Card> cards = user.getCards().stream()
                    .filter(card -> Objects.equals(accountId, card.getId()))
                    .toList();
            if (!CollectionUtils.isEmpty(cards)) {
                return user;
            }
        }
        return null;
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.values().stream()
                .filter(user -> Objects.equals(email, user.getEmail()))
                .findFirst();
    }

    public User save(User user) {
        UUID userId = user.getId();
        if (userId == null) {
            userId = UUID.randomUUID();
            user.setId(userId);
        }
        userRepo.put(userId, user);
        return user;
    }

    public User delete(User user) {
        UUID userId = user.getId();
        User actualUser = userRepo.get(userId);
        actualUser.setStatus(UserStatus.DELETED);
        return actualUser;
    }
}
