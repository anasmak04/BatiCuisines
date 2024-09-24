ALTER TABLE components
    ADD COLUMN project_id INT,
    ADD CONSTRAINT fk_project_id
FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE;
