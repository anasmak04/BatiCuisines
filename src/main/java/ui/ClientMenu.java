package main.java.ui;

import main.java.domain.entities.Client;
import main.java.exception.ClientNotFoundException;
import main.java.service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenu {
    private final ClientService clientService;
    private static Scanner scanner;

    public ClientMenu(ClientService clientService) {
        this.clientService = clientService;
        scanner = new Scanner(System.in);
    }


    public void clientMenu() {
        int choice;

        do {
            System.out.println("\n--- Client Management Menu ---");
            System.out.println("1. Save new client");
            System.out.println("2. Find all clients");
            System.out.println("3. Find client by ID");
            System.out.println("4. Update client");
            System.out.println("5. Delete client");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addNewClient();
                    break;
                case 2:
                    findAll();
                    break;
                case 3:
                    findById();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    delete();
                    break;
                case 6:
                    System.out.println("Exiting client menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);
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

    public void findById() {
        System.out.println("\n--- Find client by id ---");
        System.out.print("Enter id: ");
        Long id = scanner.nextLong();

        clientService.findById(id).ifPresent(client -> {
            System.out.printf("+--------------------+-----------------------------+----------------+--------------+%n");
            System.out.printf("| %-18s | %-27s | %-14s | %-12s |%n",
                    "Name", "Address", "Phone", "Professional");
            System.out.printf("+--------------------+-----------------------------+----------------+--------------+%n");

            System.out.printf("| %-18s | %-27s | %-14s | %-12s |%n",
                    client.getName(),
                    client.getAddress(),
                    client.getPhone(),
                    client.isProfessional() ? "Yes" : "No");

            System.out.printf("+--------------------+-----------------------------+----------------+--------------+%n");

            System.out.println("Client found!");
        });

    }


    public void update() {
        System.out.println("\n--- Update client ---");
        System.out.print("Enter the Id  : ");
        Long id = scanner.nextLong();
        Client clientOpt = clientService.findById(id).orElseThrow(() -> new ClientNotFoundException("Client Not Found"));
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new address: ");
        String address = scanner.nextLine();
        System.out.print("Enter new phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter if  professional: ");
        boolean status = scanner.nextBoolean();
        Client client1 = new Client(id, name, address, phoneNumber, status);
        clientService.update(client1);
    }


    public void delete() {
        System.out.println("\n--- Delete client ---");
        System.out.print("Enter id: ");
        Long id = scanner.nextLong();
        boolean client = clientService.delete(id);
        if (client){
            System.out.println("Client deleted!");
        }else{
            System.out.println("Client not found");
        }
    }

    public void findAll() {
        List<Client> clientList = clientService.findAll();

        System.out.printf("+--------------------+-----------------------------+----------------+--------------+%n");
        System.out.printf("| %-18s | %-27s | %-14s | %-12s |%n",
                "Name", "Address", "Phone", "Professional");
        System.out.printf("+--------------------+-----------------------------+----------------+--------------+%n");

        clientList.forEach(client -> {
            System.out.printf("| %-18s | %-27s | %-14s | %-12s |%n",
                    client.getName(),
                    client.getAddress(),
                    client.getPhone(),
                    client.isProfessional() ? "Yes" : "No");
        });
        System.out.printf("+--------------------+-----------------------------+----------------+--------------+%n");
    }


}
