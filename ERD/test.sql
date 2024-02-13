

SELECT * FROM comment;
SELECT * FROM follow;
SELECT * FROM partner;
SELECT * FROM partnerattachment;
SELECT * FROM partnermenu;
SELECT * FROM storereview;
SELECT * FROM partnerreviewattachment;
SELECT * FROM role;
SELECT * FROM userrole;
SELECT * FROM user;
SELECT * FROM partnerreq;
SELECT * FROM reviewlike;
SELECT * FROM waiting;



#
# insert into user (id, username,birthdate,activated, password,nickName, name, bio , email)
# values (2, 'admin', '910309','','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi' ,'송', 'admin', 'ㅇㅇㄴ', 'mino030@naver.com');

# insert into userrole (userId, roleId) values (2,1);
# insert into userrole (userId, roleId) values (1,3);
#
# insert into userrole (userId, roleId) values (3,2);

insert into role (id, roleName) values
    (1, 'ROLE_MEMBER'),
    (2, 'ROLE_PARTNER'),
    (3, 'ROLE_ADMIN');



# #---------------------재환----------------------------------------------
# insert into partner (lat, lng, id,  userId, area, corkCharge, favorite, storeInfo, openTime, parking, partnerName, partnerPhone, partnerState, reserveInfo, storeName, storePhone, tableCnt, zipCode)
# values (37.5283169, 126.9294254, 1,  2, "서울", 'TRUE', "일식", "돈까스 판매", 2024-02-02, 'TRUE', "짱구", "010-1234-1234", 'TRUE', "이게뭐지", "돈까스집", "010-7777-7777", 20, "01241421");
#
# insert into partnerattachment (isImage, id, partnerId, description, filename, imageUrl, sourcename)
# values (1, 1, 1, '하이', '파일네임', '이미지URL', 'sourcename');
#




# --------유저---------------------------------------------------------

insert into user (id,username,createdAt ,password,name, nickName,birthdate,email,phone,activated,temperature,profileImageUrl) values

