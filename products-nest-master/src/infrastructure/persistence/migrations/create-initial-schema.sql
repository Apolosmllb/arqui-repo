CREATE TABLE IF NOT EXISTS categories (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  created_at DATETIME NOT NULL,
  created_by BIGINT NOT NULL,
  updated_at DATETIME NOT NULL,
  updated_by BIGINT NOT NULL,
  PRIMARY KEY(id),
  UNIQUE KEY UQ_categories_name (name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE product_states (
  id TINYINT UNSIGNED NOT NULL,
  name VARCHAR(50) NOT NULL,
  created_at DATETIME NOT NULL,
  created_by BIGINT NOT NULL,
  updated_at DATETIME NOT NULL,
  updated_by BIGINT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX UQ_product_states_name(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS products (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(4000) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  currency VARCHAR(10) NOT NULL,
  stock INT UNSIGNED NOT NULL,
  product_state_id TINYINT UNSIGNED NOT NULL,
  category_id INT UNSIGNED NOT NULL,
  created_at DATETIME NOT NULL,
  created_by BIGINT NOT NULL,
  updated_at DATETIME NOT NULL,
  updated_by BIGINT NOT NULL,
  PRIMARY KEY(id),
  UNIQUE KEY UQ_products_name(name),
  KEY IX_products_category_id(category_id),
  KEY IX_products_state_id (product_state_id),
  CONSTRAINT FK_products_category_id FOREIGN KEY(category_id) REFERENCES categories(id),
  CONSTRAINT FK_products_state_id FOREIGN KEY(product_state_id) REFERENCES product_states(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;