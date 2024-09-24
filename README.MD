# BatiCuisine

## Description

BatiCuisine is a Java console application designed for professionals in the construction and kitchen renovation industry. It helps calculate the total cost of projects by considering the materials used and labor costs, which are billed by the hour. The application includes advanced features such as client management, personalized quote generation, and an overview of the financial and logistical aspects of renovation projects.

## Context

This project aims to provide professionals with a powerful and practical tool to accurately estimate costs and effectively manage kitchen renovation projects.

## Features

- **Project Management**
    - Add associated clients to projects.
    - Manage components (materials and labor).
    - Associate quotes with projects for cost estimation.
    - Project characteristics include:
        - `projectName` (String)
        - `profitMargin` (double)
        - `totalCost` (double)
        - `projectStatus` (Enum)

- **Component Management**
    - **Materials:** Manage costs of materials with attributes like:
        - `name` (String)
        - `unitCost` (double)
        - `quantity` (double)
        - `componentType` (String)
        - `vatRate` (double)
        - `transportCost` (double)
        - `qualityCoefficient` (double)

    - **Labor:** Calculate costs based on hourly rate, hours worked, and worker productivity:
        - `name` (String)
        - `componentType` (String)
        - `vatRate` (double)
        - `hourlyRate` (double)
        - `workingHours` (double)
        - `workerProductivity` (double)

- **Client Management**
    - Register basic client information.
    - Differentiate between professional and private clients, affecting discounts and taxes.
    - Store client information such as:
        - `name` (String)
        - `address` (String)
        - `phone` (String)
        - `isProfessional` (boolean)

- **Quote Generation**
    - Generate quotes before work begins, including estimated costs of materials, labor, and taxes.
    - Include issue date and validity period of the quote.
    - Track quote acceptance status.

- **Cost Calculation**
    - Integrate component costs into the total project cost calculation.
    - Apply profit margins and account for applicable taxes and discounts.
    - Adjust costs based on material quality or labor productivity.

- **Detailed Results Display**
    - Display complete project details (client, components, total cost).
    - Show client and quote information.
    - Generate a comprehensive summary of total costs including labor, materials, taxes, and profit margin.

## User Stories

1. As a construction professional, I want to create a new renovation or construction project to track all project details and associated costs.
2. As a professional, I want to associate a client with each project for billing and quoting.
3. As a user, I want to add materials to a project with their unit cost, quantity, and transport to accurately estimate material costs.
4. As a professional, I want to record workers' hours with their hourly rate and productivity for accurate labor cost calculation.
5. As a project manager, I want to manage multiple types of materials and specialized workers for precise cost calculation based on quality or expertise required.

## Example Usage

