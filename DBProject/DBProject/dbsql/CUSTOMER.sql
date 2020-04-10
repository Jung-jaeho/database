CREATE TABLE CUSTOMER (
    LICNUM CHAR(8) NOT NULL,
    CUSNAME CHAR(16) NOT NULL,
    CUSADDRESS CHAR(128) NOT NULL,
    CUSPHONE CHAR(16) NOT NULL,
    CUSEMAIL CHAR(48),
    LASTUSEDATE CHAR(12),
    LASTCARID CHAR(12),
    PRIMARY KEY(LICNUM)
); 


INSERT INTO CUSTOMER VALUES('12-523', 'iris', 'Dongmun-gil, Jindo-eup, Jindo-gun, Jeollanam-do', '01012341234', 'iris@naver.com', '20180311', '1111');
INSERT INTO CUSTOMER VALUES('11-552', 'zoe', 'Palma-ro, Gunsan-si, Jeollabuk-do', '01023452345', 'zoe@naver.com', '20180313', '1231');
INSERT INTO CUSTOMER VALUES('13-235', 'mila', 'Cheongnyong-ro, Seosu-myeon, Gunsan-si, Jeollabuk-do', '01034563456', 'mila@naver.com', '20180314', '1423');
INSERT INTO CUSTOMER VALUES('15-126', 'leon', 'Oehang-ro, Gunsan-si, Jeollabuk-do', '01045674567', 'leon@naver.com', '20180318', '1589');
INSERT INTO CUSTOMER VALUES('12-521', 'kai', 'Sinseol-ro, Gunsan-si, Jeollabuk-do', '01056785678', 'kai@naver.com', '20180320', '1809');
INSERT INTO CUSTOMER VALUES('09-882', 'eva', '?Sangnaun-ro, Gunsan-si, Jeollabuk-do', '01067896789', 'eva@naver.com', '20180322', '2100');
INSERT INTO CUSTOMER VALUES('13-222', 'toby', 'Daehak-ro, Gunsan-si, Jeollabuk-do', '01098769876', 'toby@naver.com', '20180328', '2122');
INSERT INTO CUSTOMER VALUES('14-955', 'judy', 'Julpo-gil, Julpo-myeon, Buan-gun, Jeollabuk-do', '01087658765', 'judy@naver.com', '20180331', '2222');
INSERT INTO CUSTOMER VALUES('13-551', 'roman', 'Hwasin 1-gil, Gochang-eup, Gochang-gun, Jeollabuk-do', '01076547654', 'roman@naver.com', '20180511', '2233');
INSERT INTO CUSTOMER VALUES('11-290', 'ben', '?Hoban 8-gil, Deokjin-gu, Jeonju-si, Jeollabuk-do', '01065436543', 'ben@naver.com', '20180411', '3210');
INSERT INTO CUSTOMER VALUES('12-421', 'lola', 'Deokjin-gu, Jeonju-si, Jeollabuk-do', '01054325432', 'lola@naver.com', '20180318', '3300');
INSERT INTO CUSTOMER VALUES('16-232', 'bella', 'Danyang-ro, Maepo-eup, Danyang-gun', '01043214321', 'bella@naver.com', '20180322', '3415');
