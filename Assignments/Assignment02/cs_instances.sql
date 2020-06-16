--------------------------------------------------------------------
-- cs_instances.sql - insert row instances for creature/skill database
--
-- Created - Paul J. Wagner, 02/21/2019, for CS 260 Spring 2019
--------------------------------------------------------------------

-- creature records
insert 
into Creature (C_id, C_name, C_type, reside_T_id) 
values        (1, 'Bannon', 'Person', 'p');

insert 
into Creature (C_id, C_name, C_type, reside_T_id) 
values        (2, 'Myers', 'Person', 'a');

insert 
into Creature (C_id, C_name, C_type, reside_T_id) 
values        (3, 'Neff', 'Person', 'b');

insert 
into Creature (C_id, C_name, C_type, reside_T_id) 
values        (4, 'Neff', 'Person', 'c');

insert 
into Creature (C_id, C_name, C_type, reside_T_id) 
values        (5, 'Mieska', 'Person', 'd');

insert 
into Creature (C_id, C_name, C_type, reside_T_id) 
values        (6, 'Carlis', 'Person', 'p');

insert 
into Creature (C_id, C_name, C_type, reside_T_id)
values        (7, 'Kermit', 'Frog', 'h');

insert 
into Creature (C_id, C_name, C_type, reside_T_id)
values        (8, 'Godzilla', 'Monster', 't');



-- skill records
insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('F', 'Float', 0.5, 'b');

insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('S', 'Swim', 0.7, 'b');

insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('A', 'Adapt', 0.1, 't');

insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('W', 'Walk on Water', 0.9, 'em');

insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('G', 'Gargle', 0.2, 'p');

insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('C', 'Code', 0.5, 'b');

insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('D', 'Design', 0.8, 'p');

insert
into Skill (S_code, S_desc, S_weight, origin_T_id)
values     ('T', 'Test', 0.7, 't');


-- achievement records
insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (1, 'F', 1, 'a');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (1, 'S', 3, 'a');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (1, 'G', 3, 'p');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (2, 'F', 3, 'b');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (3, 'F', 2, 'b');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (3, 'G', 1, 'p');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (4, 'F', 2, 'c');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (4, 'S', 2, 'c');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (5, 'G', 3, 'd');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (7, 'S', 1, 's');

insert
into Achievement (C_id, S_Code, score, test_T_id) 
values    (8, 'A', 1, 't');


-- aspiration records
insert
into Aspiration (C_id, S_Code, score, test_T_id)
values    (1, 'F', 1, 'a');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (1, 'S', 3, 'a');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (1, 'G', 1, 'be');

insert
into Aspiration (C_id, S_code, score)
values    (2, 'F', 3);

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (3, 'F', 2, 'b');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (3, 'G', 2, 'be');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (4, 'S', 2, 'c');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (5, 'G', 3, 'd');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (6, 'G', 3, 'e');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (7, 'S', 3, 's');

insert
into Aspiration (C_id, S_code, score, test_T_id)
values    (8, 'A', 1, 't');


-- town records
insert
into Town (T_id, T_name, Mayor_C_id, Biggest_Rival_T_id)
values    ('a', 'Anoka', 2, 'be');

insert
into Town (T_id, T_name, Biggest_Rival_T_id)
values    ('b', 'Bemidji', 'd');

insert
into Town (T_id, T_name, Biggest_Rival_T_id)
values    ('be', 'Blue Earth', 'c');

insert
into Town (T_id, T_name, Biggest_Rival_T_id)
values    ('c', 'Chaska', 'a');

insert
into Town (T_id, T_name, Biggest_Rival_T_id)
values    ('d', 'Duluth', 'b');

insert
into Town (T_id, T_name, Biggest_Rival_T_id)
values    ('e', 'Edina', 'h');

insert
into Town (T_id, T_name)
values    ('em', 'Embarrass');

insert
into Town (T_id, T_name, Biggest_Rival_T_id)
values    ('h', 'Hollywood', 'p');

insert
into Town (T_id, T_name, Biggest_Rival_T_id)
values    ('p', 'Philly', 'd');

insert
into Town (T_id, T_name)
values    ('s', 'Swampville');

insert
into Town (T_id, T_name)
values    ('t', 'Tokyo');

-- job records
insert
into Job (J_code, J_desc)
values ('Survivor', 'can stay alive in water');

insert
into Job (J_code, J_desc)
values ('Beach_Bum', 'lives cheaply');

insert
into Job (J_code, J_desc)
values ('Brother_In_Law', 'does nothing');

insert
into Job (J_code, J_desc)
values ('SWDeveloper', 'develops quality software');


-- jobskill records
insert
into JobSkill (J_code, S_code)
values ('Survivor', 'F');

insert
into JobSkill (J_code, S_code)
values ('Survivor', 'S');

insert
into JobSkill (J_code, S_code)
values ('Beach_Bum', 'G');

insert
into JobSkill (J_code, S_code)
values ('SWDeveloper', 'C');

insert
into JobSkill (J_code, S_code)
values ('SWDeveloper', 'D');

insert
into JobSkill (J_code, S_code)
values ('SWDeveloper', 'T');

