INSERT INTO locations (department)
VALUES (0), -- warehouse
       (1), -- manufacturing
       (2); -- lab

INSERT INTO product_components (name, type, stock, status, used, serial_number, minimal_stock, maximal_stock)
VALUES ('Solution-Liq', 0, 1000, 1, 0, 'PCSN00001', 1000, 5000),
       ('Bottle-10ml', 1, 1000, 1, 0, 'PCSN00002', 1000, 5000),
       ('Cap-10ml', 2, 1000, 1, 0, 'PCSN00003', 1000, 5000),
       ('Induction-liner-10ml', 3, 1000, 1, 0, 'PCSN00004', 1000, 5000),
       ('Label-product-1', 4, 1000, 1, 0, 'PCSN00051', 1000, 5000),
       ('Printed-bag-product-1', 5, 1000, 1, 0, 'PCSN00061', 1000, 5000),
       ('Inner-bag', 6, 1000, 1, 0, 'PCSN00007', 1000, 5000);

INSERT INTO raw_materials (name, stock, status, used, batch_number, minimal_stock, maximal_stock)
VALUES ('Product-1-Raw', 1000, 1, 0, 'RMSN00001', 1000, 5000);

INSERT INTO products (name, type, stock, status, sold, serial_number, minimal_stock, maximal_stock, raw_material_name)
VALUES ('Product-1-S', 0, 1000, 1, 0, 'PSN00010', 1000, 5000, 'Product-1-Raw'),
       ('Product-1-R', 1, 1000, 1, 0, 'PSN00011', 1000, 5000, 'Product-1-Raw');

-- Establishing the relationship between Product-1-S and related components
INSERT INTO product_components_products (product_components_id, products_id)
VALUES ('Solution-Liq', 'Product-1-S'),
       ('Bottle-10ml', 'Product-1-S'),
       ('Cap-10ml', 'Product-1-S'),
       ('Induction-liner-10ml', 'Product-1-S'),
       ('Label-product-1', 'Product-1-S'),
       ('Printed-bag-product-1', 'Product-1-R'),
       ('Inner-bag', 'Product-1-R');

-- Establishing relationships with Location WAREHOUSE
INSERT INTO locations_product_components (locations_id, product_components_id)
VALUES (0, 'Solution-Liq'),
       (0, 'Bottle-10ml'),
       (0, 'Cap-10ml'),
       (0, 'Induction-liner-10ml'),
       (0, 'Label-product-1'),
       (0, 'Printed-bag-product-1'),
       (0, 'Inner-bag');

INSERT INTO locations_products (locations_id, products_id)
VALUES (0, 'Product-1-S'),
       (0, 'Product-1-R');

INSERT INTO locations_raw_materials (locations_id, raw_materials_id)
VALUES (0, 'Product-1-Raw');