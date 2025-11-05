package com.apress.my.retro.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {
    private UUID id;

    @NotBlank
    private String comment;

    @NotNull
    private CardType cardType;

    private UUID retroBoardId;
}

/* --> USED TO BE FIRST VERSION Of MY-RETRO
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
*/ // Keep as comment for quick referencing