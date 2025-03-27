package br.com.service;

import br.com.dto.BoardColumnIntoDTO;
import br.com.dto.CardDetailsDTO;
import br.com.exception.CardBlockedException;
import br.com.exception.CardFinishedException;
import br.com.exception.EntityNotFoundException;
import br.com.persistence.dao.BlockDAO;
import br.com.persistence.dao.CardDAO;
import br.com.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.persistence.entity.BoardColumnKindEnum.FINAL;

@AllArgsConstructor
public class CardService {

    private final Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnIntoDTO> boardColumnsInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não fi encontrado.".formatted(cardId))
            );
            if (dto.blocked()) {
                var message = "O card %s está bloqueado. É necessário desbloquea-lo para move-lo.".formatted(cardId);
                throw new CardBlockedException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board."));
            if (currentColumn.kind().equals(FINAL)) {
                throw new CardFinishedException("O card já foi finalizado.");
            }
            var nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card está cancelado."));
            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void cancel(final Long cardId, final Long cancelColumnId,
                       final List<BoardColumnIntoDTO> boardsColumnInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não fi encontrado.".formatted(cardId))
            );
            if (dto.blocked()) {
                var message = "O card %s está bloqueado. É necessário desbloquea-lo para move-lo.".formatted(cardId);
                throw new CardBlockedException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.id().equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board."));
            if (currentColumn.kind().equals(FINAL)) {
                throw new CardFinishedException("O card já foi finalizado.");
            }
            boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card está cancelado."));

            dao.moveToColumn(cancelColumnId, cardId);
            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void block(final Long id, final String reason, final List<BoardColumnIntoDTO> boardColumnInfo) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(id);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não fi encontrado.".formatted(id))
            );
            if (dto.blocked()) {
                var message = "O card %s já está bloqueado.".formatted(id);
                throw new CardBlockedException(message);
            }
            var currenteColumn = boardColumnInfo.stream()
                    .filter(bc -> bc.id.equals(dto.columnId()))
                    .findFirst()
                    .orElseThrow();
            if (currenteColumn.kind().equals(FINAL) || currenteColumn.kind().equals(CANCEL)) {
                var message = "O card está em uma coluna do tipo %s e não pode ser bloqueado."
                        .formatted(currenteColumn.kind());
                throw new IllegalStateException(message);
            }
            var blockDAO = new BlockDAO(connection);
            blockDAO.block(reason, id);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void unblock (final Long id, final String reason) throws SQLException {
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(id);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não fi encontrado.".formatted(id))
            );
            if (! dto.blocked()) {
                var message = "O card %s não está bloqueado.".formatted(id);
                throw new CardBlockedException(message);
            }
            var blockDAO = new BlockDAO(connection);
            blockDAO.unblock(reason, id);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
}