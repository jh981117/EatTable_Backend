

SELECT * FROM comment;
SELECT * FROM follow;
SELECT * FROM partner;
SELECT * FROM partner_attachment;
SELECT * FROM partner_menu;
SELECT * FROM partner_menu_attachment;
SELECT * FROM store_review;
SELECT * FROM partner_review_attachment;
SELECT * FROM role;
SELECT * FROM user_role;
SELECT * FROM user;
SELECT * FROM user_attachment;
SELECT * FROM partner_req;
SELECT * FROM review_like;
SELECT * FROM waiting;




insert into user (id, username, PASSWORD, name, bio)
values (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);



insert into role (id, role_name) values (1, 'EAT_MEMBER');
insert into role (id, role_name) values (1, 'EAT_ADMIN');