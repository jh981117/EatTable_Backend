

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
insert into userrole (userId, roleId) values (1,3);

insert into role (id, roleName) values
    (1, 'ROLE_MEMBER'),
    (2, 'ROLE_PARTNER'),
    (3, 'ROLE_ADMIN');




#----------------------- 승빈 ----------------------------------------------
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(6,'이름6','가게6','010-1111-1111','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(7,'이름7','가게7','010-1111-2222','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(8,'이름8','가게8','010-1111-3333','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(9,'이름9','가게9','010-1111-4444','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(10,'이름10','가게10','010-1111-5555','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(11,'이름11','가게11','010-1111-6666','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(12,'이름12','가게12','010-1111-7777','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(13,'이름13','가게13','010-1111-8888','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(14,'이름14','가게14','010-1111-9999','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(15,'이름15','가게15','010-1111-1010','OPEN_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(16,'이름16','가게16','010-1111-1212','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(17,'이름17','가게17','010-1111-1313','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(18,'이름18','가게18','010-1111-1414','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(19,'이름19','가게19','010-1111-1515','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(20,'이름20','가게20','010-1111-1616','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(21,'이름21','가게21','010-1111-1717','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(22,'이름22','가게22','010-1111-1818','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(23,'이름23','가게23','010-1111-1919','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(24,'이름24','가게24','010-1111-2020','CLOSE_READY');
insert into partnerreq(id,managerName,storeName,phone,partnerReqState)
values(25,'이름25','가게25','010-1111-2121','CLOSE_READY');

# insert into partner(id,storeName,partnerName,partnerPhone,storePhone)
# values (1,'가게6','이름6','010-1111-1111','031-1111-1111');
# insert into partner(id,storeName,partnerName,partnerPhone,storePhone)
# values (2,'가게7','이름7','010-1111-2222','031-1111-2222');

