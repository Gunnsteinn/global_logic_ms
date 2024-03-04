-- src/main/resources/data.sql

-- Inserting sample user data
INSERT INTO app_user (name, email, password, token, is_active, last_login, modified, created)
VALUES
('John Doe', 'john.doe@example.com', 'Global123', 'token123', true, '2022-01-01T12:00:00Z', '2022-01-01T12:00:00Z', '2022-01-01T12:00:00Z'),
('Jane Smith', 'jane.smith@example.com', 'Logic123', 'token456', true, '2022-01-02T14:30:00Z', '2022-01-02T14:30:00Z', '2022-01-02T14:30:00Z');
