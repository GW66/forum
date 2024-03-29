create table notification
(
  id bigint primary key auto_increment,
  notifier bigint not null,
  receiver bigint not null,
  outerId bigint not null,
  type int not null,
  gmt_create bigint not null,
  status int default 0 not null,
  notifier_name varchar(100) not null,
  outer_title varchar(256) not null
)