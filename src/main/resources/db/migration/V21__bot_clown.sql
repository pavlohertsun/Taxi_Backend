CREATE OR REPLACE FUNCTION get_user_data(user_id bigint)
RETURNS json AS $$
DECLARE
    user_data json;
BEGIN
    user_data := '{}';

    SELECT json_agg(u.*) INTO user_data
    FROM customers u
    WHERE u.id = user_id;

    user_data := user_data || (SELECT json_agg(trips.*)
                               FROM trips
                               WHERE trips.customer_id = user_id);

    user_data := user_data || (SELECT json_agg(support_requests.*)
                               FROM support_requests
                               WHERE support_requests.customer_id = user_id);

    RETURN user_data;
END;
$$ LANGUAGE plpgsql;
