package com.akhiastudios.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.akhiastudios.todolist.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
// servlet é a base para qualquer framework WEB para java
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // sem @Component o Spring não sabe da existência dessa classe e ela não vai ser aplicada como filtro
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository; // puxa o userRepository para validar se o usuário existe

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath(); // pega o endpoint do request sendo feito

                if (servletPath.startsWith("/tasks/")) { // se o endpoint for igual a "/tasks/", pode prosseguir com o filtro e validacoes:
                    // PEGAR O AUTH (USUARIO E SENHA)
                    var authorization = request.getHeader("Authorization"); // pega o header do auth
                    var authEncoded = authorization.substring("Basic".length()).trim(); // pega somente a senha da resposta do authorization q é: ([tipo de auth, ou seja, Basic] [senha])
                    
                    byte [] authDecode = Base64.getDecoder().decode(authEncoded); // descriptografa o auth em um codigo Byte

                    var authString = new String(authDecode); // passa os bytes para o auth ficar legivel, o auth ficaria mais ou menos assim: fernando:123456
                    
                    // SEPARAR AUTH (FERNANDO:123456)
                    String[] credentials = authString.split(":"); // divide o auth (fernando:123456) pelo : no meio em um vetor
                    String username = credentials[0]; // o primeiro item do vetor sera o username
                    String password = credentials[1]; // o segundo, a senha do usuario
                    
                    // VALIDAR SE O USUÁRIO EXISTE
                    var user = this.userRepository.findByUsername(username);
                    
                    if (user == null) { // se o usuário nao existir no banco de dados:
                        response.sendError(401, "Usuário sem autorização/não existe");
                    } else {
                        // VALIDAR SENHA
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()); // compara a senha do usuário e a senha recebida
                        
                        if (passwordVerify.verified == true) { // se a senha for validada, o filtro é passado para a próxima camada
                            request.setAttribute("idUser", user.getId()); // injeta o id do usuário na request
                            filterChain.doFilter(request, response);
                        } else {
                            response.sendError(401, "Usuário sem autorização/não existe");
                        }

                    }

                } else {

                    filterChain.doFilter(request, response);

                }
            
    }
    
}
