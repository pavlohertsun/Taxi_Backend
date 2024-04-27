CREATE OR REPLACE FUNCTION get_customer_data(customer_id bigint)
RETURNS json AS $$
DECLARE
    customer_data json;
BEGIN
    SELECT json_build_object(
        'customer', json_build_object(
            'name', c.name,
            'surname', c.surname,
            'email', c.email,
            'phone_number', c.phone_number,
            'rating', c.rating,
            'balance', c.balance
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
                        'driver', json_build_object(
                            'name', d.name
                        )
                    )
                )
            )
            FROM trips t
            JOIN drivers d ON t.driver_id = d.id
            WHERE c.id = t.customer_id
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
            WHERE c.id = s.customer_id
        )
    ) INTO customer_data
    FROM customers c
    WHERE c.id = customer_id;

    RETURN customer_data;
END;
$$ LANGUAGE plpgsql;

