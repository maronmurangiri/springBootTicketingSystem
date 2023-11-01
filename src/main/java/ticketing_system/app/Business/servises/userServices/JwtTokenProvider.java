package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.percistance.Entities.userEntities.Users;

public interface JwtTokenProvider {
    String generateToken(Users users);
    Users getUserFromToken(String token);
}
