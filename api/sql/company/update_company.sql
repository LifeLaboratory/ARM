-- Обновить данные о компании без пароля и логина
update "company"
set "name" = '{Name}',
    "description" = '{Description}'
where
    "id_company" = {id_company}