SET FOREIGN_KEY_CHECKS=0;
DELETE FROM meal;
DELETE FROM ingredient;
DELETE FROM ingredient_meal;
INSERT INTO meal (meal_id, name)
VALUES (1, 'Spaghetti');
SET FOREIGN_KEY_CHECKS=1;
