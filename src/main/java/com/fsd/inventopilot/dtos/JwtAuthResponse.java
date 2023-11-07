package com.fsd.inventopilot.dtos;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String jwt;

    // Deze wordt meegestuurd voor de opdrachten meegestuurd met de response,
    // zodat hij kan worden getest in postman met een dummy functie.
    // In de praktijk zou ik deze token niet letterlijk declareren in objecten die worden verzonden
    private String refreshToken;
}

// voor extra veiligheid kan nog gecheckt worden of er niet geknoeid is met de jwt en refreshtoken;
// zo ver wordt in de cursus echter niet ingegaan op security
//
//        Verify the JWT's Signature:
//        To ensure the JWT is not tampered with, you need to verify its signature using the public key
//        of the issuer. The public key can be obtained from the issuer's JWKS (JSON Web Key Set) endpoint.
//
//        Check the JWT's Claims:
//        You should verify the standard claims (e.g., expiration time, issuer, audience)
//        and any custom claims you may have added to the JWT.
//
//        Check the Refresh Token:
//        Verify that the refresh token is valid and not expired.
//        The validation process may involve checking against a database
//        or cache where valid refresh tokens are stored.