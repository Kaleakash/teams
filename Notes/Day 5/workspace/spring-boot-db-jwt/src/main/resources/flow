                                   JWT Authentication Flow

┌───────────────┐
│    Client     │
│React/Postman  │
└───────┬───────┘
        │
        │ 1. Login Request
        │    POST /api/auth/login
        │    username + password
        ▼
┌─────────────────────────────┐
│   Authentication Controller │
└──────────────┬──────────────┘
               │
               │ authenticate()
               ▼
┌─────────────────────────────┐
│ AuthenticationManager       │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│ DaoAuthenticationProvider   │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│ CustomUserDetailsService    │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│     User Repository         │
└──────────────┬──────────────┘
               │
               ▼
┌─────────────────────────────┐
│      H2 / MySQL Database    │
└──────────────┬──────────────┘
               │
      User Exists?
        │           │
       No          Yes
        │           │
        ▼           ▼
401 Unauthorized    Password Verification
                    (BCryptPasswordEncoder)
                           │
                    Password Correct?
                     │             │
                    No            Yes
                     │             │
                     ▼             ▼
             401 Unauthorized   JwtService
                                Generate JWT
                                     │
                                     ▼
                        ┌────────────────────┐
                        │ JWT Token Created  │
                        └─────────┬──────────┘
                                  │
                                  ▼
                    Return JWT Token to Client
                                  │
                                  ▼
                     Client Stores JWT Token
                 (LocalStorage / Memory / Session)
                                  │
────────────────────────────────────────────────────────────────────────
                                  │
                                  │
        Authorization: Bearer <JWT Token>
                                  │
                                  ▼
                  GET /products (Protected API)
                                  │
                                  ▼
┌─────────────────────────────────────────────┐
│              JwtAuthFilter                  │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
        Read Authorization Header
                   │
        Token Present?
          │             │
         No            Yes
          │             │
          ▼             ▼
 Continue Filter   Extract Username
                        │
                        ▼
                 Validate JWT Token
                        │
               Token Valid?
                 │           │
                No          Yes
                 │           │
                 ▼           ▼
          401 Unauthorized   Load UserDetails
                              │
                              ▼
              Create Authentication Object
                              │
                              ▼
     SecurityContextHolder.setAuthentication()
                              │
                              ▼
                 Spring Security
             Role / Authority Check
                              │
                  Has Required Role?
                    │             │
                   No            Yes
                    │             │
                    ▼             ▼
             403 Forbidden     Controller
                                   │
                                   ▼
                           Business Logic
                                   │
                                   ▼
                          JSON Response (200 OK)