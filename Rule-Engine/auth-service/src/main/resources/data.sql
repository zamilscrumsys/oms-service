-- ==============================================
-- AUTH SERVICE DATABASE INITIALIZATION SCRIPT


-- =====================================================
-- Ensure token_blacklist columns can store JWT safely
-- (JWTs are often > 500 characters)

ALTER TABLE IF EXISTS token_blacklist
    ALTER COLUMN token TYPE TEXT;

ALTER TABLE IF EXISTS token_blacklist
    ALTER COLUMN username TYPE TEXT;

ALTER TABLE IF EXISTS token_blacklist
    ALTER COLUMN client_id TYPE TEXT;

-- =====================================================
-- Optional cleanup (use only for local/dev)
-- =====================================================
-- DELETE FROM token_blacklist;
-- DELETE FROM otps;
-- DELETE FROM client_apps;

-- =====================================================
-- Create default client applications
-- =====================================================
INSERT INTO client_apps (
    client_name,
    client_id,
    client_secret,
    client_type,
    redirect_uri,
    description,
    created_by
)
VALUES
(
    'OMS Web Application',
    'web_client_12345',
    -- Encoded version of 'web_secret_67890'
    '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg.7ZgG6ZJqL9OyVJQY5X5c4X4bW',
    'WEB',
    'http://localhost:3000/callback',
    'OMS Web Application Frontend',
    'SYSTEM'
),
(
    'OMS Mobile Application',
    'mobile_client_12345',
    -- Encoded version of 'mobile_secret_67890'
    '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg.7ZgG6ZJqL9OyVJQY5X5c4X4bW',
    'MOBILE',
    'com.oms.mobile://callback',
    'OMS Mobile Application',
    'SYSTEM'
),
(
    'OMS Google Application',
    'google_client_12345',
    -- Encoded version of 'google_secret_67890'
    '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg.7ZgG6ZJqL9OyVJQY5X5c4X4bW',
    'GOOGLE',
    'http://localhost',
    'OMS Google OAuth Client',
    'SYSTEM'
),
(
    'OMS Google Application',
    'google_client_12345',
    -- Encoded version of 'google_secret_67890'
    '$2a$10$3TzYuKeXbLiJr9S.j7krLOQmM/S5jocTTR5o7g2lz73VFfPIfrZkK',
    'GOOGLE',
    'http://localhost',
    'OMS Google OAuth Client',
    'SYSTEM'
)
ON CONFLICT (client_name) DO NOTHING;

-- =====================================================
-- Client credentials for testing
-- =====================================================
-- Web Application:
--   Client ID:     web_client_12345
--   Client Secret: web_secret_67890
--
-- Mobile Application:
--   Client ID:     mobile_client_12345
--   Client Secret: mobile_secret_67890
-- =====================================================

--- =====================================================
 -- Simple OTP log entry (system bootstrap)
 -- =====================================================
 INSERT INTO otps (
     identifier,
     otp_code,
     otp_type,
     expiry_time,
     is_used,
     created_by
 )
 VALUES (
     'init_log',
     '000000',
     'SYSTEM',
     NOW() + INTERVAL '1 year',
     false,
     'SYSTEM'
 )
 ON CONFLICT DO NOTHING;

