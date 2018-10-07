select "id_chat"
  , "id_chat" as "id_client"
  , "message" as "Text"
  , "data"::text as "Data"
  , "sender" as "Sender"
from "chat_message"
where "id_chat" = '{id_chat}'
order by "data" desc, id_message