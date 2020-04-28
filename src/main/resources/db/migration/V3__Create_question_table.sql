create table question
(
	id int auto_increment,
	titile varchar(50),
	description text,
	gmt_create bigint,
	gmt_modified bigint,
	creator int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256),
	constraint question_pk
		primary key (id)
);

comment on column question.creator is 'the person who create this question';

comment on column question.comment_count is 'the number of the comment for this question';

comment on column question.view_count is 'the number of the view for this question';

comment on column question.like_count is 'the number of like for this question';

