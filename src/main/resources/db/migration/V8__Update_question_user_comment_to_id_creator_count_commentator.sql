alter table question modify id bigint auto_increment;
alter table question modify creator bigint not null;
alter table question modify comment_count bigint default 0 not null;
alter table question modify view_count bigint default 0 not null;
alter table question modify like_count bigint default 0 not null;
alter table user modify id bigint auto_increment;
alter table comment modify commentator bigint not null;