-- Обновить статус оператора
update "users"
set "status" = '{Status}'
where
    "id_user" = {id_user}