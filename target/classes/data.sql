-- src/main/resources/data.sql
CREATE MEMORY TABLE temp_app_user (
                       name varchar(255) not null,
                       email varchar(255) not null,
                       password varchar(255) not null,
                       token varchar(255) not null,
                       is_active bit default true not null,
                       last_login datetime not null,
                       modified datetime not null,
                       created datetime not null
) NOT PERSISTENT;

CREATE MEMORY TABLE temp_phones (
                       number varchar(255) not null,
                       cityCode varchar(255) not null,
                       countryCode varchar(255) not null
) NOT PERSISTENT;

INSERT INTO temp_app_user (name, email, password, token, is_active, last_login, modified, created)
VALUES
('John Doe', 'john.doe@example.com', 'Global123', 'token123', true, '2022-01-01T12:00:00Z', '2022-01-01T12:00:00Z', '2022-01-01T12:00:00Z'),
('Jane Smith', 'jane.smith@example.com', 'Logic123', 'token456', true, '2022-01-02T14:30:00Z', '2022-01-02T14:30:00Z', '2022-01-02T14:30:00Z');

INSERT INTO temp_phones (number, cityCode, countryCode)
VALUES
('123', '45', '123456');

MERGE INTO app_user AS u USING temp_app_user AS t ON u.name = t.name
WHEN NOT MATCHED THEN
    INSERT (name, email, password, token, is_active, last_login, modified, created)
    VALUES (t.name, t.email, t.password, t.token, t.is_active, t.last_login, t.modified, t.created);

DROP TABLE temp_app_user;


MERGE INTO phones AS u USING temp_phones AS t ON u.number = t.number
WHEN NOT MATCHED THEN
    INSERT (number, city_code, country_code)
    VALUES (t.number, t.cityCode, t.countryCode);

DROP TABLE temp_phones;