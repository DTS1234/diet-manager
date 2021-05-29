SET FOREIGN_KEY_CHECKS=0;
DELETE
FROM meal;
DELETE
FROM ingredient;
DELETE
FROM ingredient_meal;

INSERT INTO meal (meal_id, name)
VALUES (1, 'Spaghetti');
INSERT INTO meal (meal_id, name)
VALUES (2, 'Pizza');
INSERT INTO meal (meal_id, name)
VALUES (3, 'Beef Steak');
INSERT INTO meal (meal_id, name)
VALUES (4, 'Tiramisu');
INSERT INTO meal (meal_id, name)
VALUES (5, 'Pancakes');

INSERT INTO ingredient
VALUES (1, 12, 13, 14, 'name', 15);

SET FOREIGN_KEY_CHECKS=1;

INSERT INTO ingredient_meal
VALUES (1, 1);
