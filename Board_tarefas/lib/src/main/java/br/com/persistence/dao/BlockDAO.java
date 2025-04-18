package br.com.persistence.dao;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@AllArgsConstructor
public class BlockDAO {

    private final Connection connection;

    public void block (final String reason, final Long cardId) throws SQLException {
        var sql = "INSERT INTO BLOCKS (blocked_at, block_reason, card_id) VALUES (?, ?, ?)";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setTimestamp(i ++, toTimesTamp(OffsetDateTime.now()));
            statement.setString(i++, reason);
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
        }
    }

    public void unblock (final String reason, final Long cardId) throws SQLException{
        var sql = "UPDATE BLOCKS SET unblocked_at = ?, unblocked_reason = ?, WHERE card_id = ? AND unblocked_reason IS NULL;";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setTimestamp(i ++, toTimesTamp(OffsetDateTime.now()));
            statement.setString(i++, reason);
            statement.setLong(i, entity.getBoard().getId());
            statement.executeUpdate();
        }
    }

}
