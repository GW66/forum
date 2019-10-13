alter table question modify comment_count int(11) default 0 not null;
alter table question modify view_count int(11) default 0 not null;
alter table question modify like_count int(11) default 0 not null;