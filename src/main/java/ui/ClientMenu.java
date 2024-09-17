package main.java.ui;

import main.java.domain.entities.Client;
import main.java.exception.ClientNotFoundException;
import main.java.service.ClientService;

import java.util.Optional;
import java.util.Scanner;

public class ClientMenu {
    private final ClientService clientService;
    private static Scanner scanner;

    public ClientMenu(ClientService clientService) {
        this.clientService = clientService;
        scanner = new Scanner(System.in);
    }


    public Client searchByName(String name) {
        Optional<Client> optionalClient = this.clientService.findByName(name);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            System.out.println("Client found!");
            System.out.println("Name: " + client.getName());
            System.out.println("Address: " + client.getAddress());
            System.out.println("Phone number: " + client.getPhone());

            while (true) {
                System.out.print("Would you like to continue with this client? (y/n): ");
                String choiceToContinue = scanner.nextLine().trim().toLowerCase();
                if (choiceToContinue.equals("y")) {
                    return client;
                } else if (choiceToContinue.equals("n")) {
                    addNewClient();
                } else {
                    System.out.println("Invalid choice. Please enter 'y' or 'n'.");
                }
            }
        } else {
            throw new ClientNotFoundException("Client not found");
        }
    }


    public Client addNewClient() {
        System.out.println("\n--- Add a new client ---");
        System.out.print("Enter the name for a client : ");
        String name = scanner.nextLine();
        System.out.print("Enter the address for a client : ");
        String address = scanner.nextLine();
        System.out.print("Enter the phone number for a client : ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter status for a client is professional or not : ");
        boolean status = scanner.nextBoolean();
        Client client = new Client(0L, name, address, phoneNumber, status);
        return clientService.save(client);
    }


}
