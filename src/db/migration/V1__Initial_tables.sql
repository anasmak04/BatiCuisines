CREATE TABLE clients (
 id SERIAL PRIMARY KEY,
 name VARCHAR(255) NOT NULL,
 address VARCHAR(255),
 phone VARCHAR(20),
 isProfessional BOOLEAN NOT NULL
);

CREATE TYPE projectStatus AS ENUM ('INPROGRESS', 'FINISHED', 'CANCELLED');

CREATE TABLE projects (
id SERIAL PRIMARY KEY,
projectName VARCHAR(255) NOT NULL,
profitMargin DOUBLE PRECISION,
totalCost DOUBLE PRECISION,
status projectStatus,
client_id INT,
FOREIGN KEY (client_id) REFERENCES Clients(id) ON DELETE CASCADE
);

CREATE TABLE components (
id SERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
componentType VARCHAR(255),
vatRate DOUBLE PRECISION
);

CREATE TABLE materials (
unitCost DOUBLE PRECISION,
quantity DOUBLE PRECISION,
transportCost DOUBLE PRECISION,
qualityCoefficient DOUBLE PRECISION
) INHERITS (components);

CREATE TABLE labor (
hourlyRate DOUBLE PRECISION,
workHours DOUBLE PRECISION,
workerProductivity DOUBLE PRECISION
) INHERITS (components);

CREATE TABLE quotes (
id SERIAL PRIMARY KEY,
estimatedAmount DOUBLE PRECISION,
issueDate DATE,
isAccepted BOOLEAN,
project_id INT,
FOREIGN KEY (project_id) REFERENCES Projects(id) ON DELETE CASCADE
);