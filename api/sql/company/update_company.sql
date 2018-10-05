-- Обновить данные о компании без пароля и логина
update "company"
set "name" = '{name}',
    "description" = '{description}'
where
    "id_company" = {id_company}