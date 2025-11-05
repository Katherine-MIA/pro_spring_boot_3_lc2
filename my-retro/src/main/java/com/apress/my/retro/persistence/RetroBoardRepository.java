package com.apress.my.retro.persistence;

import com.apress.my.retro.board.Card;
import com.apress.my.retro.board.CardType;
import com.apress.my.retro.board.RetroBoard;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unused")
@Repository // When having jdbc as dependency this sets up the DAO support and specific exception translations (like SQLException)
@AllArgsConstructor
public class RetroBoardRepository implements SimpleRepository<RetroBoard, UUID> {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<RetroBoard> findById(UUID uuid) {
        String sql = """
                SELECT r.ID AS id, r.NAME, c.ID, AS card_id, c.CARD_TYPE AS card_type, c.COMMENT AS comment
                FROM RETRO_BOARD r
                LEFT JOIN CARD c ON r.ID = c.RETRO_BOARD_ID
                WHERE r.ID = ?
                """;
        List<RetroBoard> results = jdbcTemplate.query(sql,
                new Object[] {uuid},
                new int[] {Types.OTHER},
                new RetroBoardRowMapper());
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Iterable<RetroBoard> findAll() {
        String sql = """
                SELECT r.ID AS id, r.NAME, c.ID AS card_id, c.CARD_TYPE, c.COMMENT
                FROM RETRO_BOARD r
                LEFT JOIN CARD c ON r.ID = c.RETRO_BOARD_ID
                """;
        return jdbcTemplate.query(sql, new RetroBoardRowMapper());
    }

    @Override
    @Transactional // Handles execution of this function through AOP to perform transaction flow, begin transaction,
    // commit and rollback if necessary.
    public RetroBoard save(RetroBoard retroBoard) {
        if(retroBoard.getId() == null) {
            retroBoard.setId(UUID.randomUUID());
        }
        String sql = "INSERT INTO RETRO_BOARD (ID, NAME) VALUES (?, ?)";
        jdbcTemplate.update(sql, retroBoard.getId(), retroBoard.getName());

        Map<UUID, Card> mutableMap = new HashMap<>(retroBoard.getCards());

        for (Card card : retroBoard.getCards().values()) {
            card.setRetroBoardId(retroBoard.getId());
            card = saveCard(card);
            mutableMap.put(card.getId(), card);
        }
        retroBoard.setCards(mutableMap);
        return retroBoard;
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        String sql = "DELETE FROM CARD WHERE RETRO_BOARD_ID = ?";
        jdbcTemplate.update(sql, uuid);

        sql = "DELETE FROM RETRO_BOARD WHERE ID = ?";
        jdbcTemplate.update(sql, uuid);
    }

    private Card saveCard(Card card) {
        if(card.getId() == null) {
            card.setId(UUID.randomUUID());
        }
        String sql = "INSERT INTO CARD (ID, CARD_TYPE, COMMENT, RETRO_BOARD_ID) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, card.getId(), card.getCardType().name(), card.getComment(), card.getRetroBoardId());
        return card;
    }
}
// More on transactions: https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-decl-explained.html

/* --> FIRST VERSION OF MY-RETRO FOR RetroBoardRepository class
@SuppressWarnings(value = "unused")
//@Repository // Facilitates injection into other classes of the instance of this object
@Component
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
*/