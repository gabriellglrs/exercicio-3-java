package application;

import entities.Client;
import entities.Order;
import entities.OrderItem;
import entities.Product;
import entities.enums.OrderStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program {
    private static Order order;

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter cliente data:");
        enterClient(scanner);
        System.out.print("How many items to this order?");
        itemsOrder(scanner);
        System.out.println("ORDER SUMMARY:");
        orderSummary();

        scanner.close();
    }

    public static void enterClient(Scanner scanner) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Birth date  (DD/MM/YYYY): ");
        Date moment = sdf.parse(scanner.nextLine());
        System.out.println("Enter order data:");
        System.out.println("Status: ");
        OrderStatus status = Status(scanner);

        order = new Order(moment, status, new Client(name, email, moment));
    }

    public static OrderStatus Status(Scanner scanner) {
        int opcaoEscolhida;
        boolean opcaoBoolean;
        OrderStatus status = OrderStatus.NULL;

        do {
            System.out.println("digite 1 para PENDING_PAYMENT");
            System.out.println("digite 2 para PROCESSING");
            System.out.println("digite 3 para SHIPPED");
            System.out.println("digite 4 para DELIVERED");
            opcaoEscolhida = scanner.nextInt();
            scanner.nextLine();
            opcaoBoolean = false;
            switch (opcaoEscolhida) {
                case 1:
                    status = OrderStatus.PENDING_PAYMENT;
                    break;
                case 2:
                    status = OrderStatus.PROCESSING;
                    break;
                case 3:
                    status = OrderStatus.SHIPPED;
                    break;
                case 4:
                    status = OrderStatus.DELIVERED;
                    break;
                default:
                    opcaoBoolean = true;
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida");
                    break;
            }
        } while (opcaoBoolean);
        return status;
    }

    public static void itemsOrder(Scanner scanner) {
        int qtdItems = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < qtdItems; i++) {
            System.out.println("Enter #" + (i + 1) + " item data:");
            System.out.print("Product name: ");
            String name = scanner.nextLine();
            System.out.print("Product price: ");
            double price = scanner.nextDouble();
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Product product = new Product(name, price);
            OrderItem orderItem = new OrderItem(quantity, price, product);
            order.addItem(orderItem);
        }
    }

    public static void orderSummary() {
        System.out.println("Order moment: " + order.getMoment());
        System.out.println("Order status: " + order.getStatus());
        System.out.println("Client: " + order.getClient().getName() + " - (" + order.getMoment() + ") - " + order.getClient().getEmail());
        System.out.println();
        System.out.println();
        System.out.println("Order items:");
        System.out.println("----------------------------------------------------");
        for (OrderItem item : order.getItems()) {
            System.out.println(item.getProduct().getName() + ", $" + String.format("%.2f", item.getPrice()) + ", Quantity: " + item.getQuantity() + ", Subtotal: " + String.format("%.2f", item.subTotal()));
            System.out.println("----------------------------------------------------");

        }
        System.out.println("Total price: $" + String.format("%.2f", order.total()));
        System.out.println("----------------------------------------------------");

    }
}
