DELIMITER $$
CREATE PROCEDURE assign_seat(
  IN p_emp_id VARCHAR(5),
  IN p_seat_seq INT
)
BEGIN
UPDATE employee
SET FLOOR_SEAT_SEQ = p_seat_seq
WHERE EMP_ID = p_emp_id;
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE release_seat(
  IN p_emp_id VARCHAR(5)
)
BEGIN
UPDATE employee
SET FLOOR_SEAT_SEQ = NULL
WHERE EMP_ID = p_emp_id;
END $$
DELIMITER ;
