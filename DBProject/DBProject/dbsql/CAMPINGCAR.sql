CREATE TABLE CAMPINGCAR (
    CARID CHAR(8) NOT NULL,
    COMPANYID CHAR(8) NOT NULL,
    CARNAME CHAR(16) NOT NULL,
    CARSNUM CHAR(16) NOT NULL,
    NOPASSENGERS INT NOT NULL,
    CARDETAILS1 CHAR(32),
    CARDETAILS2 CHAR(32),
    RENTALFEE INT NOT NULL,
    REGDATE CHAR(12) NOT NULL,
    PRIMARY KEY(CARID)
); 

INSERT INTO CAMPINGCAR VALUES('1111', '001', 'bad', 'A1134', 2, '2wd', 'auto', 100000,'20180101');
INSERT INTO CAMPINGCAR VALUES('1231', '002', 'cold', 'B2231', 2, '2wd', 'auto', 110000,'20180112');
INSERT INTO CAMPINGCAR VALUES('1423', '001', 'cool', 'A5571', 2, '2wd', 'stick', 125000,'20180123');
INSERT INTO CAMPINGCAR VALUES('1589', '004', 'fog', 'Z9821', 4, '4wd', 'auto', 130000,'20180125');
INSERT INTO CAMPINGCAR VALUES('1809', '005', 'hot', 'V1251', 4, '4wd', 'auto', 132000,'20180128');
INSERT INTO CAMPINGCAR VALUES('2100', '001', 'misty', 'S2952', 4, '4wd', 'stick', 140000,'20180201');
INSERT INTO CAMPINGCAR VALUES('2122', '007', 'rain', 'H1024', 4, '2wd', 'auto', 146000,'20180107');
INSERT INTO CAMPINGCAR VALUES('2222', '003', 'shower', 'J2934', 4, '2wd', 'stick', 150000,'20180108');
INSERT INTO CAMPINGCAR VALUES('2233', '002', 'snow', 'I1892', 6, '2wd', 'stick', 152000,'20180109');
INSERT INTO CAMPINGCAR VALUES('3210', '010', 'storm', 'Y1928', 10, '2wd', 'stick', 160000,'20180110');
INSERT INTO CAMPINGCAR VALUES('3300', '001', 'warm', 'R8722', 12, '4wd', 'auto', 167000,'20180111');
INSERT INTO CAMPINGCAR VALUES('3415', '001', 'windy', 'D1001', 12, '4wd', 'stick', 180000,'20180112');