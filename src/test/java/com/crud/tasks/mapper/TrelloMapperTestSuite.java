package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    private TrelloList list1 = new TrelloList("listId1", "listName1", true);
    private TrelloList list2 = new TrelloList("listId2", "listName2", false);
    private TrelloList list3 = new TrelloList("listId3", "listName3", true);

    private TrelloListDto listDto1 = new TrelloListDto("listId1", "listName1", true);
    private TrelloListDto listDto2 = new TrelloListDto("listId2", "listName2", false);
    private TrelloListDto listDto3 = new TrelloListDto("listId3", "listName3", true);

    private List<TrelloList> trelloLists1 = Arrays.asList(list1, list2);
    private List<TrelloList> trelloLists2 = Arrays.asList(list3);
    private List<TrelloListDto> trelloListsDto1 = Arrays.asList(listDto1, listDto2);
    private List<TrelloListDto> trelloListsDto2 = Arrays.asList(listDto3);

    private TrelloCard trelloCard = new TrelloCard("cardName1", "cardDescription1", "cardPos1", "listId1");
    private TrelloCardDto trelloCardDto = new TrelloCardDto("cardName1", "cardDescription1","cardPos1", "listId1");

    private TrelloBoard trelloBoard1 = new TrelloBoard("boardId1", "boardName1", trelloLists1);
    private TrelloBoard trelloBoard2 = new TrelloBoard("boardId2", "boardName2", trelloLists2);
    private List<TrelloBoard> trelloBoards = Arrays.asList(trelloBoard1, trelloBoard2);

    private TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("boardName1", "boardId1", trelloListsDto1);
    private TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("boardName2", "boardId2", trelloListsDto2);
    private List<TrelloBoardDto> trelloBoardsDto = Arrays.asList(trelloBoardDto1, trelloBoardDto2);

    private boolean listIdenticalToListDto(TrelloList trelloList, TrelloListDto trelloListDto) {
        return Objects.equals(trelloList.getId(), trelloListDto.getId()) &&
                Objects.equals(trelloList.getName(), trelloListDto.getName()) &&
                trelloList.isClosed() == trelloListDto.isClosed();
    }

    private boolean listsIdenticalToListsDto(List<TrelloList> lists, List<TrelloListDto> listsDto) {
        return lists.size() == listsDto.size() &&
                IntStream.range(0, lists.size())
                    .allMatch(i -> listIdenticalToListDto(lists.get(i), listsDto.get(i)));
    }

    private boolean cardIdenticalToCardDto(TrelloCard trelloCard, TrelloCardDto trelloCardDto) {
        return Objects.equals(trelloCard.getName(), trelloCardDto.getName()) &&
                Objects.equals(trelloCard.getDesription(), trelloCardDto.getDescription()) &&
                Objects.equals(trelloCard.getPos(), trelloCardDto.getPos()) &&
                Objects.equals(trelloCard.getListId(), trelloCardDto.getListId());
    }

    private boolean boardIdenticalToBoardDto(TrelloBoard trelloBoard, TrelloBoardDto trelloBoardDto) {
        return Objects.equals(trelloBoard.getId(), trelloBoardDto.getId()) &&
                Objects.equals(trelloBoard.getName(), trelloBoardDto.getName()) &&
                listsIdenticalToListsDto(trelloBoard.getLists(), trelloBoardDto.getLists());
    }

    private boolean boardsIdenticalToBoardsDto(List<TrelloBoard> trelloBoards, List<TrelloBoardDto> trelloBoardsDto) {
        return trelloBoards.size() == trelloBoardsDto.size() &&
                IntStream.range(0, trelloBoards.size())
                    .allMatch(i -> boardIdenticalToBoardDto(trelloBoards.get(i), trelloBoardsDto.get(i)));

    }

    @Test
    public void testMapToBoards() {
        //Given

        //When
        List<TrelloBoard> mappedBoards = trelloMapper.mapToBoards(trelloBoardsDto);
        //Then
        boardsIdenticalToBoardsDto(mappedBoards, trelloBoardsDto);
    }

    @Test
    public void testMapToBoardsDto() {
        //Given

        List<TrelloBoardDto> mappedBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        boardsIdenticalToBoardsDto(trelloBoards, mappedBoardsDto);
    }

    @Test
    public void testMapToListDto() {
        //Given

        //When
        List<TrelloListDto> mappedListsDto = trelloMapper.mapToListDto(trelloLists1);
        //Then
        assertTrue(listsIdenticalToListsDto(trelloLists1, mappedListsDto));
    }

    @Test
    public void testMapToList() {
        //Given

        //When
        List<TrelloList> mappedLists = trelloMapper.mapToList(trelloListsDto1);
        //Then
        assertTrue(listsIdenticalToListsDto(mappedLists, trelloListsDto1));
    }

    @Test
    public void testMapToCardDto() {
        //Given

        //When
        TrelloCardDto mappedCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertTrue(cardIdenticalToCardDto(trelloCard, mappedCardDto));
    }

    @Test
    public void testMapToCard() {
        //Given

        //When
        TrelloCard mappedCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertTrue(cardIdenticalToCardDto(mappedCard, trelloCardDto));
    }

}
