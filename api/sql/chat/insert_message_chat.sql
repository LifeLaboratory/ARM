insert into "chat_message"("id_chat", "message", "data", "sender")
select ({SALT}::text || '|' || {id_client}::text)::text
  , {Message}, to_timestamp({Data_message}), {Sender}
