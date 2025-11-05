package com.apress.my.retro.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RetroBoard {
    private UUID id;

    @NotBlank(message = "Name must be provided")
    private String name;

    @Singular
    Map<UUID, Card> cards = new HashMap<>();
}

/* --> FIRST VERSION OF MY-RETRO RetroBoard class
@Builder // helps create immutable objects
@Data // generated getters, setters, toString(), hashCode(), equals(Object o)
public class RetroBoard {
    private UUID id;
    @NotNull // field can't be null
    @NotBlank(message = "A name must be provided") // field can't be blank
    private String name;

    @Singular // allows good flow of builder pattern through creating a method that behaves like:
    // first call of method card(): cards = new List<Card>(given_card)
    // second call of card(): cards.add(other_given_card)
    // these calls should be done at object creation, bc after the list will be final
    private List<Card> cards;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }
}
*/