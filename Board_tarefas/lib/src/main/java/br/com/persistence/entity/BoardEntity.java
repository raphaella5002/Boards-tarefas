package br.com.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static br.com.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.persistence.entity.BoardColumnKindEnum.INITIAL;

@Data
public class BoardEntity {

    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardColumnEntity getInitialColumn(){
        return getFilteredColumn(bc -> getKind().equals(INITIAL));
    }

    public BoardColumnEntity getCancelColumn (){
        return getFilteredColumn(bc -> bc.getKind().equals(CANCEL));
    }

    private BoardColumnEntity getFilteredColumn (Predicate<BoardColumnEntity> filter){
        return boardColumns.stream()
                .filter(filter)
                .findFirst().orElseThrow();
    }

    public void setId(long id) {
    }

    public void setName(String name) {
    }

    public String getName() {
        return null;
    }

    public Arrays getBoardColumns() {
        return null;
    }

    public void setBoardColumns(Object o) {

    }
}
