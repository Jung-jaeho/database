CREATE TABLE RENTALCOMPANY (
    COMPID CHAR(8) NOT NULL,
    COMPNAME CHAR(16) NOT NULL,
    COMPADDRESS CHAR(128) NOT NULL,
    COMPPHONENO CHAR(16) NOT NULL,
    COMPMANAGER CHAR(16) NOT NULL,
    COMPMANAGEREMAIL CHAR(48) NOT NULL,
    PRIMARY KEY(COMPID)
); 

INSERT INTO RentalCompany VALUES('001', 'A', '6, Buljeong-ro, Bundang-gu, Seongnam-si, Gyeonggi-do', '01029945502', 'Tim', 'aefaejgkj@naver.com' );
INSERT INTO RentalCompany VALUES('002', 'B', 'Bukjeong 15-gil, Yangsan-si, Gyeongsangnam-d', '01048592245', 'Alex', 'rlamminwoo@daum.net');
INSERT INTO RentalCompany VALUES('003', 'C', '9, Horyeoul-ro, Sejong-si', '01028315581', 'alan', 'rrlatkdbdbdb@paran.com');
INSERT INTO RentalCompany VALUES('004', 'D', 'Munhyeon-ro, Nam-gu, Busan', '01015215132', 'John', 'twogi@naver.com' );
INSERT INTO RentalCompany VALUES('005', 'E', 'Geomapyeong-ro, Wansan-gu, Jeonju-si, Jeollabuk-do', '01085523424', 'Noah', 'joowoo@daum.net' );
INSERT INTO RentalCompany VALUES('006', 'F', 'Cheonan-daero, Dongnam-gu, Cheonan-si, Chungcheongnam-do', '01088561111', 'Liam', 'ansung@gmail.com');
INSERT INTO RentalCompany VALUES('007', 'G', 'Bareun 7-gil, Sejong-si', '01022521124', 'James', 'ladygaga@naver.com' );
INSERT INTO RentalCompany VALUES('008', 'H', 'Jungdong-ro 248beon-gil, Bucheon-si, Gyeonggi-do', '01028585922', 'William', 'wooonejae@naver.com');
INSERT INTO RentalCompany VALUES('009', 'I', 'Keunumul-ro, Mapo-gu, Seoul', '01011159982', 'Luke', 'onesung@gmail.com');
INSERT INTO RentalCompany VALUES('010', 'J', 'Gyeongancheon-ro, Cheoin-gu, Yongin-si, Gyeonggi-do', '01095212345', 'Jack', 'jaeho@naver.com');
INSERT INTO RentalCompany VALUES('011', 'K', 'Gyeonggi-daero, Osan-si, Gyeonggi-do', '01012485944', 'Owen', 'chochocho@daum.net');
INSERT INTO RentalCompany VALUES('012', 'L', 'Gobong-ro, Ilsandong-gu, Goyang-si, Gyeonggi-do', '01058691234', 'Levi', 'imjung@gmail.net' );
