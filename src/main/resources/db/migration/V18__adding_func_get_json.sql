CREATE OR REPLACE FUNCTION get_user_data(user_id bigint)
RETURNS json AS $$
DECLARE
    user_data json;
BEGIN
    SELECT json_agg(u.*) INTO user_data
    FROM users u
    WHERE u.id = user_id;

    SELECT json_agg(trips.*) INTO user_data
    FROM trips
    WHERE trips.user_id = user_id;

    SELECT json_agg(support_requests.*) INTO user_data
    FROM support_requests
    WHERE support_requests.user_id = user_id;

    RETURN user_data;
END;
$$ LANGUAGE plpgsql;
