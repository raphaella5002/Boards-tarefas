package br.com.service;

import br.com.persistence.dao.BoardDAO;
import br.com.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;
    public BoardService(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert (final BoardEntity entity) throws SQLException{
        var dao = new BoardDAO(connection);
        try{
            dao.insert(entity);
            var columns = entity.getBoardColumns().stream().map(c ->{
                c.setBoard(entity);
                return c;
            }).toList();
            for (var column : columns){
                boardColumnDAO.insert(column);
            }
            connection.commit();
        } catch(SQLException e) {
            connection.rollback();
            throw e;
    }
        return entity;
}

    public boolean delete(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);
        try{
            if(!dao.exists(id)){
                return false;
            }
            dao.delete(id);
            connection.commit();
                return true;

        } catch (SQLException e){
            connection.rollback();
                throw e;
        }
    }

}
