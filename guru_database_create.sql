create table category (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table ingredient (id bigint not null auto_increment, amount decimal(19,2), description varchar(255), recipe_id bigint, uom_id bigint, primary key (id)) engine=InnoDB
create table notes (id bigint not null auto_increment, recipe_notes longtext, recipe_id bigint, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, expiry_date datetime, token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table privilege (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table recipe (id bigint not null auto_increment, cook_time integer, description varchar(255), difficulty varchar(255), directions longtext, image longblob, prep_time integer, servings integer, source varchar(255), url varchar(255), notes_id bigint, primary key (id)) engine=InnoDB
create table recipe_category (recipe_id bigint not null, category_id bigint not null, primary key (recipe_id, category_id)) engine=InnoDB
create table role (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table roles_privileges (role_id bigint not null, privilege_id bigint not null) engine=InnoDB
create table unit_of_measure (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table user_account (id bigint not null auto_increment, email varchar(255), enabled bit not null, first_name varchar(255), is_using2fa bit not null, last_name varchar(255), password varchar(60), secret varchar(255), primary key (id)) engine=InnoDB
create table users_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB
create table verification_token (id bigint not null auto_increment, expiry_date datetime, token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
alter table ingredient add constraint FKj0s4ywmqqqw4h5iommigh5yja foreign key (recipe_id) references recipe (id)
alter table ingredient add constraint FK6iv5l89qmitedn5m2a71kta2t foreign key (uom_id) references unit_of_measure (id)
alter table notes add constraint FKdbfsiv21ocsbt63sd6fg0t3c8 foreign key (recipe_id) references recipe (id)
alter table password_reset_token add constraint FKns9q9f0f318uaoxiqn6lka9ux foreign key (user_id) references user_account (id)
alter table recipe add constraint FK37al6kcbdasgfnut9xokktie9 foreign key (notes_id) references notes (id)
alter table recipe_category add constraint FKqsi87i8d4qqdehlv2eiwvpwb foreign key (category_id) references category (id)
alter table recipe_category add constraint FKcqlqnvfyarhieewfeayk3v25v foreign key (recipe_id) references recipe (id)
alter table roles_privileges add constraint FK5yjwxw2gvfyu76j3rgqwo685u foreign key (privilege_id) references privilege (id)
alter table roles_privileges add constraint FK9h2vewsqh8luhfq71xokh4who foreign key (role_id) references role (id)
alter table users_roles add constraint FKt4v0rrweyk393bdgt107vdx0x foreign key (role_id) references role (id)
alter table users_roles add constraint FKci4mdvg1fmo9eqmwno1y9o0fa foreign key (user_id) references user_account (id)
alter table verification_token add constraint FK_VERIFY_USER foreign key (user_id) references user_account (id)
create table category (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table ingredient (id bigint not null auto_increment, amount decimal(19,2), description varchar(255), recipe_id bigint, uom_id bigint, primary key (id)) engine=InnoDB
create table notes (id bigint not null auto_increment, recipe_notes longtext, recipe_id bigint, primary key (id)) engine=InnoDB
create table password_reset_token (id bigint not null auto_increment, expiry_date datetime, token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
create table privilege (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table recipe (id bigint not null auto_increment, cook_time integer, description varchar(255), difficulty varchar(255), directions longtext, image longblob, prep_time integer, servings integer, source varchar(255), url varchar(255), notes_id bigint, primary key (id)) engine=InnoDB
create table recipe_category (recipe_id bigint not null, category_id bigint not null, primary key (recipe_id, category_id)) engine=InnoDB
create table role (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table roles_privileges (role_id bigint not null, privilege_id bigint not null) engine=InnoDB
create table unit_of_measure (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB
create table user_account (id bigint not null auto_increment, email varchar(255), enabled bit not null, first_name varchar(255), is_using2fa bit not null, last_name varchar(255), password varchar(60), secret varchar(255), primary key (id)) engine=InnoDB
create table users_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB
create table verification_token (id bigint not null auto_increment, expiry_date datetime, token varchar(255), user_id bigint not null, primary key (id)) engine=InnoDB
alter table ingredient add constraint FKj0s4ywmqqqw4h5iommigh5yja foreign key (recipe_id) references recipe (id)
alter table ingredient add constraint FK6iv5l89qmitedn5m2a71kta2t foreign key (uom_id) references unit_of_measure (id)
alter table notes add constraint FKdbfsiv21ocsbt63sd6fg0t3c8 foreign key (recipe_id) references recipe (id)
alter table password_reset_token add constraint FKns9q9f0f318uaoxiqn6lka9ux foreign key (user_id) references user_account (id)
alter table recipe add constraint FK37al6kcbdasgfnut9xokktie9 foreign key (notes_id) references notes (id)
alter table recipe_category add constraint FKqsi87i8d4qqdehlv2eiwvpwb foreign key (category_id) references category (id)
alter table recipe_category add constraint FKcqlqnvfyarhieewfeayk3v25v foreign key (recipe_id) references recipe (id)
alter table roles_privileges add constraint FK5yjwxw2gvfyu76j3rgqwo685u foreign key (privilege_id) references privilege (id)
alter table roles_privileges add constraint FK9h2vewsqh8luhfq71xokh4who foreign key (role_id) references role (id)
alter table users_roles add constraint FKt4v0rrweyk393bdgt107vdx0x foreign key (role_id) references role (id)
alter table users_roles add constraint FKci4mdvg1fmo9eqmwno1y9o0fa foreign key (user_id) references user_account (id)
alter table verification_token add constraint FK_VERIFY_USER foreign key (user_id) references user_account (id)
