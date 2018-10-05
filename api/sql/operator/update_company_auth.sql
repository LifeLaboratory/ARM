-- Обновляет данные для авторизации оператора
update "users"
set "login" = '{Login}',
    "password" = '{Password}'
where
    "id_user" = {id_user}