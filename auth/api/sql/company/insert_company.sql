-- добавление компании, возвращает UUID сессии
with ins as (
insert into "company"("name", "description", "login", "password")
VALUES ('{Name}', '{Description}', '{Login}', '{Password}')
returning "id_company"
)
insert into "session"("session", "id_company")
select md5(random()::text || clock_timestamp()::text)::uuid, "id_company" from ins
returning "session"