package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.CreatedTrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void testFetchNoBoards() {
        //Given
        when(trelloClient.getTrelloBoards()).thenReturn(new ArrayList<>());
        //When
        List<TrelloBoardDto> fetchedBoardsDto = trelloService.fetchTrelloBoards();
        //Then
        assertNotNull(fetchedBoardsDto);
        assertTrue(fetchedBoardsDto.isEmpty());
    }

    @Test
    public void testFetchTrelloBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = Arrays.asList(
                new TrelloBoardDto("name1", "id1", new ArrayList<>()),
                new TrelloBoardDto("name2", "id2", new ArrayList<>())
        );
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);
        //When
        List<TrelloBoardDto> fetchedBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(trelloBoardDtos, fetchedBoardDtos);
    }

    @Test
    public void testCreateTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "top", "1");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1", "name", "http://shortUrl");
        when(trelloClient.createNewCard(any())).thenReturn(createdCard);
        //When
        CreatedTrelloCardDto createdTrelloCard = trelloService.createTrelloCard(trelloCardDto);
        //Then
        assertEquals(createdCard, createdTrelloCard);
        verify(simpleEmailService, times(1)).send(any(Mail.class));
    }
}
