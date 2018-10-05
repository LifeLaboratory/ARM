-- Обновить данные об операторе без пароля и логина
update "users"
set "name" = '{name}'
where
    "id_user" = {id_user}