-- Получить информацию о компании
select "name" as "Name"
  , "description" as "Description"
from "company"
where "id_company" = {id_company}