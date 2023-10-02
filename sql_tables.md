-- User Table
CREATE TABLE users (
user_id SERIAL PRIMARY KEY,
phone_number VARCHAR(15) UNIQUE NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
password_hash VARCHAR(255) NOT NULL,
name VARCHAR(255),
profile_image VARCHAR(512), -- This will store the URL to the profile image
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Business Table
CREATE TABLE businesses (
business_id SERIAL PRIMARY KEY,
business_name VARCHAR(255) NOT NULL,
contact_email VARCHAR(255),
contact_phone_number VARCHAR(15),
address VARCHAR(512),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Product/Service Catalog Table
CREATE TABLE catalog_items (
item_id SERIAL PRIMARY KEY,
business_id INTEGER REFERENCES businesses(business_id),
item_name VARCHAR(255) NOT NULL,
description TEXT,
price DECIMAL(10, 2),
availability_status VARCHAR(50) DEFAULT 'available', -- Could be 'available', 'unavailable', 'limited', etc.
image_url VARCHAR(512), -- URL to the product/service image
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Appointment Table
CREATE TABLE appointments (
appointment_id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(user_id),
business_id INTEGER REFERENCES businesses(business_id),
start_time TIMESTAMP NOT NULL,
end_time TIMESTAMP NOT NULL,
status VARCHAR(50) DEFAULT 'booked', -- Could be 'booked', 'available', 'cancelled'
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Chat Table
CREATE TABLE chats (
chat_id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(user_id),
business_id INTEGER REFERENCES businesses(business_id),
other_user_id INTEGER REFERENCES users(user_id), -- in case of user-to-user chat
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Message Table
CREATE TABLE messages (
message_id SERIAL PRIMARY KEY,
chat_id INTEGER REFERENCES chats(chat_id),
sender_id INTEGER, -- Could be a user_id or a business_id
text_content TEXT,
media_url VARCHAR(512), -- URL to media (image/video/audio)
timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

