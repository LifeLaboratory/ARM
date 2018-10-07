-- добавление нового чата
with ins as (
     insert into "client"("name")
     values ('')
    ),
min_us as (
     select "id_user", count(*) filter (where "status" = '0') as "min" from "chat_list"
    )
insert into "chat_list"("id_company", "status", "date", "id_chat", "id_user")
select 1, 'Open', now()::timestamp + interval'7 h', ('{SALT}'::text || '|' || {id_client}::text)::text, (select "id_user" from min_us order by "min" limit 1)
returning "id_chat"