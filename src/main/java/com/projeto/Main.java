package com.projeto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       ItemDAO DAO = new ItemDAO();
       Scanner sc = new Scanner(System.in);
       int esc;
       do{
        System.out.println("###- MENU -###\n1- Adicionar item\n2- Listar itens\n3- Remover item \n4- Alterar item\n0- Sair");
        esc = sc.nextInt();
        sc.nextLine();
        switch (esc) {
            case 1:
                DAO.adicionarProduto();
                break;
            case 2:
                DAO.listarItens();
                break;
            case 3:
                DAO.removerItem();
                break;
            case 4:
                DAO.alterarItem();
                break;
            case 0:
                break;
        
            default:
                System.out.println("digitou errado");
                break;
        }

       }
       while(esc != 0);
       sc.close();
    }
}