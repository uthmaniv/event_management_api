CREATE TABLE authority(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE role(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE user_roles(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES "user"(id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE role_authorities(
    role_id INT NOT NULL,
    authority_id INT NOT NULL,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES role(id),
    CONSTRAINT fk_authority_id FOREIGN KEY (authority_id) REFERENCES authority(id)
);

INSERT INTO role (id, name)
VALUES (1, 'ADMIN'),(2, 'USER');

INSERT INTO authority(id, name)
VALUES (1, 'READ'), (2, 'WRITE'), (3, 'DELETE'), (4,'READ_USER'), (5,'DELETE_USER');


INSERT INTO role_authorities(role_id, authority_id)
VALUES (1, 1),(1,2),(1,3),(1,4),(2,1),(2,2),(2,3);

INSERT INTO user_roles(user_id, role_id)
VALUES (1,1),(2,2),(3,2),(4,2),(5,2);