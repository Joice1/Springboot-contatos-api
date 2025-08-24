package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ContatoRepository {

    // Inserir contato no banco
    public void insert(Contato contato) throws Exception {
        if (contato.getId() == null) {
            contato.setId(UUID.randomUUID());  // Gera UUID caso não tenha
        }

        String sql = """
            INSERT INTO contato (id, nome, email, telefone)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, contato.getId());
            statement.setString(2, contato.getNome());
            statement.setString(3, contato.getEmail());
            statement.setString(4, contato.getTelefone());
            statement.execute();
        }
    }

    // Atualizar contato
    public boolean update(Contato contato) throws Exception {
        String sql = """
            UPDATE contato
            SET nome = ?, email = ?, telefone = ?
            WHERE id = ?
        """;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, contato.getNome());
            statement.setString(2, contato.getEmail());
            statement.setString(3, contato.getTelefone());
            statement.setObject(4, contato.getId());

            return statement.executeUpdate() > 0;
        }
    }

    // Buscar contato por id
    public Contato findById(UUID id) throws Exception {
        String sql = "SELECT * FROM contato WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Contato contato = new Contato();
                contato.setId((UUID) resultSet.getObject("id"));
                contato.setNome(resultSet.getString("nome"));
                contato.setEmail(resultSet.getString("email"));
                contato.setTelefone(resultSet.getString("telefone"));
                return contato;
            }
        }
        return null;  // contato não encontrado
    }

    // Listar todos os contatos
    public List<Contato> findAll() throws Exception {
        String sql = "SELECT * FROM contato";

        List<Contato> contatos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Contato contato = new Contato();
                contato.setId((UUID) resultSet.getObject("id"));
                contato.setNome(resultSet.getString("nome"));
                contato.setEmail(resultSet.getString("email"));
                contato.setTelefone(resultSet.getString("telefone"));
                contatos.add(contato);
            }
        }
        return contatos;
    }

    // Deletar contato pelo id
    public boolean delete(UUID id) throws Exception {
        String sql = "DELETE FROM contato WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, id);
            return statement.executeUpdate() > 0;
        }
    }
}
