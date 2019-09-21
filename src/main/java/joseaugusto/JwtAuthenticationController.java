package joseaugusto;


import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;



    @RequestMapping(value = "/pegaToken", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        System.out.println(authenticationRequest);
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    // http://localhost:8080/publico/teste
    @GetMapping("/publico/teste")
    public ResponseEntity<?> permiteAcesso(){
        return new ResponseEntity<String>("Publico para todos", HttpStatus.OK);
    }


    //http://localhost:8080/autenticado
    @GetMapping("/autenticado")
    public ResponseEntity<?> onlyAuthenticated(@AuthenticationPrincipal UserDetails auth){
        Usuario usu = new Usuario("ana",auth.getUsername()+"Autenticado passando detalhes do usuario", "1234");
        System.out.println(auth.getUsername());
        System.out.println(auth);

        return new ResponseEntity<Usuario>(usu, HttpStatus.OK);
    }

    //http://localhost:8080/autenticado
    @GetMapping("/admin/teste")
    public ResponseEntity<?> onlyAdmin(@AuthenticationPrincipal UserDetails auth){
        Usuario usu = new Usuario("onlyadmin",auth.getUsername()+"so para admin", "1234");
        System.out.println(auth.getUsername());
        return new ResponseEntity<Usuario>(usu, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
