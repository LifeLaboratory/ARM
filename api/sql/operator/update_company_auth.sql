-- Обновляет данные для авторизации оператора
update "users"
set "login" = '{login}',
    "password" = '{password}'
where
    "id_user" = {id_user}