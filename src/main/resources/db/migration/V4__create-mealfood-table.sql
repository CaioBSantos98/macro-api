CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE meal_foods (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    food_quantity DOUBLE PRECISION NOT NULL,
    meal_id UUID NOT NULL,
    food_id UUID NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES foods(id) ON DELETE CASCADE
);