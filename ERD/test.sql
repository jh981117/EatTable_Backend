

SELECT * FROM comment;
SELECT * FROM follow;
SELECT * FROM partner;
SELECT * FROM partnerattachment;
SELECT * FROM partnermenu;
SELECT * FROM partnermenuattachment;
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







