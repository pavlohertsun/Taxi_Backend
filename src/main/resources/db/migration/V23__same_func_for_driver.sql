CREATE OR REPLACE FUNCTION get_driver_data(driver_id bigint)
RETURNS json AS $$
DECLARE
    driver_data json;
BEGIN
    SELECT json_build_object(
        'driver', json_build_object(
            'name', d.name,
            'surname', d.surname,
            'email', d.email,
            'phone_number', d.phone_number,
            'balance', d.balance,
            'rating', r.rating,
            'trips_count', r.trips_count
        ),
        'trips', (
            SELECT json_agg(
                json_build_object(
                    'trip', json_build_object(
                        'start_time', t.start_time,
                        'end_time', t.end_time,
                        'start_point', t.start_point,
                        'end_point', t.end_point,
                        'price', t.price,
                        'description', t.description,
                        'customer', json_build_object(
                            'name', c.name
                        )
                    )
                )
            )
            FROM trips t
            JOIN customers c ON t.customer_id = c.id
            WHERE d.id = t.driver_id
        ),
        'support_requests', (
            SELECT json_agg(
                json_build_object(
                    'request', json_build_object(
                        'date', s.date,
                        'request', s.request,
                        'response', s.response
                    )
                )
            )
            FROM support_requests s
            WHERE d.id = s.driver_id
        )
    ) INTO driver_data
    FROM drivers d
    JOIN drivers_rating r ON d.id = r.id
    WHERE d.id = driver_id;

    RETURN customer_data;
END;
$$ LANGUAGE plpgsql;

