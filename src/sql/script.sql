CREATE TABLE product (
                         id int PRIMARY KEY,
                         name VARCHAR(255),
                         price NUMERIC,
                         creation_datetime TIMESTAMP
);
CREATE TABLE product_category (
                                  id int PRIMARY KEY,
                                  name VARCHAR(255),
                                  product_id INT REFERENCES product(id)
);