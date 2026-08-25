package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Criptografia de senha com bcrypt (aula 04).
 *
 * A senha nunca é guardada no banco como o usuário digitou. Guardamos um
 * "hash": um texto embaralhado do qual não dá para voltar à senha original.
 * Para conferir o login, embaralhamos a senha digitada e comparamos com o
 * hash salvo - é isso que o checarSenha faz.
 */
public class SenhaUtil {

    public static String hashSenha(String senhaPura) {
        return BCrypt.hashpw(senhaPura, BCrypt.gensalt());
    }

    public static boolean checarSenha(String senhaPura, String hashSalvo) {
        if (senhaPura == null || hashSalvo == null) {
            return false;
        }
        return BCrypt.checkpw(senhaPura, hashSalvo);
    }
}
