# Frontend proyecto clase
 
 Este frontend usa React + Vite y se conecta contra Auth0 para autenticar administradores y consumir el backend `auth0-backend`.
 
 ## Configuración de entorno
 
 1. Copia el archivo `.env.example` a `.env` dentro de `frontend-proyecto-clase/` y ajusta los valores según tu tenant de Auth0:
 
    ```bash
    cd frontend-proyecto-clase
    cp .env.example .env
    ```
 
 2. Variables relevantes:
   - `VITE_AUTH0_DOMAIN` y `VITE_AUTH0_CLIENT_ID`: credenciales de tu aplicación SPA en Auth0.
   - `VITE_AUTH0_AUDIENCE`: identificador (API Identifier) de la API protegida que expone los scopes `read` y/o `write`.
   - `VITE_AUTH0_API_SCOPE`: scopes que debe incluir el Access Token para el backend (por ejemplo `read write`).
   - `VITE_AUTH0_ADMIN_ROLE`: nombre del rol que habilita el acceso al panel (por defecto `admin`).
   - `VITE_AUTH0_ROLES_CLAIM`: claim exacto donde Auth0 envía los roles (por ejemplo `https://<tu-dominio>/roles`).
   - `VITE_API_BASE_URL`: URL del backend que recibe las peticiones autenticadas.
 
3. Los `Allowed Callback URLs`, `Allowed Logout URLs` y `Allowed Web Origins` del tenant deben incluir `http://localhost:5173` (frontend). Si publicas el backend, añade su URL a las Origins permitidas de tu API en Auth0.
 
 ## Puesta en marcha
 
 ```bash
 cd frontend-proyecto-clase
 npm install
 npm run dev
 ```
 
 El backend `auth0-backend` se levanta con Maven:
 
 ```bash
 cd auth0-backend
 ./mvnw spring-boot:run
 ```
 
 ## Notas
 
 - El frontend solicita silenciosamente un Access Token con el `audience` y los scopes definidos; si el token no contiene el claim de roles configurado, se notificará al usuario.
 - Los tokens se almacenan en `localstorage` y se revalidan con `getAccessTokenSilently` para adjuntar el `Bearer` en las peticiones al backend.
