drop procedure calculate_and_show_ranking;

DELIMITER //
CREATE PROCEDURE calculate_and_show_ranking()
BEGIN
    DROP TABLE IF EXISTS exam_ranked;
    CREATE TABLE exam_ranked AS
    SELECT
        id,
        name,
        kor,
        eng,
        mat,
        kor + eng + mat AS sum,
        (kor + eng + mat)/3 AS avg,
        RANK() OVER (ORDER BY (kor + eng + mat) DESC) AS ranking
    FROM
        examtable;

    SELECT * FROM exam_ranked;
END //
DELIMITER ;

CALL calculate_and_show_ranking();