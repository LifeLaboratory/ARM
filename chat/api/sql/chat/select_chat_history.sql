select "id_chat"
  , "message" as "Message"
  , "data" as "Data"
  , "sender" as "Sender"
from "chat_message"
where "id_chat" = {id_chat}
order by "data" desc, id_message