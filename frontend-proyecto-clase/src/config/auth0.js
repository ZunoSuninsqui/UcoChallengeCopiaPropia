// src/config/auth0.js

// Lee variables de entorno (Vite expone VITE_* en import.meta.env)
const domain   = (import.meta.env.VITE_AUTH0_DOMAIN || '').trim();
const clientId = (import.meta.env.VITE_AUTH0_CLIENT_ID || '').trim();
const audience = (import.meta.env.VITE_AUTH0_AUDIENCE || '').trim();

// Scopes:
// - OIDC básicos para login (openid profile email) + los de tu API (read write)
// - Recomendación: define VITE_AUTH0_API_SCOPE="read write"
const loginScope = (import.meta.env.VITE_AUTH0_SCOPE || '').trim();       // ej: "openid profile email read write"
const apiScope   = (import.meta.env.VITE_AUTH0_API_SCOPE || '').trim();   // ej: "read write"

// Rol de admin esperado por tu app (el frontend compara contra este valor)
const adminRole = (import.meta.env.VITE_AUTH0_ADMIN_ROLE || 'admin').trim();

// Claim de roles en el token:
// - Si no lo especificas, por defecto miraremos "<namespace>roles"
// - namespace = "https://<tu-dominio-auth0>/"
const explicitRolesClaim =
  (import.meta.env.VITE_AUTH0_ROLES_CLAIM || import.meta.env.VITE_AUTH0_ADMIN_CLAIM || '').trim();

const namespace = domain ? `https://${domain}/` : '';

// Claim de roles por defecto (si no seteas VITE_AUTH0_ROLES_CLAIM)
const defaultRolesClaim = namespace ? `${namespace}roles` : 'roles';

// Export de configuración principal
export const auth0Config = {
  domain,
  clientId,
  audience,
  adminRole,
  // Para depuración o uso externo:
  namespace,
  defaultLoginScopes: ['openid', 'profile', 'email'],
  extraScope: loginScope,  // todo el scope que pidas en login
};

// Scopes usados en el login inicial (Auth0Provider / loginWithRedirect)
export const buildLoginScope = () => {
  // Si VITE_AUTH0_SCOPE está definido, úsalo tal cual
  if (loginScope) return loginScope;

  // Fallback sensato
  return 'openid profile email read write';
};

// Params para pedir el access token de tu API (getAccessTokenSilently)
export const buildResourceAuthorizationParams = () => {
  const params = {};
  if (audience) params.audience = audience;

  // Preferimos VITE_AUTH0_API_SCOPE ("read write").
  // Si no está, intentamos extraer los scopes "no-OIDC" del loginScope.
  let scopeToUse = apiScope;
  if (!scopeToUse && loginScope) {
    // Filtra openid/profile/email si vinieran mezclados
    scopeToUse = loginScope
      .split(/\s+/)
      .filter(s => s && !['openid', 'profile', 'email'].includes(s))
      .join(' ');
  }
  if (!scopeToUse) scopeToUse = 'read write';

  params.scope = scopeToUse;
  return params;
};

// Candidatos de claim de roles a inspeccionar en el token
export const resolveRoleClaimCandidates = () => {
  const candidates = [
    explicitRolesClaim,        // si lo defines vía VITE_AUTH0_ROLES_CLAIM, este manda
    defaultRolesClaim,         // p.ej. "https://dev-x2nlunlga02cbz17.us.auth0.com/roles"
    'roles',                   // fallback genérico
  ].filter(Boolean);

  // Evita duplicados
  return Array.from(new Set(candidates));
};
