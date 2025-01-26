package ku_rum.backend.global.security.jwt;

public enum JWTPrefix {
  BEARER("Bearer"),
  ACESS("Access"),
  REFRESH("Refresh"),
  AUTHORIZATION("Authorization");

  private String prefix;

  JWTPrefix(String prefix) {
    this.prefix = prefix;
  }
}
