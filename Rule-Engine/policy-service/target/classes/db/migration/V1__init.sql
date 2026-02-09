CREATE TABLE rule (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    condition TEXT,
    action TEXT,
    status VARCHAR(20)
);

CREATE TABLE variable (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(50),
    default_value VARCHAR(200)
);

CREATE TABLE parameter (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    type VARCHAR(50),
    multiple_values BOOLEAN
);

CREATE TABLE parameter_value (
    id SERIAL PRIMARY KEY,
    parameter_id INT REFERENCES parameter(id),
    value VARCHAR(150)
);

CREATE TABLE policy (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200),
    description TEXT,
    status VARCHAR(20)
);

CREATE TABLE policy_rules (
    policy_id INT REFERENCES policy(id),
    rules_id INT REFERENCES rule(id)
);

CREATE TABLE policy_approval (
    id SERIAL PRIMARY KEY,
    policy_id INT REFERENCES policy(id),
    maker_id INT,
    checker_id INT,
    approver_id INT,
    status VARCHAR(20),
    comments TEXT
);
