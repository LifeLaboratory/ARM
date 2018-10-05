-- По id_user проверяем существует ли пользователь/компания
select
  coalesce(
    (select exists(
      select 1
      from "users"
      where "id_user" = {id_user}
      )
    ),
    (select exists(
      select 1
      from "company"
      where "id_company" = {id_company}
      )
    )
) users