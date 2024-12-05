package backend.services;

import java.util.HashMap;
import java.util.Map;

public class LoginService {

    // Map para armazenar usuários e senhas (em um aplicativo real, isso seria feito de maneira mais segura)
    private static final Map<String, String> usuarios = new HashMap<>();

    static {
        // Usuários cadastrados (exemplo)
        usuarios.put("admin", "1234");
        usuarios.put("funcionario", "senha123");
    }

    // Método para validar o login
    public static boolean autenticar(String usuario, String senha) {
        String senhaCadastrada = usuarios.get(usuario);
        return senhaCadastrada != null && senhaCadastrada.equals(senha);
    }

    private static final Map<String, Integer> usuarioMercado = new HashMap<>(); // Armazena mercado associado ao usuário

    // Método para associar usuário a um mercado
    public static void associarMercado(String usuario, int mercadoId) {
        usuarioMercado.put(usuario, mercadoId);
    }

    // Método para recuperar o mercado associado a um usuário
    public static Integer getMercadoAssociado(String usuario) {
        return usuarioMercado.get(usuario);
    }
}
