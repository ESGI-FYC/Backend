create sequence product_id_seq start 109 increment 1;
create sequence users_id_seq start 4 increment 1;
create sequence order_item_seq start 12 increment 1;
create sequence orders_seq start 6 increment 1;

create table order_item
(
    id         int8 PRIMARY KEY NOT NULL,
    amount     int8,
    quantity   int8,
    product_id int8
);

create table orders
(
    id           int8 PRIMARY KEY NOT NULL,
    address      varchar(255),
    city         varchar(255),
    date         date,
    email        varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    phone_number varchar(255),
    post_index   int4,
    total_price  float8
);

create table orders_order_items
(
    order_id       int8 not null,
    order_items_id int8 not null
);

create table product
(
    id                     int8 PRIMARY KEY NOT NULL,
    country                varchar(255),
    description            varchar(255),
    filename               varchar(255),
    fragrance_base_notes   varchar(255),
    fragrance_middle_notes varchar(255),
    fragrance_top_notes    varchar(255),
    product_gender         varchar(255),
    product_title          varchar(255),
    productr               varchar(255),
    price                  int4,
    type                   varchar(255),
    volume                 varchar(255),
    year                   int4,
    product_rating         float8
);

create table product_reviews
(
    product_id int8 not null,
    reviews_id int8 not null
);

create table review
(
    id      int8 PRIMARY KEY NOT NULL,
    author  varchar(255),
    date    date,
    message varchar(255),
    rating  int4
);

create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table users
(
    id                  int8 PRIMARY KEY NOT NULL,
    activation_code     varchar(255),
    active              boolean not null,
    address             varchar(255),
    city                varchar(255),
    email               varchar(255),
    first_name          varchar(255),
    last_name           varchar(255),
    password            varchar(255),
    password_reset_code varchar(255),
    phone_number        varchar(255),
    post_index          varchar(255),
    provider            varchar(255),
);

alter table if exists orders_order_items add constraint UK_9d47gapmi35omtannusv6btu3 unique (order_items_id);
alter table if exists product_reviews add constraint UK_gp5u9cs9leiwnbh2rhn27e2w7 unique (reviews_id);
alter table if exists order_item add constraint FKst073lwr6yongjsmgaravadre foreign key (product_id) references product;
alter table if exists orders_order_items add constraint FK7nw03p9mxq154wvbsonaq0qrw foreign key (order_items_id) references order_item;
alter table if exists orders_order_items add constraint FK3l8rktw0f4w5t6tift31e2d7c foreign key (order_id) references orders;
alter table if exists product_reviews add constraint FKq51iuslnvq3nw8teocq9y7ag8 foreign key (reviews_id) references review;
alter table if exists product_reviews add constraint FK7k3k0ru1omu7xdtdamtrl276 foreign key (product_id) references product;
alter table if exists user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;
