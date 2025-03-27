package br.com.dto;

import br.com.persistence.entity.BoardColumnKindEnum;

public record BoardColumnDTO(long id,
                             String name,
                             BoardColumnKindEnum kind,
                             int cardsAmount) {

}
