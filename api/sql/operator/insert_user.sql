-- добавление данных оператора
with ins as (
insert into "users"("name", "login", "password", "status")
  VALUES ('{Name}', '{Login}', '{Password}', 'Не в сети')
        returning "id_user"
)
insert into "user_company"("id_user", "id_company")
select "id_user", {id_company} from ins