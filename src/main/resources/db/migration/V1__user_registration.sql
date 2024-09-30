CREATE TABLE user_registration (
                                   id VARCHAR(255) PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   user_name VARCHAR(255) NOT NULL UNIQUE,
                                   password VARCHAR(255) NOT NULL,
                                   is_active BOOLEAN DEFAULT FALSE,

    -- Fields from BaseEntity
                                   created_by VARCHAR(255),
                                   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   version BIGINT    DEFAULT 0,
                                   last_modified_by VARCHAR(255),
                                   last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
