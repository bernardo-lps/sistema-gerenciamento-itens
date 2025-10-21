package com.projeto;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemDAO {

    private String url = "jdbc:mysql://localhost/itens";
    private String user = "root";
    private String password = "cavalopreto01";
    Scanner scanner;
    Item item;

    public ItemDAO(){
        scanner = new Scanner(System.in);
    }

    public void adicionarProduto(){
        int id, qtd;
        String nome, descricao;
        boolean stats;
        System.out.println("digite o id do item:");
        id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("digite o nome do item: ");
        nome = scanner.nextLine();
        System.out.println("digite uma descricao para o item: ");
        descricao = scanner.nextLine();
        scanner.nextLine();
        System.out.println("digite a quantidade: ");
        qtd = scanner.nextInt();
        stats = true;
        Item item = new Item(id, nome, descricao, stats,qtd);
        salvarProduto(item);
    }
    public void salvarProduto(Item item){
        try (var connection = DriverManager.getConnection(url, user, password);){
            var sql = "a";
            System.out.println("conexão bem sucedida!");

            try(PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM item WHERE id = ?")){
                ps.setInt(1, item.getId());
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        int count = rs.getInt(1);
                        if(count > 0){
                            sql = "UPDATE item SET quantidade = quantidade +'"+item.getQtd()+"' WHERE id = '"+item.getId()+"'";
                        }
                        else{
                            sql = "insert into item (id, nome, descricao, stats, quantidade) values "+"('"+item.getId()+"','"+item.getNome()+"','"+item.getDescricao()+"', 1, '"+item.getQtd()+"')";
                        }
                    }
                }
            }
            
            try(Statement stmt = connection.createStatement()){
                stmt.executeUpdate(sql);
                System.out.println("item salvo!");
            }
        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }

    public List<Item> buscarTodos(){
        var sql = "SELECT * FROM itens.item";

        List<Item> itens = new ArrayList<Item>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try (var connection = DriverManager.getConnection(url, user, password);){

        
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while(rs.next()){

                boolean status = rs.getBoolean("stats");
                Item item = new Item((rs.getInt("id")),(rs.getString("nome")),(rs.getString("descricao")), status,(rs.getInt("quantidade")));

                itens.add(item);
            }


        } catch (SQLException e) {
            System.out.println("error");
            e.printStackTrace();
        } finally{
                try {
                    if(rs != null){
                        rs.close();
                    }
                    if(ps != null){
                        ps.close();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            
        }
        return itens;

    }

    public void listarItens(){
        System.out.println("#############- LISTA DE ITENS -#############");
        for (Item itens : buscarTodos()) {
            if(itens.isStats() == true){
                System.out.printf("id: %d | nome: %s | descrição: %s | status: ativo | quantidade: %d\n", itens.getId(), itens.getNome(), itens.getDescricao(), itens.getQtd());
            }
            else{
                System.out.printf("id: %d | nome: %s | descrição: %s | status: inativo | quantidade: %d\n", itens.getId(), itens.getNome(), itens.getDescricao(), itens.getQtd());
            }
            
        }
    }

    public void removerItem(){
        listarItens();
        int escolha;
        System.out.println("escolha o item que vc quer remover (pelo ID):");
        escolha = scanner.nextInt();
        var sql = "DELETE FROM itens.item WHERE id = '"+escolha+"';";
        

        try(var connection = DriverManager.getConnection(url, user, password);){
            System.out.println("conexao bem sucedida!!");
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate(sql);
            System.out.println("item removido!");


        } catch (SQLException e) {
            System.out.println("error ");
            e.printStackTrace();
        } 
    }

    public void alterarItem(){
        var sql = "a";
        int escolhaId;
        int escolhaDaAlteracao;
        String alteracao = null;
        String coluna = null;
        listarItens();
        System.out.println("escolha o item(ID)");
        escolhaId = scanner.nextInt();
        System.out.println("escolha o que deseja alterar\n1- id\n2- nome\n3- descrição\n4- status\n5- quantidade");
        escolhaDaAlteracao = scanner.nextInt();
        scanner.nextLine();
        switch (escolhaDaAlteracao) {
            case 1:
                coluna = "id";
                System.out.println("digite o novo id (apenas numeros inteiros positivos)");
                alteracao = scanner.nextLine();
                break;
            case 2:
                coluna = "nome";
                System.out.println("digite o novo nome");
                alteracao = scanner.nextLine();
                break;
            case 3:
                coluna = "descricao";
                System.out.println("digite a nova descriçaõ");
                alteracao = scanner.nextLine();
                break;
            case 4:
                coluna = "stats";
                System.out.println("altere o status\n1- ativo\n0- inativo");
                alteracao = scanner.nextLine();
                break;
            case 5:
                coluna = "quantidade";
                System.out.println("altere a quantidade");
                alteracao = scanner.nextLine();
                break;
            default:
                break;
        }
        sql = "UPDATE item SET "+coluna+" = ? WHERE id = ?";
        try(var connection = DriverManager.getConnection(url, user, password);){
            System.out.println("conexao bem sucedida!!");
            //UPDATE item SET quantidade = quantidade +'"+item.getQtd()+"' WHERE id = '"+item.getId()+"'
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, alteracao);
                ps.setInt(2, escolhaId);
                ps.executeUpdate();
            }
            System.out.println("item alterado!");


        } catch (SQLException e) {
            System.out.println("error ");
            e.printStackTrace();
        } 
    }
}
