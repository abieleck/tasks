package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        // filtrowanie bez Optional
        /*trelloBoards.stream()
                .filter(b -> b.getId() != null)
                .filter(b -> b.getName() != null)
                .filter(b -> b.getName().contains("Kodilla"))
                .forEach(b -> System.out.println(b.getId() + " " + b.getName()));*/

        // Filtrowanie z Optional do punktu 4 zadania:
        trelloBoards.forEach(
                b -> Optional.of(b)
                    .filter(t -> t.getId() != null)
                    .filter(t -> t.getName() != null)
                    .filter(t -> t.getName().contains("Kodilla"))
                    .ifPresent(t -> System.out.println(t.getId() + " " + t.getName()))
        );

    }
}