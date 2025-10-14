package com.apress.my.retro.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Card {

    private UUID id;

    @NotBlank(message = "The comment is mandatory.")
    @NotNull
    private String comment;

    @NotNull(message = "Card type: HAPPY|MEH|SAD is mandatory.")
    // can have a string and set with @Pattern(regexp = "^(SAD|HAPPY|MEH)$", message = "CardType mandatory...")
    private CardType cardType;

    public UUID getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public CardType getCardType() {
        return cardType;
    }
}
