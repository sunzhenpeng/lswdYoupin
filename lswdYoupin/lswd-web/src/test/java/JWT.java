import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.junit.Test;

import java.security.Key;

/**
 * Created by lirui on 16/8/28.
 */
public class JWT {

    @Test
    public void compact(){
        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        System.out.println(compactJws);

        Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws);
    }

}
