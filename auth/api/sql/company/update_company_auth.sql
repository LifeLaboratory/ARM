-- Обновляет данные для авторизации компании
update "company"
set "login" = '{Login}',
    "password" = '{Password}'
where
    "id_company" = {id_company}
returning "id_company"