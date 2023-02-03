INSERT INTO categories (id, name, created_at, created_by, updated_at, updated_by) VALUES
(1, 'shoes', UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1),
(2, 'books', UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1),
(3, 'electronics', UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1);

INSERT INTO product_states(id, name, created_at, created_by, updated_at, updated_by)
VALUES
(0, 'INACTIVE', UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1),
(1, 'ACTIVE', UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1);

INSERT INTO products (id, name, description, price, currency, stock, product_state_id, category_id, created_at, created_by, updated_at, updated_by) VALUES
(1, 'Adidas Cloudfoam Ultimate', 'Walk in the air in the black / black CLOUDFOAM ULTIMATE running shoe from ADIDAS', 178.89, 'SOL', 5, 1, 1, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1),
(2, 'Under armour Men ''s Micro G Assert – 7', 'Under armour Men ''Lightweight mesh upper delivers complete breathability . Durable leather overlays for stability ', 12.5, 'SOL', 4, 1, 1, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1),
(3, 'Spring Boot in Action', 'Under armour Men '' Craig Walls is a software developer at Pivotal and is the author of Spring in Action', 40.06, 'SOL', 12, 1, 2, UTC_TIMESTAMP(), 1, UTC_TIMESTAMP(), 1);