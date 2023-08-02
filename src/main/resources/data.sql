INSERT IGNORE INTO roles (id, name)
VALUES (1, 'ADMIN');

INSERT IGNORE INTO roles (id, name)
VALUES (2, 'USER');

INSERT IGNORE INTO profile_types (id, type_name)
VALUES (1, 'BORROWER');

INSERT IGNORE INTO profile_types (id, type_name)
VALUES (2, 'LENDER');

INSERT IGNORE INTO transaction_types (transaction_type_id, type_name)
VALUES
    (1, 'DEPOSIT'),
    (2, 'WITHDRAW'),
    (3, 'PAY'),
    (4, 'LEND'),
    (5, 'RELEASE_HOLD');
