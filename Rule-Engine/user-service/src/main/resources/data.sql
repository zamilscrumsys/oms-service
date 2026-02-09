-- ==============================================
-- USER SERVICE DATABASE INITIALIZATION SCRIPT
-- ==============================================



-- Insert default functions
INSERT INTO functions (name, description, api_endpoint, http_method, created_by)
VALUES
('VIEW_PROFILE', 'View user profile', '/api/v1/users/me', 'GET', 'SYSTEM'),
('EDIT_PROFILE', 'Edit user profile', '/api/v1/users/{id}', 'PUT', 'SYSTEM'),
('VIEW_USERS', 'View all users', '/api/v1/users', 'GET', 'SYSTEM'),
('MANAGE_USERS', 'Manage users', '/api/v1/users/**', 'ALL', 'SYSTEM'),
('MANAGE_ROLES', 'Manage roles', '/api/v1/roles/**', 'ALL', 'SYSTEM')
ON CONFLICT (name) DO NOTHING;

-- Insert default roles
INSERT INTO roles (name, description, created_by)
VALUES
('ROLE_USER', 'Default user role', 'SYSTEM'),
('ROLE_ADMIN', 'Administrator role', 'SYSTEM'),
('ROLE_SUPER_ADMIN', 'Super administrator role', 'SYSTEM')
ON CONFLICT (name) DO NOTHING;

-- Assign functions to roles
-- ROLE_USER gets VIEW_PROFILE and EDIT_PROFILE
INSERT INTO role_functions (role_id, function_id)
SELECT r.id, f.id FROM roles r, functions f
WHERE r.name = 'ROLE_USER' AND f.name IN ('VIEW_PROFILE', 'EDIT_PROFILE')
ON CONFLICT DO NOTHING;

-- ROLE_ADMIN gets additional VIEW_USERS and MANAGE_USERS
INSERT INTO role_functions (role_id, function_id)
SELECT r.id, f.id FROM roles r, functions f
WHERE r.name = 'ROLE_ADMIN' AND f.name IN ('VIEW_PROFILE', 'EDIT_PROFILE', 'VIEW_USERS', 'MANAGE_USERS')
ON CONFLICT DO NOTHING;

-- ROLE_SUPER_ADMIN gets all functions
INSERT INTO role_functions (role_id, function_id)
SELECT r.id, f.id FROM roles r, functions f
WHERE r.name = 'ROLE_SUPER_ADMIN' AND f.name IN ('VIEW_PROFILE', 'EDIT_PROFILE', 'VIEW_USERS', 'MANAGE_USERS', 'MANAGE_ROLES')
ON CONFLICT DO NOTHING;

-- Insert default departments
INSERT INTO departments (name, description, department_code, created_by)
VALUES
('IT Department', 'Information Technology Department', 'IT', 'SYSTEM'),
('HR Department', 'Human Resources Department', 'HR', 'SYSTEM'),
('Finance Department', 'Finance and Accounting Department', 'FIN', 'SYSTEM')
ON CONFLICT (name) DO NOTHING;

-- Create default admin user
-- Password: Admin@123 (encoded with BCrypt)
INSERT INTO users (
    username, email, password, first_name, last_name,
    email_verified,active,password_temporary, department_id, created_by
)
SELECT
    'admin',
    'mohamedzamil124@gmail.com',
    '$2a$10$dRtdv7VOwAJaYO/X41sGhuuiuaqs2VO3730jC7jnfo8yvOETfxgxK',-- Admin@123
    'System',
    'Administrator',
    true,
    true,
    false,
    d.id,
    'SYSTEM'
FROM departments d
WHERE d.name = 'IT Department'
ON CONFLICT (email) DO NOTHING;

-- Assign admin role to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'mohamedzamil124@gmail.com' AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

-- Create test user
-- Password: User@123 (encoded with BCrypt)
INSERT INTO users (
    username,
    email,
    mobile_number,
    password,
    first_name,
    last_name,
    email_verified,
    active,
    password_temporary,
    department_id,
    created_by
)
SELECT
    'john_doe',
    'john.doe@example.com',
    '+1234567890',
    '$2a$10$6Zn9J.7ZJqL9OyVJQY5X5c4X4bWN9qo8uLOickgx2ZMRZoMye.IJsg.7ZgG', -- User@123
    'John',
    'Doe',
    true,
    true,     -- active
    false,    -- password_temporary
    d.id,
    'SYSTEM'
FROM departments d
WHERE d.name = 'HR Department'
ON CONFLICT (email) DO NOTHING;

-- Assign user role to test user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'john.doe@example.com' AND r.name = 'ROLE_USER'
ON CONFLICT DO NOTHING;

-- Insert a log entry to mark successful initialization
INSERT INTO functions (name, description, created_by)
VALUES ('DB_INIT_COMPLETE', 'Database initialized at ' || CURRENT_TIMESTAMP, 'SYSTEM')
ON CONFLICT (name) DO NOTHING;


-- Ensure cascade delete for user_roles
ALTER TABLE user_roles
DROP CONSTRAINT IF EXISTS fk_user_roles_user;

ALTER TABLE user_roles
ADD CONSTRAINT fk_user_roles_user
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE CASCADE;
