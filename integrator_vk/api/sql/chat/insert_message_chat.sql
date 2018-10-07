insert into "chat_message"("id_chat", "message", "data", "sender")
select ('{SALT}'::text || '|' || '{id_client}'::text)::text
, '{Message}'
    , case when '{Sender}' = 'Client'
           then '{Data_message}'::timestamp
           else '{Data_message}'::timestamp + interval'7 h' end, '{Sender}'
returning "id_message"