(1,'user1','2024-02-13 13:48:28.177494','$2a$10$WiGFjlDZzzmNuFWDmv4jsOK56flmJpmWLe1ATuiiMZHChQ8UXdPfG' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(2,'user2','2024-02-13 13:48:28.177494','$2a$10$Mh6/AJAbQ29bHY.X5iZqFuZLr/JlJ7Wf1AxdsiIZgP5Y.ukLBvL0i' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(3,'user3','2024-02-14 13:48:28.177494','$2a$10$pk2f0TiYdi94YbsL7HN34ujY6A8REMn/6CusdvsbZZv2K183s/Lfq' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(4,'user4','2024-02-14 13:48:28.177494','$2a$10$mD/DNyndOu94zmvkwLGgQuG7AzfD3UirxagramZoe5YT87Ifa0.6y' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(5,'user5','2024-02-15 13:48:28.177494','$2a$10$.3rd2iOf9tVWqt.k2tHtRe0gTXUx.9Pbv.8d/al2/nnRnpVJcK.Q2' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(6,'user6','2024-02-15 13:48:28.177494','$2a$10$AbfaPhMEsMBc2UWGlwxUs.3FEEG5Ghz22AM136dz4EBMBcdHHGA2m' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(7,'user7','2024-02-15 13:48:28.177494','$2a$10$4i6V33LcygnNnTU5ygF4n.jEFFTQI2c6bOxsrfljmdnGwkM7PPIxq' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(8,'user8','2024-02-15 13:48:28.177494','$2a$10$dATltd.bTWXmN1EjMFuW5e3BS2t6irG1Vq98SHaxVAHIzE3063sd2' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(9,'user9','2024-02-16 13:48:28.177494','$2a$10$PND2lcJgIR8C2q6a01iPlenAx4UWDkzLboPOUb9HYXg7AdYBDT0yS' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(10,'user10','2024-02-16 13:48:28.177494','$2a$10$o7pAABDQ/Ghpc3GfSB3qdu9eErBDIDBHczw2SXW5v.YfMcNtFlhAu' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(11,'user11','2024-02-17 13:48:28.177494','$2a$10$k.3xPwvk3uScocdDII6pr.Nd2aoTENDzJcaeKfhdFd.1ddsoI.CS6' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(12,'user12','2024-02-18 13:48:28.177494','$2a$10$vZIFoPn572QrY5SGJwd8/ebg3XBWFrHUhy9wNIixdXcWp0ShLVE3a' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(13,'user13','2024-02-19 13:48:28.177494','$2a$10$Nb1.GGSccUbyUF24IV2sTe5oj0xgnXTRSE./mHNFobi.5j0wynISC' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(14,'user14','2024-02-19 13:48:28.177494','$2a$10$pBbo8.J3Ff2IxSebDR.v0.aSK58qnOCqqMRyjSXV4Q7kqTHSxQpLW' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(15,'user15','2024-02-20 13:48:28.177494','$2a$10$QyGEonrSoriwKneoQrdbOuWNYbM.BR8o/VWnBdldScP/OgztmdQIu' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(16,'user16','2024-02-21 13:48:28.177494','$2a$10$82KGMdI8qYjPsz0JiAt6QuAptTO9QAm1J6sBjOzLvlQVADpYHp6Fi' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(17,'user17','2024-02-21 13:48:28.177494','$2a$10$LCGhPEXqHEa/rIZThy9SOOAdtZ7xEHuoPmtIo5XCiu8FGBTRL.yWq' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(18,'user18','2024-02-21 13:48:28.177494','$2a$10$lL3X.esOof03kubHUrjE3uQ9oGxBNqntr0mMUZawhS9YyJFAR6ul2' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(19,'user19','2024-02-22 13:48:28.177494','$2a$10$FJ3095fmIqRMNPSd4OpouOWPmncVZUkyoulNg1/K5jhVFiJ/Znkfa' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(20,'user20','2024-02-23 13:48:28.177494','$2a$10$h6eUWnTYZJbdsHkr5fgzIO1wXLcx02bKbjrcrJxfvrjhn7WVkOcTe' , '송','송송','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png'),
(21,'admin','2024-02-23 13:48:28.177494','$2a$10$qkmCAhzHcV1zC9I2TiUz5eCWobIKTEG3Vzlej5jRAFv0JqaSuYWxS' , '관리자','관리자','910309','email@email','01000000000',1,'10','https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png');






# ------- 권한 ------------------------------------------
insert into userrole (id, roleId, userId) VALUES
        (1, 1, 1),
        (2, 1, 2),
        (3, 1, 3),
        (4, 1, 4),
        (5, 1, 5),
        (6, 1, 6),
        (7, 1, 7),
        (8, 1, 8),
        (9, 1, 9),
        (10, 1, 10),
        (11, 1, 11),
        (12, 1, 12),
        (13, 1, 13),
        (14, 1, 14),
        (15, 1, 15),
        (16, 1, 16),
        (17, 1, 17),
        (18, 1, 18),
        (19, 1, 19),
        (20, 1, 20),
        (21, 1, 21),
        (22, 3, 21),
        (1, 2, 1),
        (2, 2, 2),
        (3, 2, 3),
        (4, 2, 4),
        (5, 2, 5),
        (6, 2, 6),
        (7, 2, 7),
        (8, 2, 8),
        (9, 2, 9),
        (10,2, 10),
        (11, 2, 11),
        (12, 2, 12),
        (13, 2, 13),
        (14, 2, 14),
        (15, 2, 15),
        (16, 2, 16),
        (17, 2, 17),
        (18, 2, 18),
        (19, 2, 19),
        (20, 2, 20);



#----------------------- 승빈 ----------------------------------------------
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(6,'이름6','가게6','010-1111-1111','OPEN_READY','2024-02-13 13:48:28.177494',1);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(7,'이름7','가게7','010-1111-2222','OPEN_READY','2024-02-13 13:48:28.177494',2);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(8,'이름8','가게8','010-1111-3333','OPEN_READY','2024-02-13 13:48:28.177494',3);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(9,'이름9','가게9','010-1111-4444','OPEN_READY','2024-02-13 13:48:28.177494',4);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(10,'이름10','가게10','010-1111-5555','OPEN_READY','2024-02-13 13:48:28.177494',5);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(11,'이름11','가게11','010-1111-6666','OPEN_READY','2024-02-13 13:48:28.177494',6);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(12,'이름12','가게12','010-1111-7777','OPEN_READY','2024-02-13 13:48:28.177494',7);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(13,'이름13','가게13','010-1111-8888','OPEN_READY','2024-02-13 13:48:28.177494',8);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(14,'이름14','가게14','010-1111-9999','OPEN_READY','2024-02-13 13:48:28.177494',9);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(15,'이름15','가게15','010-1111-1010','OPEN_READY','2024-02-13 13:48:28.177494',10);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(16,'이름16','가게16','010-1111-1212','CLOSE_READY','2024-02-13 13:48:28.177494',11);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(17,'이름17','가게17','010-1111-1313','CLOSE_READY','2024-02-13 13:48:28.177494',12);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(18,'이름18','가게18','010-1111-1414','CLOSE_READY','2024-02-13 13:48:28.177494',13);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(19,'이름19','가게19','010-1111-1515','CLOSE_READY','2024-02-13 13:48:28.177494',14);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(20,'이름20','가게20','010-1111-1616','CLOSE_READY','2024-02-13 13:48:28.177494',15);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(21,'이름21','가게21','010-1111-1717','CLOSE_READY','2024-02-13 13:48:28.177494',16);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(22,'이름22','가게22','010-1111-1818','CLOSE_READY','2024-02-13 13:48:28.177494',17);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(23,'이름23','가게23','010-1111-1919','CLOSE_READY','2024-02-13 13:48:28.177494',18);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(24,'이름24','가게24','010-1111-2020','CLOSE_READY','2024-02-13 13:48:28.177494',19);
insert into partnerreq(id,managerName,storeName,phone,partnerReqState,createdAt,userId)
values(25,'이름25','가게25','010-1111-2121','CLOSE_READY','2024-02-13 13:48:28.177494',20);





#--------업체--------------------------------------------


insert into partner (createdAt, updatedAt, area, lat, lng, zipCode, corkCharge, dog, favorite, openTime, parking, partnerName, partnerPhone, partnerState, reserveInfo, storeInfo, storeName, storePhone, tableCnt, viewCnt, userId) VALUES
    ('2024-02-23 13:48:28.177494','2024-02-12 13:48:28.177494','서울특별시 강남역사거리','37.4979052','127.0275777','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','강남김밥','11111111','20','102',1),
    ('2024-02-23 13:48:28.177494','2024-02-12 13:48:28.177494','서울특별시 서초역사거리','37.4917945','127.0076354','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','서초김밥','11111111','20','10',2),
    ('2024-02-23 13:48:28.177494','2024-02-12 13:48:28.177494','서울특별시 서초구 방배동','37.4794939','126.9931207','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','방배김밥','11111111','20','12',3),
    ('2024-02-23 13:48:28.177494','2024-02-13 13:48:28.177494','서울특별시 강남구 역삼동','37.5000776','127.0385419','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','역삼김밥','11111111','20','20',4),
    ('2024-02-23 13:48:28.177494','2024-02-13 13:48:28.177494','서울특별시 사당사거리','37.4765181','126.9816384','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','사당김밥','11111111','20','50',5),
    ('2024-02-23 13:48:28.177494','2024-02-14 13:48:28.177494','서울특별시 신도림사거리','37.510179','126.8903686','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','신도림김밥','11111111','20','1000',6),
    ('2024-02-23 13:48:28.177494','2024-02-14 13:48:28.177494','서울특별시 신림사거리','37.4631968','126.9358124','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','신림김밥','11111111','20','2000',7),
    ('2024-02-23 13:48:28.177494','2024-02-15 13:48:28.177494','서울특별시 봉천사거리','37.4779671','126.9534883','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','봉천김밥','11111111','20','150',8),
    ('2024-02-23 13:48:28.177494','2024-02-15 13:48:28.177494','서울특별시 압구정사거리','37.5271478','127.0334517','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','압구정김밥','11111111','20','5',9),
    ('2024-02-23 13:48:28.177494','2024-02-15 13:48:28.177494','서울특별시 종로사거리','37.5949159','126.977339','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','종로김밥','11111111','20','9',10),
    ('2024-02-23 13:48:28.177494','2024-02-15 13:48:28.177494','서울특별시 용산사거리','37.5313805','126.9798839','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','용산김밥','11111111','20','10',11),
    ('2024-02-23 13:48:28.177494','2024-02-15 13:48:28.177494','서울특별시 동서울사거리','37.534385','127.094045','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','동서울김밥','11111111','20','30',12),
    ('2024-02-23 13:48:28.177494','2024-02-16 13:48:28.177494','서울특별시 잠실사거리','37.5067945','127.0830482','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','잠실김밥','11111111','20','12',13),
    ('2024-02-23 13:48:28.177494','2024-02-17 13:48:28.177494','서울특별시 건대사거리','37.5399286','127.0706001','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','건대김밥','11111111','20','40',14),
    ('2024-02-23 13:48:28.177494','2024-02-17 13:48:28.177494','서울특별시 홍대사거리','37.5551463','126.9215309','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','홍대김밥','11111111','20','70',15),
    ('2024-02-23 13:48:28.177494','2024-02-17 13:48:28.177494','서울특별시 성북사거리','37.6056991','127.0175664','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','성북김밥','11111111','20','40',16),
    ('2024-02-23 13:48:28.177494','2024-02-17 13:48:28.177494','서울특별시 불광사거리','37.6191594','126.9355669','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','불광김밥','11111111','20','35',17),
    ('2024-02-23 13:48:28.177494','2024-02-18 13:48:28.177494','서울특별시 독립문사거리','37.5718671','126.9566937','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','독립문김밥','11111111','20','50',18),
    ('2024-02-23 13:48:28.177494','2024-02-18 13:48:28.177494','서울특별시 연신내사거리','37.6188607','126.9213347','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','연신내김밥','11111111','20','70',19),
    ('2024-02-23 13:48:28.177494','2024-02-18 13:48:28.177494','서울특별시 목동사거리','37.5360367','126.8745334','12312',TRUE,TRUE,'한식','24시',TRUE,'송송','11111111111','TRUE','아무렇게나','하하하하하','목동김밥','11111111','20','65',20);




#--------파트너 어테치먼트-----------------------------


insert into partnerattachment (filename, imageUrl, isImage, partnerId) VALUES
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/s25377/25377_2232815282859189.png?detail750',1,1),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/s1pE_2hl3vjLdnFYCkRmKcQ/1pe_2hl3vjldnfyckrmkcq_2361410350586042.jpg?detail750',1,2),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sjdTkAxJi8e1X02OCeQjdOA/jdtkaxji8e1x02oceqjdoa_237512113808579.png?detail750',1,3),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sR1B6qa4fT537GjL6KO9bHg/r1b6qa4ft537gjl6ko9bhg_2371016411290157.jpg?detail750',1,4),
    ('1','https://ugc-images.catchtable.co.kr/catchtable/shopinfo/sBBiBFMN4TMKGS_wxWHzQ1A/46fc93b0e6d5460aabfbbdfa3b6c262f?detail750',1,5),
    ('1','https://ugc-images.catchtable.co.kr/catchtable/shopinfo/spc8XLqQkwoPvQVvHrKby7A/c74b4710891749ba9a0c5f60e184c20d?detail750',1,6),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sONn32WCV9edJm7OxF_hcqA/onn32wcv9edjm7oxf_hcqa_2371317362857540.jpg?detail750',1,7),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sMw5J2WVI13FMCvZ_siAqmA/mw5j2wvi13fmcvz_siaqma_2372011243524932.jpeg?detail750',1,8),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/smj1t-yFX4rH6vDS79Te31w/mj1t-yfx4rh6vds79te31w_236210554046226.jpg?detail750',1,9),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sTkDM9-Ou_h6pSK4midndzg/tkdm9-ou_h6psk4midndzg_2342510552156345.jpg?detail750',1,10),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/s5VQV0VsuaF0fxLMB-RQvFQ/5vqv0vsuaf0fxlmb-rqvfq_2342511014291923.jpg?detail750',1,11),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/si3ed949noo8-IQGq7ZIZpA/i3ed949noo8-iqgq7zizpa_2342511071163551.jpg?detail750',1,12),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/suAZlG7_IYLQkV1KhMTqQDA/uazlg7_iylqkv1khmtqqda_2381017503172438.jpg?detail750',1,13),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sSjFHpIJgypcTxjnH3oh19A/sjfhpijgypctxjnh3oh19a_2372618162731821.jpg?detail750',1,14),
    ('1','https://ugc-images.catchtable.co.kr/catchtable/shopinfo/sjcUoAFBvEdAtrHuRG0CHOA/233eef6764a941dfb258ce2b6c288d92?detail750',1,15),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sEIoyVfSBqry0ED_oqS6QtQ/eioyvfsbqry0ed_oqs6qtq_2351617495520990.jpeg?detail750',1,16),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sGLE9cw-FitTFZEhOEdUT_g/gle9cw-fittfzehoedut_g_2351215074761485.jpeg?detail750',1,17),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/snUVjSSlp0uXVetuDgQ-eWg/nuvjsslp0uxvetudgq-ewg_2352511164355747.jpg?detail750',1,18),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sRpUFfIelZApNTQFn7_EkTQ/rpuffielzapntqfn7_ektq_2361617333044950.jpg?detail750',1,19),
    ('1','https://image.toast.com/aaaaaqx/catchtable/shopinfo/sqTUWbsiVKYGXcAsXwLSh1Q/qtuwbsivkygxcasxwlsh1q_236716303859638.jpeg?detail750',1,20);