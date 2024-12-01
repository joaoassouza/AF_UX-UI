package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados e a
 * verificação de usuários. Contém métodos para conectar ao banco e validar
 * credenciais de login e senha.
 *
 * @author João Souza
 * @version 1.0
 */
public class User {

    /**
     * Armazena o nome do usuário recuperado do banco de dados após a
     * verificação.
     */
    public String nome;

    /**
     * Indica se a verificação das credenciais do usuário foi bem-sucedida. Este
     * valor é definido como true se o usuário for encontrado no banco de dados.
     */
    public boolean result;

    /**
     * Construtor da classe `User`. Inicializa os atributos nome e result com
     * valores padrão.
     */
    public User() {
        this.nome = "";
        this.result = false;
    }

    /**
     * Estabelece uma conexão com o banco de dados MySQL. O método utiliza o
     * driver JDBC para se conectar ao banco e retorna uma instância de
     * Connection.
     *
     * @return Um objeto Connection se a conexão for bem-sucedida, ou null caso
     * ocorra algum erro.
     */
    public Connection conectarBD() {
        Connection conn = null;
        try {
            // Carrega o driver do MySQL
            Class.forName("com.mysql.DriverManager").newInstance();

            // URL de conexão ao banco de dados
            String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
        }
        return conn;
    }

    /**
     * Verifica se o usuário com as credenciais fornecidas (login e senha)
     * existe no banco de dados. Executa uma consulta SQL para validar as
     * credenciais e recupera o nome do usuário, se encontrado.
     *
     * @param login O login do usuário a ser verificado.
     * @param senha A senha do usuário a ser verificada.
     * @return true se o usuário for encontrado no banco de dados,
     * false caso contrário.
     */
    public boolean verificarUsuario(String login, String senha) {
        String sql = "";
        Connection conn = conectarBD();

        // Monta a instrução SQL
        sql = "SELECT nome FROM usuarios ";
        sql += "WHERE login = '" + login + "'";
        sql += " AND senha = '" + senha + "'";

        try {
            // Cria uma declaração para execução da consulta
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Verifica se o resultado contém dados
            if (rs.next()) {
                result = true;
                nome = rs.getString("nome");
            }
        } catch (Exception e) {
            // Tratamento de erro omitido
        }
        return result;
    }
}
// Fim da classe
