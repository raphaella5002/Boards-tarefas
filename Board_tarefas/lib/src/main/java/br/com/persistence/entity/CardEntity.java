package br.com.persistence.entity;

import static br.com.persistence.entity.BoardColumnKindEnum.INITIAL;

public class CardEntity {

    private Long id;
    private String title;
    private String description;
    private BoardColumnEntity boardColumn = new BoardColumnEntity();

}
