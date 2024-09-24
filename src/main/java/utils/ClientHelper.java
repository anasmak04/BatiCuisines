package main.java.utils;

import main.java.domain.entities.Client;

import java.util.Scanner;

public class ClientHelper {

    private static Scanner scanner = new Scanner(System.in);

    public static Client getClientDetails(Long id) {
        if (id != null) {
            System.out.println("\n--- Update Client ---");
        } else {
            System.out.println("\n--- Add New Client ---");
        }

        System.out.print("Enter the name for the client: ");
        String name = scanner.nextLine();
        System.out.print("Enter the address for the client: ");
        String address = scanner.nextLine();
        System.out.print("Enter the phone number for the client: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Is the client professional? (true/false): ");
        boolean status = scanner.nextBoolean();
        scanner.nextLine();

        return new Client(id != null ? id : 0L, name, address, phoneNumber, status);
    }

}
