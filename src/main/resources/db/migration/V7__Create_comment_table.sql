create table comment
(
  id bigint primary key auto_increment,
  parent_id bigint not null,
  type bigint not null,
  commentator int not null,
  gmt_create bigint not null,
  gmt_modified bigint not null,
  like_count bigint default 0 not null,
  content VARCHAR(1024) null
)