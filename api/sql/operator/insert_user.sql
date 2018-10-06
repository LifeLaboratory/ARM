-- добавление данных оператора
with ins as (
insert into "users"("name", "login", "password", "status")
  select '{Name}', '{Login}', '{Password}', 'Не в сети'
  where exists(select 1 from "company" where "id_company" = {id_company})
  returning "id_user"
)
insert into "user_company"("id_user", "id_company")
select "id_user", {id_company} from ins
returning "id_user"