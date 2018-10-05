-- Обновить статус оператора
update "users"
set "status" = '{status}'
where
    "id_user" = {id_user}