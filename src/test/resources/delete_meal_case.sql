SET REFERENTIAL_INTEGRITY FALSE;
DELETE FROM MEAL;
DELETE FROM INGREDIENT;
DELETE FROM INGREDIENT_MEAL;
INSERT INTO MEAL (meal_id, name)
VALUES (1, 'Spaghetti');
INSERT INTO MEAL (meal_id, name)
VALUES (2, 'Pizza');
SET REFERENTIAL_INTEGRITY TRUE;