insert into "session"("session", "id_user") values (
md5(random()::text || clock_timestamp()::text)::uuid
, {}
)