  select count(*) filter (where "accept" = '1') as "Close"
   , count(*) filter (where "accept" = '0') as "Open"
   , count(*) as "All"
  from "chat_list"