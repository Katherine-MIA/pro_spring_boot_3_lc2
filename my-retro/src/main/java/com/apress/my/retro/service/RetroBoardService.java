package com.apress.my.retro.service;

import com.apress.my.retro.board.Card;
import com.apress.my.retro.board.RetroBoard;
import com.apress.my.retro.exception.CardNotFoundException;
import com.apress.my.retro.exception.RetroBoardNotFoundException;
import com.apress.my.retro.persistence.Repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings(value = "unused") // it's painful having an IDE that doesn't recognise spring; not nice being poor don't try it
@AllArgsConstructor
@Service // Should have one service per model, not one service for both Card and RetroBoard, but ok.
public class RetroBoardService {
    Repository<RetroBoard, UUID> repository;

    public RetroBoard save(RetroBoard domain){
        // A cool guy once taught me that this can be checked better with Objects.isNull(domain.getCards()) (Very elegant this way)
        if(domain.getCards() == null)
            domain.setCards(new ArrayList<>());
        return this.repository.save(domain);
    }

    public RetroBoard findById(UUID uuid){
        Optional<RetroBoard> retroBoard = this.repository.findById(uuid);
        if(retroBoard.isEmpty())
            throw new RetroBoardNotFoundException();
        return retroBoard.get();
    }

    public Iterable<RetroBoard> findAll(){
        return this.repository.findAll();
    }

    public void delete(UUID uuid){
        this.repository.delete(uuid);
    }

    public Iterable<Card> findAllCardsFromRetroBoard(UUID uuid){
        return this.findById(uuid).getCards();
    }

    // Uncle Bob said something not very nice about this method  ... and about all the comments I added to the code...
    public Card addCardToRetroBoard(UUID uuid, Card card){
        if(card.getId() == null)
            card.setId(UUID.randomUUID());
        RetroBoard retroBoard = this.findById(uuid);
        List<Card> cardList = new ArrayList<>(retroBoard.getCards());
        cardList.add(card);
        retroBoard.setCards(cardList);
        return card;
    }

    public Card findCardByUUIDFromRetroBoard(UUID uuid, UUID uuidCard){
        RetroBoard retroBoard = this.findById(uuid);
        Optional<Card> card = retroBoard.getCards()
                .stream()
                .filter(c -> c.getId().equals(uuidCard))
                .findFirst();
        if(card.isPresent()) return card.get();
        throw new CardNotFoundException();
    }

    public void removeCardFromRetroBoard(UUID uuid, UUID cardUUID){
        RetroBoard retroBoard = this.findById(uuid);
        List<Card> cardList = new ArrayList<>(retroBoard.getCards());
        // nice use of removeIf I would've used stream like above
        cardList.removeIf(card -> card.getId().equals(cardUUID));
        retroBoard.setCards(cardList);
    }
}
