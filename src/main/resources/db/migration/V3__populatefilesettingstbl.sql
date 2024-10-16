DO $$
BEGIN
    -- Check if the table exists
    IF EXISTS (SELECT 1 FROM information_schema.tables
              WHERE table_schema = 'public'
                AND table_name = 'wallet') THEN

        INSERT INTO wallet (wallet_balance) VALUES (2.0);

    END IF;

    IF EXISTS (SELECT 1 FROM information_schema.tables
              WHERE table_schema = 'public'
                AND table_name = 'user_account') THEN

         UPDATE user_account SET account_balance = 2.0 WHERE id = 1;

    END IF;

END $$;
