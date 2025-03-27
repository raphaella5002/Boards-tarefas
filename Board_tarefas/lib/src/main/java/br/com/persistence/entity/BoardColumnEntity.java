package br.com.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardColumnEntity {

    private Long id;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;
    private BoardEntity board = new BoardEntity();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CardEntity> cards = new ArrayList<>();

    public String getName() {
    }

    public int getOrder() {
    }

    public <E> Enum<E> getKind() {
    }

    public Thread getBoard() {
    }

    public void setId(long lastInsertID) {

    }

    public void setName(String name) {
    }

    public void setOrder(int order) {
    }

    public void setKind(BoardColumnKindEnum kind) {

    }
}
