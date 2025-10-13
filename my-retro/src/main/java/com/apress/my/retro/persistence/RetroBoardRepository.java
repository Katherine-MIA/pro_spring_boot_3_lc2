package com.apress.my.retro.persistence;

import com.apress.my.retro.board.Card;
import com.apress.my.retro.board.CardType;
import com.apress.my.retro.board.RetroBoard;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@SuppressWarnings(value = "unused")
@Repository // Facilitates injection into other classes of the instance of this object
public class RetroBoardRepository implements com.apress.my.retro.persistence.Repository<RetroBoard, UUID> {
    // Clearly whoever wrote this book doesn't use Sonar (omg it made a yellow stripe)
    // This is only for simulating a persistence normally it would be connected to a DB
    private Map<UUID, RetroBoard> retroBoardMap = new HashMap<>(){{
        put(UUID.fromString("9DC9B71B-A07E-418B-B972-40225449AFF2"),
                RetroBoard.builder()
                        .id(UUID.fromString("9DC9B71B-A07E-418B-B972-40225449AFF2"))
                        .name("Spring Boot 3.0 Meeting")
                        .card(Card.builder()
                                .id(UUID.fromString("011EF086-7645-4534-9512-B9BC4CCFB688"))
                                .comment("Example Projects")
                                .cardType(CardType.HAPPY)
                                .build())
                        .card(Card.builder()
                                .id(UUID.fromString("BB2A80A5-A0F5-4180-A6DC-80C84BC014C9"))
                                .comment("Happy to meet the team")
                                .cardType(CardType.HAPPY)
                                .build())
                        .card(Card.builder()
                                .id(UUID.fromString("775A3905-D6BE-49AB-A3C4-EBE287B51539"))
                                .comment("When to meet again??")
                                .cardType(CardType.MEH)
                                .build())
                        .card(Card.builder()
                                .id(UUID.fromString("896C093D-1C50-49A3-A58A-6F1008789632"))
                                .comment("We need more time to finish")
                                .cardType(CardType.SAD)
                                .build())
                        .build()
        );
    }};

    @Override
    public RetroBoard save(RetroBoard domain){
        if( domain.getId() == null )
            domain.setId(UUID.randomUUID());
        this.retroBoardMap.put(domain.getId(), domain);
        return domain;
    }

    @Override
    public Optional<RetroBoard> findById(UUID uuid) {
        return Optional.ofNullable(this.retroBoardMap.get(uuid));
    }

    @Override
    public Iterable<RetroBoard> findAll() {
        return this.retroBoardMap.values();
    }

    @Override
    public void delete(UUID uuid) {
        this.retroBoardMap.remove(uuid);
    }
}
