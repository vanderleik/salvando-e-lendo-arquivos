package application;

import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Programa que registra as informações de produtos vendidos (nome, quantidade e preço unitário) em um arquivo (sales.csv).
 * Para salvar o arquivo, o programa cria previamente o caminho "C:\temp\in".
 * Em seguida, o programa faz a leitura do arquivo sales.csv salvando as informações de nome e valor total da venda em
 * um arquivo summary.csv. Previamente ao salvamento ele cria a pasta "C:\temp\out" para salvar o arquivo.
 */
public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> products = new ArrayList<>();

        System.out.print("Informe a quantidade de itens vendidos: ");
        int itensVendidos = sc.nextInt();
        System.out.println("Entre com as informações dos itens vendidos: ");

        for (int i = 1; i <= itensVendidos; i++) {
            System.out.print("Nome do produto #" + i + ": ");
            sc.nextLine();
            String nome = sc.nextLine();
            System.out.print("Preço unitário: ");
            double preco = sc.nextDouble();
            System.out.print("Quantidade: ");
            int quantidade = sc.nextInt();

            products.add(new Product(nome, preco, quantidade));
        }

        String strPath = "c:\\temp";
        boolean success = new File(strPath + "\\in").mkdir();
        String pathIn = "c:\\temp\\in\\sales.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathIn, true))){
            for (Product product : products) {
                bw.write(String.valueOf(product));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        success = new File(strPath + "\\out").mkdir();
        String pathOut = "c:\\temp\\out\\summary.csv";

        List<Product> resumo = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathIn))){
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                String nome = fields[0];
                String preco = fields[1];
                String quantidade = fields[2];

                resumo.add(new Product(nome, Double.valueOf(preco.trim()), Integer.parseInt(quantidade.trim())));
                line = br.readLine();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathOut, true))){
                for (Product item : resumo) {
                    String itemResumo = item.getName() + "," + String.format("%.2f", item.total());
                    bw.write(itemResumo);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
