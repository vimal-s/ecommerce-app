insert into item (name, price, description) values ('Round Widget', 2.99, 'A widget that is round');
insert into item (name, price, description) values ('Square Widget', 1.99, 'A widget that is square');

insert into cart (id, total) values (1, 2.99);

insert into cart_items (cart_id, items_id) values (1, 1);

insert into user (username, password, cart_id)
            values ('testUser', '$2a$10$/bkY9wnmvUh2ngVuour0Gu6Mq/DOcHpQXfCRHMerXsaKH1pJjZR66', 1);

insert into user_order (id, user_id, total) values (1, 1, 2.99);

insert into user_order_items (user_order_id, items_id) values (1, 1);
