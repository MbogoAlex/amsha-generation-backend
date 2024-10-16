DO $$
BEGIN
    -- Check if the table exists
    IF EXISTS (SELECT 1 FROM information_schema.tables
              WHERE table_schema = 'public'
                AND table_name = 'wallet') THEN

        INSERT INTO wallet (wallet_balance) VALUES (0.0);

    END IF;

END $$;