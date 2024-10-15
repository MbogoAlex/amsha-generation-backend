DO $$
BEGIN
    -- Check if the table exists
    IF EXISTS (SELECT 1 FROM information_schema.tables
              WHERE table_schema = 'public'
                AND table_name = 'settings') THEN

        INSERT INTO settings (settings_key, value) VALUES
        ('imagePath', '/home/mbogo/Desktop/amsha-photos/img/'),
        ('domain', 'http://192.168.100.5:8080');

    END IF;

END $$;
