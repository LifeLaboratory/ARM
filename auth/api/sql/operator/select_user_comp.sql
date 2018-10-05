-- По id_user проверяем существует ли пользователь/компания
select
  coalesce(
    (select exists(
      select 1
      from "users"
      where "id_user" = {}
      )
    ),
    (select exists(
      select 1
      from "company"
      where "id_company" = {}
      )
    )
) users