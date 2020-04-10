CREATE TABLE CARSERVICECENTER (
    CENTERID CHAR(8) NOT NULL,
    CENTERNAME CHAR(16) NOT NULL,
    CENTERADDRESS CHAR(128) NOT NULL,
    CENTERPHONENO CHAR(16) NOT NULL,
    CENTERMANAGER CHAR(16) NOT NULL,
    CENTERMANAGEREMAIL CHAR(48) NOT NULL,
    PRIMARY KEY(CENTERID)
); 


INSERT INTO CarServiceCenter VALUES('A111', 'Ateam', ' 292beon-gil, Nam-gu, Incheon', '021111111', 'aomg', 'aomg@naver.com' );
INSERT INTO CarServiceCenter VALUES('B222', 'Bteam', ' 56beon-gil, Nam-gu, Incheon', '022222222', 'gonzo', 'gonzo@naver.com' );
INSERT INTO CarServiceCenter VALUES('C333', 'Cteam', ' 4-gil, Jongno-gu, Seoul', '023333333', 'young', 'young@naver.com' );
INSERT INTO CarServiceCenter VALUES('D444', 'Dteam', '12-gil, Gwangjin-gu, Seoul', '024444444', 'melon', 'melon@naver.com' );
INSERT INTO CarServiceCenter VALUES('F555', 'Fteam', '11na-gil, Seongdong-gu, Seoul', '025555555', 'hotel', 'hotel@naver.com' );
INSERT INTO CarServiceCenter VALUES('E666', 'Eteam', 'Mokdongjungangnam-ro 7-gil, Yangcheon-gu, Seoul', '026666666', 'dok2', 'dok2@naver.com' );
INSERT INTO CarServiceCenter VALUES('F777', 'Fteam', 'Olympic-ro 83-gil, Gangdong-gu, Seoul', '027777777', 'jamezz', 'jamezz@naver.com' );
INSERT INTO CarServiceCenter VALUES('G888', 'Gteam', 'Sangam-ro 41-gil, Gangdong-gu, Seoul', '028888888', 'beenzino', 'beenzino@naver.com' );
INSERT INTO CarServiceCenter VALUES('H999', 'Hteam', '?Jungbu-daero, Bubal-eup, Icheon-si, Gyeonggi-do', '029999999', 'waves', 'waves@naver.com' );
INSERT INTO CarServiceCenter VALUES('I121', 'Iteam', 'Docheok-ro 765beon-gil, Docheok-myeon, Gwangju-si', '031111111', 'quiett', 'quiett@naver.com' );
INSERT INTO CarServiceCenter VALUES('J232', 'Jteam', '?Dongsan 2-gil, Paju-si, Gyeonggi-do', '032222222', 'chan', 'chan@naver.com' );
INSERT INTO CarServiceCenter VALUES('K343', 'Kteam', 'Bokjeong-ro, Sujeong-gu, Seongnam-si', '033333333', 'gmo', 'gmo@naver.com' );
