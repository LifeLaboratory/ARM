-- Обновить данные об операторе без пароля и логина
update "users"
set "name" = '{Name}'
where
    "id_user" = {id_user}
returning "id_user"